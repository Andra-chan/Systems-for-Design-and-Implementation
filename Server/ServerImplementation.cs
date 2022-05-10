using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Model;
using Persistence.database.repositoryInterfaces;
using Services;

namespace Server
{
    public class ServerImplementation : IServices
	{
		private IFlightDbRepository<int, Flight> flightDbRepository;
		private ITicketDbRepository<int, Ticket> ticketDbRepository;
		private IUserDbRepository<int, User> userDbRepository;
		private readonly IDictionary<String, IObserver> loggedClients;

		public ServerImplementation(IFlightDbRepository<int, Flight> flightDbRepository,
			ITicketDbRepository<int, Ticket> ticketDbRepository, IUserDbRepository<int, User> userDbRepository)
		{
			this.flightDbRepository = flightDbRepository;
			this.ticketDbRepository = ticketDbRepository;
			this.userDbRepository = userDbRepository;
			loggedClients = new Dictionary<String, IObserver>();
		}

		public int login(String email, String password, IObserver client)
		{
			User user = userDbRepository.findOneLogin(email, password);
			if (user != null)
			{
				if (loggedClients.ContainsKey(email))
					throw new ServiceException("User already logged in.");
				loggedClients[email] = client;
				return user.Id;
			}
			else
				throw new ServiceException("Authentication failed.");
		}

		public void logout(User user, IObserver client)
		{
			IObserver localClient = loggedClients[user.Email];
			if (localClient == null)
				throw new ServiceException("User " + user.Email + " is not logged in.");
			loggedClients.Remove(user.Email);
		}

		public IList<Flight> getAllFlights()
		{
			return flightDbRepository.findAll();
		}

		public IList<Ticket> getAllTickets()
		{
			return ticketDbRepository.findAll();
		}

		public IList<Flight> getFlightBySearch(String searchDestination, DateTime searchDateTime)
		{
			return flightDbRepository.findBySearch(searchDestination, searchDateTime);
		}

		public IList<Flight> getFlightBySearchAirport(String searchDestination, String airport)
		{
			return flightDbRepository.findBySearchAirport(searchDestination, airport);
		}

		public void addFlight(Flight elem)
		{
			flightDbRepository.save(elem);
		}

		public void addUser(User user)
		{
			userDbRepository.save(user);
		}

		private void notifyTicketsSold()
		{
			foreach (var elem in loggedClients)
			{
				Console.WriteLine("notifying client {0}", elem.Key);
				Task.Run(() => elem.Value.ticketsSold(this.getAllFlights()));
			}
		}

		public void buyTicket(int flightId, String name, String tourists, String address, int seats)
		{
			Flight flight = flightDbRepository.findOne(flightId);
			if (seats > flight.RemainingSeats)
				return;
			ticketDbRepository.save(new Ticket(flightId, name, tourists, address, seats));
			flight.RemainingSeats = flight.RemainingSeats - seats;
			if (flight.RemainingSeats < 0)
				flight.RemainingSeats = 0;
			flightDbRepository.update(flight);
			notifyTicketsSold();
		}
	}
}