/**
package project.network.utils;

import project.services.IServices;

import java.net.Socket;

public class RpcConcurrentServer extends AbsConcurrentServer {
    private IServices server;
    public RpcConcurrentServer(int port, IServices server) {
        super(port);
        this.server = server;
        System.out.println("Project - RpcConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        // ClientRpcWorker worker=new ClientRpcWorker(server, client);
        ClientRpcReflectionWorker worker=new ClientRpcReflectionWorker(server, client);

        Thread tw=new Thread(worker);
        return tw;
    }

    @Override
    public void stop(){
        System.out.println("Stopping services ...");
    }
}
 **/
