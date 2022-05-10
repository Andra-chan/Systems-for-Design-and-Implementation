package project.network.protobuffprotocol;

import project.model.Flight;
import project.model.Ticket;
import project.model.User;
import project.services.IObserver;
import project.services.IServices;
import project.services.ServiceException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProtoProxy implements IServices {
    private String host;
    private int port;

    private IObserver client;

    private InputStream input;
    private OutputStream output;
    private Socket connection;

    private BlockingQueue<Protobuffs.Response> qresponses;
    private volatile boolean finished;
    public ProtoProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses=new LinkedBlockingQueue<Protobuffs.Response>();
    }

    public Integer login(String email, String password, IObserver client) throws ServiceException {
        initializeConnection();
        System.out.println("Login request ...");
        sendRequest(ProtoUtils.createLoginRequest(email, password));
        Protobuffs.Response response=readResponse();
        if (response.getType()==Protobuffs.Response.Type.Login){
            this.client=client;
            Integer id = response.getId();
            return id;
        }
        if (response.getType()==Protobuffs.Response.Type.Error){
            String errorText=ProtoUtils.getError(response);
            closeConnection();
            throw new ServiceException(errorText);
        }
        return null;
    }

    public void logout(User user, IObserver client) throws ServiceException {
        sendRequest(ProtoUtils.createLogoutRequest(user));
        Protobuffs.Response response=readResponse();
        closeConnection();
        if (response.getType()== Protobuffs.Response.Type.Error){
            String errorText=ProtoUtils.getError(response);
            throw new ServiceException(errorText);
        }
    }

    public List<Flight> getAllFlights(){
        sendRequest(ProtoUtils.createAllFlightsRequest());
        Protobuffs.Response response=readResponse();
        if (response.getType()== Protobuffs.Response.Type.Error){
            String errorText=ProtoUtils.getError(response);
            closeConnection();
            throw new ServiceException(errorText);
        }
        if (response.getType()== Protobuffs.Response.Type.GetAllFlights){
            return ProtoUtils.getFlights(response);
        }
        return null;
    }

    @Override
    public List<Flight> getFlightBySearchAirport(String destination, String airport) throws ServiceException {
        if(destination==null || airport==null)
            sendRequest(ProtoUtils.createFlightRequest());
        else
            sendRequest(ProtoUtils.createFlightRequest(destination, airport));
        Protobuffs.Response response=readResponse();
        if (response.getType()== Protobuffs.Response.Type.Error){
            String errorText=ProtoUtils.getError(response);
            closeConnection();
            throw new ServiceException(errorText);
        }
        if (response.getType()== Protobuffs.Response.Type.SearchByAirport){
            return ProtoUtils.getFlights(response);
        }
        return null;
    }

    @Override
    public void buyTicket(Integer flightId, String name, String tourists, String address, Integer seats) throws ServiceException {
        sendRequest(ProtoUtils.createSellTicketRequest(new Ticket(flightId,name,tourists, address, seats)));
        Protobuffs.Response response=readResponse();
        if (response.getType()== Protobuffs.Response.Type.Error){
            String errorText=ProtoUtils.getError(response);
            closeConnection();
            throw new ServiceException(errorText);
        }
        System.out.println("Ticket sold - proxy");
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

    private void sendRequest(Protobuffs.Request request)throws ServiceException{
        try {
            System.out.println("Sending request ..."+request);
            //request.writeTo(output);
            request.writeDelimitedTo(output);
            output.flush();
            System.out.println("Request sent.");
        } catch (IOException e) {
            throw new ServiceException("Error sending object "+e);
        }

    }

    private Protobuffs.Response readResponse() throws ServiceException{
        Protobuffs.Response response=null;
        try{
            response=qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }
    private void initializeConnection() throws ServiceException{
        try {
            connection=new Socket(host,port);
            output=connection.getOutputStream();
            //output.flush();
            input=connection.getInputStream();     //new ObjectInputStream(connection.getInputStream());
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


    private void handleUpdate(Protobuffs.Response updateResponse){

        if (updateResponse.getType().equals(Protobuffs.Response.Type.SellTicket)){
            List<Flight> flights=ProtoUtils.getFlights(updateResponse);
            System.out.println("Some tickets were sold!");
            try{
                client.ticketsSold(flights);
            }catch (ServiceException e){
                e.printStackTrace();
            }
        }

    }

    private class ReaderThread implements Runnable{
        public void run() {
            while(!finished){
                try {
                    Protobuffs.Response response=Protobuffs.Response.parseDelimitedFrom(input);
                    System.out.println("response received "+response);

                    if (isUpdateResponse(response.getType())){
                        handleUpdate(response);
                    }else{
                        try {
                            qresponses.put(response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error "+e);
                }
            }
        }
    }

    private boolean isUpdateResponse(Protobuffs.Response.Type type){
        return type == Protobuffs.Response.Type.SellTicket;
    }
}
