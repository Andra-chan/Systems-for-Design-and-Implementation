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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServicesObjectProxy implements IServices {
    private final String host;
    private final int port;

    private IObserver client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    //private List<Response> responses;
    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;
    public ServicesObjectProxy(String host, int port) {
        this.host = host;
        this.port = port;
        //responses=new ArrayList<Response>();
        qresponses=new LinkedBlockingQueue<Response>();
    }

    @Override
    public Integer login(String email, String password, IObserver client) throws ServiceException {
        initializeConnection();
        sendRequest(new LoginRequest(email, password));
        Response response=readResponse();
        if (response instanceof LoginResponse){
            this.client=client;
            return ((LoginResponse) response).getId();
        }
        if (response instanceof OkResponse){
            this.client=client;
            return null;
        }
        if (response instanceof ErrorResponse){
            ErrorResponse err=(ErrorResponse)response;
            closeConnection();
            throw new ServiceException(err.getMessage());
        }
        return null;
    }

    @Override
    public List<Flight> getAllFlights() throws ServiceException{
        sendRequest(new GetFlightsRequest());
        Response response = readResponse();
        if (response instanceof ErrorResponse){
            ErrorResponse err=(ErrorResponse)response;
            throw new ServiceException(err.getMessage());
        }
        if(response instanceof GetFlightsResponse)
            return ((GetFlightsResponse)response).getFlightDTOS();
        return null;
    }

    @Override
    public List<Flight> getFlightBySearchAirport(String searchDestination, String airport) throws ServiceException{
        sendRequest(new FlightRequest(searchDestination, airport));
        Response response=readResponse();
        if (response instanceof ErrorResponse){
            ErrorResponse err=(ErrorResponse)response;
            throw new ServiceException(err.getMessage());
        }
        if(response instanceof FlightResponse)
            return ((FlightResponse)response).getFlightDTOS();
        return null;
    }

    @Override
    public void buyTicket(Integer flightId, String name, String tourists, String address, Integer seats) throws ServiceException {
        sendRequest(new SellTicketRequest(new TicketDTO(flightId, name, tourists, address, seats)));
        Response response=readResponse();
        if (response instanceof ErrorResponse){
            ErrorResponse err=(ErrorResponse)response;
            throw new ServiceException(err.getMessage());
        }
        System.out.println("Ticket sold - proxy");
    }

    @Override
    public void logout(User user, IObserver client) throws ServiceException {
        sendRequest(new LogoutRequest(user));
        Response response=readResponse();
        closeConnection();
        if (response instanceof ErrorResponse){
            ErrorResponse err=(ErrorResponse)response;
            throw new ServiceException(err.getMessage());
        }
    }

    private void closeConnection() {
        finished=true;
        try {
            input.close();
            output.close();
            connection.close();
            client=null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendRequest(Request request)throws ServiceException {
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new  ServiceException("Error sending object "+e);
        }

    }

    private Response readResponse() throws ServiceException {
        Response response=null;
        try{

            response=qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }
    private void initializeConnection() {
        try {
            connection=new Socket(host,port);
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            finished=false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void startReader(){
        Thread tw=new Thread(new ReaderThread());
        tw.start();
    }


    private void handleUpdate(UpdateResponse update){

        if (update instanceof TicketSoldResponse){
            TicketSoldResponse ticketSoldResponse=(TicketSoldResponse) update;
            Flight flight=ticketSoldResponse.getFlight();
            Integer seats = ticketSoldResponse.getSeats();
            System.out.println("Some tickets were sold!");
            try{
                client.ticketsSold(flight, seats);
            }catch (ServiceException e){
                e.printStackTrace();
            }
        }
    }

    private class ReaderThread implements Runnable{
        public void run() {
            while(!finished){
                try {
                    System.out.println("waiting for response");
                    Object response=input.readObject();
                    System.out.println("response received "+response);
                    if (response instanceof UpdateResponse){
                        handleUpdate((UpdateResponse)response);
                    }else{
                        try {
                            qresponses.put((Response)response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error "+e);
                } catch (ClassNotFoundException e) {
                    System.out.println("Reading error "+e);
                }

            }
        }
    }
}
