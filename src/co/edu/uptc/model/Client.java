package co.edu.uptc.model;

import com.google.gson.Gson;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;

public class Client {
    Connection connection;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    ModelClient model;

    public Client(String host, int port, ModelClient model) {
        this.model = model;
        innit(host, port);
        receive();
    }

    public void innit(String host, int port){
        connection = new Connection();
        connection.setType("client");
        connection.setPort(port);
        connection.setHost(host);
        connect();
    }

    public void receive(){
        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    String info;
                    while (model.isRunning){
                        dataInputStream = new DataInputStream(connection.socket.getInputStream());
                        info = dataInputStream.readUTF();
                        Rectangle rectangle = new Gson().fromJson(info, Rectangle.class);
                        model.setRectangle(rectangle);
                        model.paintRectangle();
                    }
                } catch (SocketException e) {
                    model.presenter.notifyWarning("Se ha desconectado el servidor");
                    connection.socket = null;
                    connect();
                    receive();
                } catch (IOException e) {
                    model.presenter.notifyWarning("Error técnico + \n" + e.getMessage());
                }
            }
        };
        thread.start();
    }

    private void connect(){
        while (connection.socket == null){
            try {
                connection.connect();
            } catch (ConnectException e){
                model.presenter.notifyWarning("No se pudo conectar al sevidor");
            } catch (IOException e) {
                model.presenter.notifyWarning("Error técnico + \n" + e.getMessage());
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                model.presenter.notifyWarning("Error técnico + \n" + e.getMessage());
            }
        }
    }
}
