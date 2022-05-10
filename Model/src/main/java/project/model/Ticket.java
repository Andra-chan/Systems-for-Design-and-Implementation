package project.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name="Tickets")
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

    @Column(name="flight_id")
    public Integer getFlightId() {
        return flightId;
    }

    public void setFlightId(Integer flightId) {
        this.flightId = flightId;
    }

    @Column(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name="tourists")
    public String getTourists() {
        return tourists;
    }

    public void setTourists(String tourists) {
        this.tourists = tourists;
    }

    @Column(name="address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name="seats")
    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }
}
