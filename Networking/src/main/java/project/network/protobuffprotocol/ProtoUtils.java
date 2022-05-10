package project.network.protobuffprotocol;

import project.model.Flight;
import project.model.Ticket;
import project.model.User;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class ProtoUtils {

    public static String getError(Protobuffs.Response response) {
        String errorMessage = response.getError();
        return errorMessage;
    }

    public static Protobuffs.Request createLoginRequest(String email, String password){
        Protobuffs.Request request= Protobuffs.Request.newBuilder().setType(Protobuffs.Request.Type.Login)
                .setEmail(email).setPassword(password).build();
        return request;
    }
    public static Protobuffs.Request createLogoutRequest(User user){
        Protobuffs.User user1=Protobuffs.User.newBuilder().setFirstName(user.getFirstName()).setLastName(user.getLastName()).setEmail(user.getEmail()).setPassword(user.getPassword()).build();
        Protobuffs.Request request= Protobuffs.Request.newBuilder().setType(Protobuffs.Request.Type.Logout)
                .setUser(user1).build();
        return request;
    }

    public static User getUser(Protobuffs.Response response) {
        User user=new User(response.getUser().getFirstName(), response.getUser().getLastName(), response.getUser().getEmail(), response.getUser().getPassword());
        return user;
    }

    public static User getUser(Protobuffs.Request request) {
        User user=new User(request.getUser().getFirstName(), request.getUser().getLastName(), request.getUser().getEmail(), request.getUser().getPassword());
        return user;
    }

    public static Integer getSeats(Integer seats) {
        return seats;
    }

    public static Protobuffs.Response createOkResponse() {
        Protobuffs.Response response=Protobuffs.Response.newBuilder()
                .setType(Protobuffs.Response.Type.Ok).build();
        return response;
    }

    public static Protobuffs.Response createErrorResponse(String message) {
        Protobuffs.Response response=Protobuffs.Response.newBuilder()
                .setType(Protobuffs.Response.Type.Error)
                .setError(message).build();
        return response;
    }

    public static Protobuffs.Response createLoginResponse(Integer id) {
        Protobuffs.Response response=  Protobuffs.Response.newBuilder()
                .setType( Protobuffs.Response.Type.Login)
                .setId(id).build();
        return response;
    }

    public static List<Flight> getFlights(Protobuffs.Response response) {
        Flight[] flights = new Flight[response.getFlightsCount()];
        for(int i=0;i<response.getFlightsCount();i++){
            Protobuffs.Flight flight=response.getFlights(i);
            LocalDateTime date = Timestamp.valueOf(flight.getDate().replace('T', ' ')).toLocalDateTime();
            Flight flight1 = new Flight(flight.getDestination(),flight.getAirport(), date,flight.getTotalSeats(), flight.getRemainingSeats());
            flight1.setId(flight.getId());
            flights[i]=flight1;
        }
        return Arrays.asList(flights.clone());
    }

    public static Protobuffs.Request createFlightRequest(String destination, String airport) {
        Protobuffs.Request request= Protobuffs.Request.newBuilder().setType(Protobuffs.Request.Type.SearchByAirport)
                .setDestination(destination).setAirport(airport).build();
        return request;
    }

    public static Protobuffs.Request createFlightRequest() {
        Protobuffs.Request request= Protobuffs.Request.newBuilder().setType(Protobuffs.Request.Type.SearchByAirport)
                .build();
        return request;
    }

    public static Protobuffs.Request createAllFlightsRequest() {
        Protobuffs.Request request= Protobuffs.Request.newBuilder().setType(Protobuffs.Request.Type.GetAllFlights)
                .build();
        return request;
    }

    public static Protobuffs.Response createFlightResponse(Iterable<Flight> flights) {
        Protobuffs.Response.Builder response= Protobuffs.Response.newBuilder()
                .setType(Protobuffs.Response.Type.SearchByAirport);
        for (Flight flight: flights){
            Protobuffs.Flight flight1 = Protobuffs.Flight.newBuilder().setId(flight.getId()).setDestination(flight.getDestination())
                    .setAirport(flight.getAirport()).setDate(flight.getDateTime().toString()).setTotalSeats(flight.getTotalSeats()).setRemainingSeats(flight.getRemainingSeats()).build();
            response.addFlights(flight1);
        }

        return response.build();
    }

    public static Protobuffs.Response createAllFlightsResponse(Iterable<Flight> flights) {
        Protobuffs.Response.Builder response= Protobuffs.Response.newBuilder()
                .setType(Protobuffs.Response.Type.GetAllFlights);
        for (Flight flight: flights){
            Protobuffs.Flight flight1 = Protobuffs.Flight.newBuilder().setId(flight.getId()).setDestination(flight.getDestination())
                    .setAirport(flight.getAirport()).setDate(flight.getDateTime().toString()).setTotalSeats(flight.getTotalSeats()).setRemainingSeats(flight.getRemainingSeats()).build();
            response.addFlights(flight1);
        }

        return response.build();
    }

    public static Protobuffs.Request createSellTicketRequest(Ticket ticket) {
        Protobuffs.Ticket ticket1 = Protobuffs.Ticket.newBuilder().setFlightId(ticket.getFlightId())
                .setName(ticket.getName()).setTourists(ticket.getTourists()).setAddress(ticket.getAddress()).setSeats(ticket.getSeats()).build();
        Protobuffs.Request request= Protobuffs.Request.newBuilder().setType(Protobuffs.Request.Type.SellTicket)
                .setTicket(ticket1).build();
        return request;
    }

    public static Ticket getTicket(Protobuffs.Ticket ticket) {
        Ticket ticket1 = new Ticket(ticket.getFlightId(), ticket.getName(), ticket.getTourists(), ticket.getAddress(), ticket.getSeats());
        return  ticket1;
    }

    public static Flight getFlight(Protobuffs.Flight flight){
        LocalDateTime date = Timestamp.valueOf(flight.getDate().replace('T', ' ')).toLocalDateTime();
        Flight flight1 = new Flight(flight.getDestination(),flight.getAirport(), date,flight.getTotalSeats(), flight.getRemainingSeats());
        flight1.setId(flight.getId());
        return flight1;
    }

    public static Protobuffs.Response createTicketSoldResponse(List<Flight> flights) {
        Protobuffs.Response.Builder response= Protobuffs.Response.newBuilder()
                .setType(Protobuffs.Response.Type.SellTicket);
        for (Flight flight: flights){
            Protobuffs.Flight flight1 = Protobuffs.Flight.newBuilder().setId(flight.getId()).setDestination(flight.getDestination())
                    .setAirport(flight.getAirport()).setDate(flight.getDateTime().toString()).setTotalSeats(flight.getTotalSeats()).setRemainingSeats(flight.getRemainingSeats()).build();
            response.addFlights(flight1);
        }
        return response.build();
    }

}
