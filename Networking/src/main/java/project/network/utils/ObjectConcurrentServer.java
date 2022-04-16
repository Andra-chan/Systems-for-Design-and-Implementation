package project.network.utils;

import project.network.objectprotocol.ClientObjectWorker;
import project.services.IServices;

import java.net.Socket;

public class ObjectConcurrentServer extends AbsConcurrentServer {
    private IServices server;
    public ObjectConcurrentServer(int port, IServices server) {
        super(port);
        this.server = server;
        System.out.println("Project - ObjectConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        ClientObjectWorker worker=new ClientObjectWorker(server, client);
        Thread tw=new Thread(worker);
        return tw;
    }


}
