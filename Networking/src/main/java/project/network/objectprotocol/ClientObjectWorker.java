package project.network.objectprotocol;

import project.model.Flight;
import project.model.FlightDTO;
import project.model.TicketDTO;
import project.model.User;
import project.services.IObserver;
import project.services.IServices;
import project.services.ServiceException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ClientObjectWorker implements Runnable, IObserver {
    private IServices server;
    private IObserver observer;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;
    public ClientObjectWorker(IServices server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try{
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            connected=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while(connected){
            try {;
                Object request=input.readObject();
                System.out.println("Run of clientobjworker"+request);
                Object response=handleRequest((Request)request);
                System.out.println(response);
                if (response!=null){
                    sendResponse((Response) response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
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

    private Response handleRequest(Request request){
        Response response=null;
        if (request instanceof LoginRequest){
            System.out.println("Login request ...");
            LoginRequest logReq=(LoginRequest)request;
            String email = logReq.getEmail();
            String password = logReq.getPassword();
            try {
                Integer id=server.login(email, password,this);

                return new LoginResponse(id);
            } catch (ServiceException e) {
                connected=false;
                return new ErrorResponse(e.getMessage());
            }
        }
        if (request instanceof FlightRequest){
            System.out.println("Flight Request");
            FlightRequest flightReq=(FlightRequest) request;
            String destination = flightReq.getDestination();
            String airport = flightReq.getAirport();
            try {
                List<Flight> flightDTOS=server.getFlightBySearchAirport(destination, airport);
                return new FlightResponse(flightDTOS);

            } catch (ServiceException e) {
                connected=false;
                return new ErrorResponse(e.getMessage());
            }
        }
        if (request instanceof  GetFlightsRequest) {
            System.out.println("Get Flights Request");
            try {
                List<Flight> flights = server.getAllFlights();
                return new GetFlightsResponse(flights);
            } catch (ServiceException e) {
                connected = false;
                return new ErrorResponse(e.getMessage());
            }
        }
        if (request instanceof LogoutRequest){
            System.out.println("Logout request");
            LogoutRequest logReq=(LogoutRequest)request;
            User user= logReq.getUser();
            try {
                server.logout(user, this);
                connected=false;
                return new OkResponse();

            } catch (ServiceException e) {
                return new ErrorResponse(e.getMessage());
            }
        }
        if(request instanceof SellTicketRequest){
            System.out.println("Sell ticket request");
            SellTicketRequest sellTicketRequest=(SellTicketRequest) request;
            TicketDTO ticketDTO= sellTicketRequest.getTicketDTO();
            try {
                server.buyTicket(ticketDTO.getFlightId(), ticketDTO.getName(),ticketDTO.getTourists(), ticketDTO.getAddress(), ticketDTO.getSeats());
                return new OkResponse();
                //return new SellTicketResponse(ticketDTO);
            } catch (ServiceException e) {
                return new ErrorResponse(e.getMessage());
            }
        }

        return response;
    }

    private void sendResponse(Response response) throws IOException{
        System.out.println("sending response "+response);
        synchronized (output) {
            output.writeObject(response);
            output.flush();
        }
    }

    @Override
    public void ticketsSold(List<Flight> flights) throws ServiceException {
        System.out.println("Ticket sold!");
        try {
            sendResponse(new TicketSoldResponse(flights));
        } catch (IOException e) {
            throw new ServiceException("Sending error: "+e);
        }
    }
}
