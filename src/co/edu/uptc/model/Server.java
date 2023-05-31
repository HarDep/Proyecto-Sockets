package co.edu.uptc.model;

import co.edu.uptc.configs.GlobalConfigs;
import co.edu.uptc.pojos.FigureInformation2;
import co.edu.uptc.pojos.Info1;
import co.edu.uptc.pojos.Info2;
import com.google.gson.Gson;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private Connection connection;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    ModelServer model;
    private final List<Socket> sockets;
    private boolean isRemoved = false;

    public Server(String host, int port, ModelServer model) {
        this.model = model;
        this.sockets = new ArrayList<>();
        innit(host, port);
        connect();
    }

    public void innit(String host, int port){
        connection = new Connection();
        connection.setType("server");
        connection.setPort(port);
        connection.setHost(host);
        try {
            connection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void send(){
        if (!sockets.isEmpty()){
            Info1 inf = model.getInformation();
            String info;
            switch (GlobalConfigs.infoMode){
                case 1 -> info = new Gson().toJson(inf);
                case 2 ->{
                    Rectangle rectangle = inf.getFigureInformation().getRectangle();
                    int x = rectangle.x << 22;
                    int y = rectangle.y << 12;
                    int w = rectangle.width << 6;
                    int h = rectangle.height;
                    int num = x + y + w + h;
                    info =new Gson().toJson(new Info2(new FigureInformation2(num,inf.getFigureInformation().getColor()),
                            inf.getPanelInformation()));
                }
                case 3 -> info = "";
                default -> info = new Gson().toJson(inf.getFigureInformation().getRectangle());
            }
            synchronized (sockets){
                for (Socket socket:sockets) {
                    try {
                        dataOutputStream = new DataOutputStream(socket.getOutputStream());
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
        Thread thread = new Thread(() -> {
            while (model.isRunning){
                try {
                    Socket socket = connection.serverSocket.accept();
                    synchronized (sockets) {
                        sockets.add(socket);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
