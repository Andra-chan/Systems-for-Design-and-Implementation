package project.network.objectprotocol;

public class FlightRequest implements Request{
    private String airport;
    private String destination;

    public FlightRequest(String destination, String airport){
        this.destination = destination;
        this.airport = airport;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
