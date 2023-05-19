package co.edu.uptc.model;

import co.edu.uptc.presenter.Contract;
import com.google.gson.Gson;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Client {
    Conection conection;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    ModelClient client;

    public Client(String host, int port, ModelClient client) {
        this.client = client;
        innit(host, port);
        receive();
    }

    public void innit(String host, int port){
        conection = new Conection();
        conection.setType("client");
        conection.setPort(port);
        conection.setHost(host);
        conection.connect();
    }

    public void receive(){
        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    String info;
                    while (client.isRunning){
                        dataInputStream = new DataInputStream(conection.socket.getInputStream());
                        info = dataInputStream.readUTF();
                        Rectangle rectangle = new Gson().fromJson(info, Rectangle.class);
                        client.setRectangle(rectangle);
                        client.paintRectangle();
                    }
                } catch (IOException e) {
                    //throw new RuntimeException(e);
                    System.out.println("desconectado");
                }
            }
        };
        thread.start();
    }
}
