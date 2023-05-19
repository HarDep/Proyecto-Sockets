package co.edu.uptc.model;

import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private Conection conection;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    ModelServer server;
    private final List<Socket> sockets;
    private boolean isRemoved = false;

    public Server(String host, int port, ModelServer server) {
        this.server = server;
        this.sockets = new ArrayList<>();
        innit(host, port);
        connect();
    }

    public void innit(String host, int port){
        conection = new Conection();
        conection.setType("server");
        conection.setPort(port);
        conection.setHost(host);
        conection.connect();
    }
    public void send(){
        if (!sockets.isEmpty()){
            String info =new Gson().toJson(server.getSquare());
            synchronized (sockets){
                for (Socket socket:sockets) {
                    try {
                        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                        dataOutputStream.writeUTF(info);
                    } catch (IOException e) {
                        sockets.remove(socket);
                        isRemoved = true;
                        break;
                    }
                }
            }
        }
        if (isRemoved){
            isRemoved = false;
            send();
        }
    }
    public void connect(){
        Thread thread = new Thread(){
            @Override
            public void run() {
                while (server.isRunning){
                    try {
                        Socket socket = conection.serverSocket.accept();
                        synchronized (sockets) {
                            sockets.add(socket);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
        thread.start();
    }
}
