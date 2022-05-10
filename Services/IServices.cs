using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Model;

namespace Services
{
	public interface IServices
	{

		int login(String email, String password, IObserver client);

		void logout(User user, IObserver client);

		IList<Flight> getAllFlights();

		//IList<Ticket> getAllTickets();

		//IList<Flight> getFlightBySearch(String searchDestination, DateTime searchDateTime);

		IList<Flight> getFlightBySearchAirport(String searchDestination, String airport);

		//void addFlight(Flight elem);

		//void addUser(User user);

		void buyTicket(int flightId, String name, String tourists, String address, int seats);
	}
}
