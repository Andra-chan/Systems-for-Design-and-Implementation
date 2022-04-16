package project.services;

import project.model.Flight;


public interface IObserver {
    void ticketsSold(Flight flight, Integer seats) throws ServiceException;
}
