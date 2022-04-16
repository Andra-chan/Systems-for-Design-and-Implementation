package project.model;

import java.io.Serializable;

public class Ticket extends Entity<Integer> implements Serializable {
    private Integer flightId;
    private String name;
    private String tourists;
    private String address;
    private Integer seats;

    public Ticket(Integer flightId, String name, String tourists, String address, Integer seats) {
        this.flightId = flightId;
        this.name = name;
        this.tourists = tourists;
        this.address = address;
        this.seats = seats;
    }

    public Integer getFlightId() {
        return flightId;
    }

    public void setFlightId(Integer flightId) {
        this.flightId = flightId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTourists() {
        return tourists;
    }

    public void setTourists(String tourists) {
        this.tourists = tourists;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }
}
