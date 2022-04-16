package project.network.objectprotocol;

import project.model.Flight;
import project.model.FlightDTO;
import project.model.TicketDTO;

public class TicketSoldResponse implements UpdateResponse{
    private Flight flight;
    private Integer seats;

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public TicketSoldResponse(Flight flight, Integer seats) {
        this.flight = flight;
        this.seats = seats;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }
}