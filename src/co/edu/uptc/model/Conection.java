package co.edu.uptc.model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Conection {
    protected ServerSocket serverSocket;
    protected Socket socket;
    private String type;
    private String host;
    private int port;

    public void connect(){
        switch (type){
            case "server" -> {
                try {
                    serverSocket = new ServerSocket(port);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            case "client" -> {
                try {
                    socket = new Socket(host,port);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
