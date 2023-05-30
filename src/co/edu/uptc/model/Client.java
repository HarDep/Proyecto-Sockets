package co.edu.uptc.model;

import com.google.gson.Gson;

import java.awt.*;
import java.io.*;
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
                    //getInfo();
                    getFile();
                }
            }
        };
        thread.start();
    }

    private void getInfo(){
        try {
            String info;
            dataInputStream = new DataInputStream(connection.socket.getInputStream());
            info = dataInputStream.readUTF();
            Rectangle rectangle = new Gson().fromJson(info, Rectangle.class);
            model.setRectangle(rectangle);
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

    public void getFile(){
        try {
            System.out.println("111111");
            DataInputStream dis=new DataInputStream(connection.socket.getInputStream());
            System.out.println("2222");
            String file = dis.readUTF();
            File file1 = new File(file);
            if (!file1.exists()){
                byte[] receivedData = new byte[1024];
                BufferedInputStream bis = new BufferedInputStream(connection.socket.getInputStream());
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                int in;
                while (true){
                    in = bis.read(receivedData);
                    System.out.println("1: "+in);
                        if (in==-1) break;
                    bos.write(receivedData,0,in);
                    //System.out.println("55555");
                }
                bos.close();
                //bis.close();
                System.out.println("ya");
            } else
                model.presenter.notifyWarning("El archivo ya se ha enviado");
        } catch (SocketException e) {
            model.presenter.notifyWarning("Se ha desconectado el servidor");
            connection.socket = null;
            connect();
        } catch (IOException e) {
            e.printStackTrace();
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
