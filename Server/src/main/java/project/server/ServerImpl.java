package project.server;

import project.model.Flight;
import project.model.FlightDTO;
import project.model.Ticket;
import project.model.User;
import project.network.utils.ServerException;
import project.repository.IFlightRepository;
import project.repository.ITicketRepository;
import project.repository.IUserRepository;
import project.services.IObserver;
import project.services.IServices;
import project.services.ServiceException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ServerImpl implements IServices {
    private IFlightRepository flightDbRepository;
    private ITicketRepository ticketDbRepository;
    private IUserRepository userDbRepository;
    private Map<Integer, IObserver> loggedClients;
    private final int defaultThreadsNo = 5;

    public ServerImpl(IFlightRepository flightDbRepository, ITicketRepository ticketDbRepository, IUserRepository userDbRepository) {
        this.flightDbRepository = flightDbRepository;
        this.ticketDbRepository = ticketDbRepository;
        this.userDbRepository = userDbRepository;
        loggedClients = new ConcurrentHashMap<>();
    }

    private void notifyClients(Flight flight, Integer seats) throws ServiceException {
        System.out.println("Notify");

        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        for(Integer id: loggedClients.keySet()){
            IObserver client=loggedClients.get(id);
            if (client!=null)
                executor.execute(() -> {
                    try {
                        System.out.println("Notifying [" + id + "]");
                        client.ticketsSold(flight, seats);
                    } catch (ServiceException e) {
                        System.err.println("Error notifying " + e);
                    }
                });
        }

        executor.shutdown();
    }

    public synchronized Integer login(String email, String password, IObserver client) throws ServiceException {
        User user = userDbRepository.findOneLogin(email, password);
        if (user != null) {
            if(loggedClients.get(user.getId())!=null)
                throw new ServiceException("User already logged in!");
            loggedClients.put(user.getId(), client);
            return user.getId();
        }
        else
            throw new ServiceException("Authentication failed.");
    }

    public synchronized void logout(User user, IObserver client) throws ServiceException {
        IObserver localClient = loggedClients.remove(user.getId());
        if (localClient == null)
            throw new ServiceException("User " + user.getId() + " is not logged in.");
    }

    public List<Flight> getAllFlights() {
        return StreamSupport.stream(flightDbRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public Iterable<Ticket> getAllTickets() {
        return ticketDbRepository.findAll();
    }

    public List<Flight> getFlightBySearch(String searchDestination, LocalDateTime searchDateTime) {
        return StreamSupport.stream(flightDbRepository.findBySearch(searchDestination, searchDateTime).spliterator(), false).collect(Collectors.toList());
    }

    public List<Flight> getFlightBySearchAirport(String searchDestination, String airport) throws ServiceException{
        return flightDbRepository.findBySearchAirport(searchDestination, airport);
    }

    public Flight addFlight(Flight elem) {
        return flightDbRepository.save(elem);
    }

    public User addUser(User user) {
        return userDbRepository.save(user);
    }

    public synchronized void buyTicket(Integer flightId, String name, String tourists, String address, Integer seats) throws ServiceException {

        Flight flight = flightDbRepository.findOne(flightId);
        if (seats > flight.getRemainingSeats())
            return;
        ticketDbRepository.save(new Ticket(flightId, name, tourists, address, seats));
        flight.setRemainingSeats(flight.getRemainingSeats() - seats);
        if (flight.getRemainingSeats() < 0)
            flight.setRemainingSeats(0);
        flightDbRepository.update(flight);
        notifyClients(flight, seats);
    }

}