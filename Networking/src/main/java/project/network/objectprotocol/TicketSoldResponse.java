package project.network.objectprotocol;

import project.model.Flight;
import project.model.FlightDTO;
import project.model.TicketDTO;

import java.util.List;

public class TicketSoldResponse implements UpdateResponse{
    private List<Flight> flights;


    public TicketSoldResponse(List<Flight> flights) {
        this.flights = flights;
    }

    public List<Flight> getFlight() {
        return flights;
    }

    public void setFlight(List<Flight> flights) {
        this.flights = flights;
    }
}