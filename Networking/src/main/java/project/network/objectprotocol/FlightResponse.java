package project.network.objectprotocol;

import project.model.Flight;
import project.model.FlightDTO;

import java.util.List;

public class FlightResponse implements Response{
    private List<Flight> flightDTOS;

    public FlightResponse(List<Flight> flightDTOS) {
        this.flightDTOS = flightDTOS;
    }

    public List<Flight> getFlightDTOS() {
        return flightDTOS;
    }
}
