package project.network.protobuffprotocol;

import com.google.protobuf.Message;
import project.model.*;
import project.services.IObserver;
import project.services.IServices;
import project.services.ServiceException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

public class ProtoWorker implements Runnable, IObserver {
    private IServices server;
    private Socket connection;

    private InputStream input;
    private OutputStream output;
    private volatile boolean connected;
    public ProtoWorker(IServices server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try{
            output=connection.getOutputStream() ;//new ObjectOutputStream(connection.getOutputStream());
            input=connection.getInputStream(); //new ObjectInputStream(connection.getInputStream());
            connected=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {

        while(connected){
            try {
                // Object request=input.readObject();
                System.out.println("Waiting requests ...");
                Protobuffs.Request request=Protobuffs.Request.parseDelimitedFrom(input);
                System.out.println("Request received: "+request);
                Protobuffs.Response response=handleRequest(request);
                if (response!=null){
                    sendResponse(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error "+e);
        }
    }

    private Protobuffs.Response handleRequest(Protobuffs.Request request){
        Protobuffs.Response response=null;
        switch (request.getType()){
            case Login:{
                System.out.println("Login request ...");
                String email = request.getEmail();
                String password = request.getPassword();

                try {
                    Integer id = server.login(email, password, this);
                    return ProtoUtils.createLoginResponse(id);
                } catch (ServiceException e) {
                    connected=false;
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }
            case Logout:{
                System.out.println("Logout request");
                User user=ProtoUtils.getUser(request);
                try {
                    server.logout(user, this);
                    connected=false;
                    return ProtoUtils.createOkResponse();

                } catch (ServiceException e) {
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }
            case GetAllFlights:{
                System.out.println("Get all flights request...");
                try{
                    List<Flight> flights = server.getAllFlights();
                    return ProtoUtils.createAllFlightsResponse(flights);
                } catch (ServiceException e) {
                return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }
            case SearchByAirport:{
                System.out.println("Search request...");
                String destination = null;
                String airport = null;
                if(!request.getAirport().isEmpty())
                    airport= request.getAirport();
                if(!request.getDestination().isEmpty())
                    destination= request.getDestination();

                try {
                    List<Flight> flights = server.getFlightBySearchAirport(destination, airport);
                    return ProtoUtils.createFlightResponse(flights);
                } catch (ServiceException e) {
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }
            case SellTicket:{
                System.out.println("Sell Ticket request ...");
                Ticket ticket = ProtoUtils.getTicket(request.getTicket());
                try {
                    server.buyTicket(ticket.getFlightId(), ticket.getName(), ticket.getTourists(), ticket.getAddress(), ticket.getSeats());
                    return ProtoUtils.createOkResponse();
                } catch (ServiceException e) {
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }
        }
        return response;
    }

    private void sendResponse(Protobuffs.Response response) throws IOException{
        System.out.println("sending response "+response);
        response.writeDelimitedTo(output);
        //output.writeObject(response);
        output.flush();
    }

    @Override
    public void ticketsSold(List<Flight> flights) throws ServiceException {
        System.out.println("Tickets sold!");
        try {
            sendResponse(ProtoUtils.createTicketSoldResponse(flights));
        } catch (IOException e) {
            throw new ServiceException("Sending error: "+e);
        }
    }
}
