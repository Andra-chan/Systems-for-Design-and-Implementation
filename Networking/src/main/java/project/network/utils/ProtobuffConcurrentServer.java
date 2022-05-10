package project.network.utils;

import project.network.protobuffprotocol.ProtoWorker;
import project.services.IServices;

import java.net.Socket;

public class ProtobuffConcurrentServer extends AbsConcurrentServer {
    private IServices server;
    public ProtobuffConcurrentServer(int port, IServices server) {
        super(port);
        this.server = server;
        System.out.println("Project - ProtobuffConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        ProtoWorker worker=new ProtoWorker(server, client);
        Thread tw=new Thread(worker);
        return tw;
    }
}
