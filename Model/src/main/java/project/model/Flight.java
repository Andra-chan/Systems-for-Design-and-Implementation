package project.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Flight extends Entity<Integer> implements Serializable {
    private String destination;
    private String airport;
    private LocalDateTime dateTime;
    private Integer totalSeats;
    private Integer remainingSeats;

    public Flight(String destination, String airport, LocalDateTime dateTime, Integer totalSeats, Integer remainingSeats) {
        this.destination = destination;
        this.airport = airport;
        this.dateTime = dateTime;
        this.totalSeats = totalSeats;
        this.remainingSeats = remainingSeats;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    public Integer getRemainingSeats() {
        return remainingSeats;
    }

    public void setRemainingSeats(Integer remainingSeats) {
        this.remainingSeats = remainingSeats;
    }

}
