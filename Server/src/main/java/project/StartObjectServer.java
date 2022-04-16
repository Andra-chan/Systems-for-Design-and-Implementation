package project;

import project.network.utils.AbstractServer;
import project.network.utils.ObjectConcurrentServer;
import project.network.utils.ServerException;
import project.repository.database.FlightDbRepository;
import project.repository.database.TicketDbRepository;
import project.repository.database.UserDbRepository;
import project.server.ServerImpl;
import project.services.IServices;

import java.io.IOException;
import java.util.Properties;

public class StartObjectServer {
    private static int defaultPort=55555;
    public static void main(String[] args) {
        Properties serverProps=new Properties();
        try {
            serverProps.load(StartObjectServer.class.getResourceAsStream("/projectserver.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find projectserver.properties "+e);
            return;
        }

        FlightDbRepository flightDbRepository = new FlightDbRepository(serverProps);
        UserDbRepository userDbRepository = new UserDbRepository(serverProps);
        TicketDbRepository ticketDbRepository = new TicketDbRepository(serverProps);
        IServices service = new ServerImpl(flightDbRepository, ticketDbRepository, userDbRepository);

        int ServerPort=defaultPort;
        try {
            ServerPort = Integer.parseInt(serverProps.getProperty("project.server.port"));
        }catch (NumberFormatException nef){
            System.err.println("Wrong  Port Number"+nef.getMessage());
            System.err.println("Using default port "+defaultPort);
        }
        System.out.println("Starting server on port: "+ServerPort);
        AbstractServer server = new ObjectConcurrentServer(ServerPort, service);
        try {
            server.start();
        } catch (ServerException e) {
            System.err.println("Error starting the server" + e.getMessage());
        }
    }
}
