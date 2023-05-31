package co.edu.uptc.model;

import co.edu.uptc.pojos.FigureInformation;
import co.edu.uptc.pojos.Info;
import co.edu.uptc.pojos.Info2;
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
    boolean isInfoRectangleUnit = true;

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
        Thread thread = new Thread(() -> {
            while (model.isRunning) {
                getInfo();
            }
        });
        thread.start();
    }

    private void getInfo(){
        try {
            String info;
            dataInputStream = new DataInputStream(connection.socket.getInputStream());
            info = dataInputStream.readUTF();

            if (isInfoRectangleUnit){
                Info2 inf = new Gson().fromJson(info, Info2.class);
                int num = inf.getFigureInformation().getRectangle();
                int x = num >>> 22;
                int y = (num >>> 12) - (x << 10);
                int w = (num >>> 6) - ((num >>> 12) << 6);
                int h = num - ((num >>> 6) << 6);
                model.setRectangle(x,y,w,h);
                model.setInformation(inf.getFigureInformation().getColor(), inf.getPanelInformation().getColor());
            }else {
                Info inf = new Gson().fromJson(info, Info.class);
                Rectangle rectangle = inf.getFigureInformation().getRectangle();
                model.setRectangle(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
                model.setInformation(inf.getFigureInformation().getColor(),inf.getPanelInformation().getColor());
            }

            model.paintInfo();
        } catch (SocketException e) {
            model.presenter.notifyWarning("Se ha desconectado el servidor");
            connection.socket = null;
            connect();
            getInfo();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void connect(){
        while (connection.socket == null){
            try {
                connection.connect();
            } catch (ConnectException e){
                model.presenter.notifyWarning("No se pudo conectar al servidor");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
