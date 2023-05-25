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
            //System.out.println(info);
            //Rectangle rectangle = new Gson().fromJson(info, Rectangle.class);
            //FigureInformation figureInformation = new Gson().fromJson(info, FigureInformation.class);
            //System.out.println(figureInformation);
            Info2 inf = new Gson().fromJson(info, Info2.class);
            //model.setRectangle(rectangle);

            int num = inf.getFigureInformation().getRectangle();

            int x = num >>> 22;

            int y = (num >>> 12) - (x << 10);

            int w = (num >>> 6) - ((num >>> 12) << 6);

            int h = num - ((num >>> 6) << 6);


            //System.out.println(Integer.toBinaryString(num) + "--bef");
            //System.out.println(Integer.toBinaryString(x));
            //System.out.println(Integer.toBinaryString(y));
            //System.out.println(Integer.toBinaryString(w)+ "dest");
            //System.out.println(Integer.toBinaryString(h));

            model.setInformation(new Info(new FigureInformation(new Rectangle(x,y,w,h),
                    inf.getFigureInformation().getColor()),inf.getPanelInformation()));
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
