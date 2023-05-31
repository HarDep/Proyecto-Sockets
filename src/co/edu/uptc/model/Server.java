package co.edu.uptc.model;

import co.edu.uptc.configs.GlobalConfigs;
import co.edu.uptc.pojos.*;
import com.google.gson.Gson;

import java.awt.*;
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
    private Info1 inf;
    private String info;

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

    public void putInfo(){
        inf = model.getInformation();
        switch (GlobalConfigs.infoMode){
            case GlobalConfigs.MODE_INFO1 -> info = new Gson().toJson(inf);
            case GlobalConfigs.MODE_INFO2 ->{
                Rectangle rectangle = inf.getFigureInformation().getRectangle();
                int x = rectangle.x << 22;
                int y = rectangle.y << 12;
                int w = rectangle.width << 6;
                int h = rectangle.height;
                int num = x + y + w + h;
                info =new Gson().toJson(new Info2(new FigureInformation2(num,inf.getFigureInformation().getColor()),
                        inf.getPanelInformation()));
            }
            case GlobalConfigs.MODE_INFO3 -> putFile(null, null, GlobalConfigs.NO_FILE);
            default -> info = new Gson().toJson(inf.getFigureInformation().getRectangle());
        }
    }

    public void send(){
        if (!sockets.isEmpty()){
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

    public void sendFile(File file){
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            int count = 0;
            int length = (int) file.length();
            byte[] byteArray = new byte[1024];
            while (count != length) {
                count += bis.read(byteArray);
                putFile(file.getName(),byteArray, ( count == 1024 ? GlobalConfigs.START_FILE :
                        ( count == length ? GlobalConfigs.END_FILE : GlobalConfigs.KEEP_FILE ) ) );
                send();
            }
            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void putFile(String fileName, byte[] data, int status){
        inf = model.getInformation();
        Info3 inf3 = new Info3(inf.getFigureInformation(),inf.getPanelInformation(),
                new FileReading(fileName,data,status));
        info = new Gson().toJson(inf3);
    }
}
