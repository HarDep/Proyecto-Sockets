package co.edu.uptc.model;

import co.edu.uptc.pojos.FigureInformation;
import co.edu.uptc.pojos.Info;
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
                while (model.isRunning) {
                    getInfo();
                }
            }
        };
        thread.start();
        //System.out.println(thread.getPriority());
        //thread.setPriority(6);
    }

    private void getInfo(){
        try {
            String info;
            dataInputStream = new DataInputStream(connection.socket.getInputStream());
            info = dataInputStream.readUTF();
            //Rectangle rectangle = new Gson().fromJson(info, Rectangle.class);
            //FigureInformation figureInformation = new Gson().fromJson(info, FigureInformation.class);
            //System.out.println(figureInformation);
            Info inf = new Gson().fromJson(info, Info.class);
            //model.setRectangle(rectangle);
            model.setInformation(inf);
            model.paintRectangle();
        } catch (SocketException e) {
            model.presenter.notifyWarning("Se ha desconectado el servidor");
            connection.socket = null;
            connect();
            getInfo();
        } catch (IOException e) {
            model.presenter.notifyWarning("Error técnico + \n" + e.getMessage());
        }
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
        }
    }
}
