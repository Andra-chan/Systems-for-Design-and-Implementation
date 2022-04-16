package project.network.objectprotocol;

import project.model.Flight;

import java.util.List;

public class GetFlightsResponse implements Response{
    private List<Flight> flightDTOS;

    public GetFlightsResponse(List<Flight> flightDTOS) {
        this.flightDTOS = flightDTOS;
    }

    public List<Flight> getFlightDTOS() {
        return flightDTOS;
    }
}
