package project.services;

import project.model.Flight;
import project.model.FlightDTO;
import project.model.User;


import java.util.List;

public interface IServices {

    public Integer login(String email, String password, IObserver client) throws ServiceException;
    void logout(User user, IObserver client) throws ServiceException;
    public List<Flight> getAllFlights();
    //public Iterable<Ticket> getAllTickets();
    //public List<Flight> getFlightBySearch(String searchDestination, LocalDateTime searchDateTime);
    public List<Flight> getFlightBySearchAirport(String searchDestination, String airport) throws  ServiceException;
    //public Flight addFlight(Flight elem);
    //public User addUser(User user);
    public void buyTicket(Integer flightId, String name, String tourists, String address, Integer seats) throws ServiceException;
}
