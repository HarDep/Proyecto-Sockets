package co.edu.uptc.model;

import com.google.gson.Gson;

import java.io.*;
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
            model.presenter.notifyWarning("Error técnico + \n" + e.getMessage());
        }
    }
    public void send(){
        if (!sockets.isEmpty()){
            String info =new Gson().toJson(model.getSquare());
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

    public void sendFile(File file){
        if (!sockets.isEmpty()){
            String info =new Gson().toJson(model.getSquare());
            synchronized (sockets){
                for (Socket socket:sockets) {
                    try {
                        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                        BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
                        DataOutputStream dos=new DataOutputStream(socket.getOutputStream());
                        dos.writeUTF(file.getName());
                        bis.transferTo(bos);
                        /*int in;
                        byte[] byteArray = new byte[8192];
                        while ((in = bis.read(byteArray)) != -1){
                            bos.write(byteArray,0,in);
                        }*/
                        bis.close();
                        bos.close();
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
            sendFile(file);
        }
    }
    public void connect(){
        Thread thread = new Thread(){
            @Override
            public void run() {
                while (model.isRunning){
                    try {
                        Socket socket = connection.serverSocket.accept();
                        synchronized (sockets) {
                            sockets.add(socket);
                        }
                    } catch (Exception e) {
                        model.presenter.notifyWarning("Error técnico + \n" + e.getMessage());
                    }
                }
            }
        };
        thread.start();
    }
}
