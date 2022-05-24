package project.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Flight extends Entity<Integer> implements Serializable {
    private String destination;
    private String airport;

    //@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    //@JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime dateTime;

    private Integer totalSeats;
    private Integer remainingSeats;

    public Flight(){}

    public Flight(String destination){
        this(destination,"",LocalDateTime.now(),0,0);
    }

    public Flight(String destination, String airport){
        this(destination,airport,LocalDateTime.now(),0,0);
    }

    public Flight(String destination, String airport, LocalDateTime dateTime){
        this(destination,airport,dateTime,0,0);
    }


    public Flight(String destination, String airport, LocalDateTime dateTime, Integer totalSeats){
        this(destination,airport,dateTime,totalSeats,0);
    }
    public Flight(String destination, String airport, String dateTime, Integer totalSeats, Integer remainingSeats) {
        this.destination = destination;
        this.airport = airport;
        this.dateTime = LocalDateTime.parse(dateTime);
        this.totalSeats = totalSeats;
        this.remainingSeats = remainingSeats;
    }

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
