package project.services;

import project.model.Flight;

import java.util.List;


public interface IObserver {
    void ticketsSold(List<Flight> flights) throws ServiceException;
}
