package project.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class FlightRest extends Entity<Integer> implements Serializable {
    private String destination;
    private String airport;
    private String dateTime;
    private Integer totalSeats;
    private Integer remainingSeats;

    public FlightRest(){}

    public FlightRest(String destination){
        this(destination,"","",0,0);
    }

    public FlightRest(String destination, String airport){
        this(destination,airport,"",0,0);
    }

    public FlightRest(String destination, String airport, String dateTime){
        this(destination,airport,dateTime,0,0);
    }

    public FlightRest(String destination, String airport, String dateTime, Integer totalSeats){
        this(destination,airport,dateTime,totalSeats,0);
    }

    public FlightRest(String destination, String airport, String dateTime, Integer totalSeats, Integer remainingSeats) {
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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
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
