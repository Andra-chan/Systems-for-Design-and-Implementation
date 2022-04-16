package project.network.objectprotocol;

import project.model.Flight;
import project.model.FlightDTO;
import project.model.TicketDTO;

public class TicketSoldRequest implements Request{
    private Flight flight;

    public TicketSoldRequest(Flight flight) {
        this.flight = flight;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }
}
