package co.edu.uptc.model;

import co.edu.uptc.configs.GlobalConfigs;
import co.edu.uptc.pojos.*;
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
    private boolean writeFile;
    private BufferedOutputStream bos;

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
            switch (GlobalConfigs.infoMode){
                case GlobalConfigs.MODE_INFO1 -> {
                    Info1 inf = new Gson().fromJson(info, Info1.class);
                    Rectangle rectangle = inf.getFigureInformation().getRectangle();
                    model.setRectangle(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
                    model.setInformation(inf.getFigureInformation().getColor(),inf.getPanelInformation().getColor());
                }
                case GlobalConfigs.MODE_INFO2 -> {
                    Info2 inf = new Gson().fromJson(info, Info2.class);
                    int num = inf.getFigureInformation().getRectangle();
                    int x = num >>> 22;
                    int y = (num >>> 12) - (x << 10);
                    int w = (num >>> 6) - ((num >>> 12) << 6);
                    int h = num - ((num >>> 6) << 6);
                    model.setRectangle(x,y,w,h);
                    model.setInformation(inf.getFigureInformation().getColor(), inf.getPanelInformation().getColor());
                }
                case GlobalConfigs.MODE_INFO3 ->{
                    Info3 inf = new Gson().fromJson(info,Info3.class);
                    if (inf.getFileReading().getStatus() != GlobalConfigs.NO_FILE)
                        saveFile(inf.getFileReading());
                    Rectangle rectangle = inf.getFigureInformation().getRectangle();
                    model.setRectangle(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
                    model.setInformation(inf.getFigureInformation().getColor(),inf.getPanelInformation().getColor());
                }
                case GlobalConfigs.MODE_PA -> {
                    Info4 inf = new Gson().fromJson(info, Info4.class);
                    int num = inf.getFigureInformation().getRectangle();
                    int x = num >>> 22;
                    int y = (num >>> 12) - (x << 10);
                    int w = (num >>> 6) - ((num >>> 12) << 6);
                    int h = num - ((num >>> 6) << 6);
                    model.setRectangle(x,y,w,h);
                    model.setInformation(inf.getFigureInformation().getColor(), inf.getPanelInformation().getColor());
                    model.presenter.setFrameInfo(inf.getFrameInformation());
                }
                default -> {
                    Rectangle rectangle = new Gson().fromJson(info,Rectangle.class);
                    model.setRectangle(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
                }
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

    private void saveFile(FileReading fileReading) {
        if (fileReading.getStatus() == GlobalConfigs.START_FILE || fileReading.getStatus() == GlobalConfigs.ALL_FILE) {
            writeFile = !new File(fileReading.getFileName()).exists();
            if (!writeFile)
                writeFile = model.presenter.notifySelection("El archivo " + fileReading.getFileName() + " ya ha" +
                        " sido enviado antes, ¿desea sobreescribirlo?");
        }
        if (writeFile){
            try {
                if (fileReading.getStatus() != GlobalConfigs.ALL_FILE){
                    if (fileReading.getStatus() == GlobalConfigs.START_FILE)
                        bos = new BufferedOutputStream(new FileOutputStream(fileReading.getFileName()));
                    bos.write(fileReading.getData(),0,fileReading.getData().length);
                    if (fileReading.getStatus() == GlobalConfigs.END_FILE){
                        bos.close();
                        model.presenter.notifyMessage("Se ha guardado el archivo" + fileReading.getFileName());
                    }
                }else {
                    bos = new BufferedOutputStream(new FileOutputStream(fileReading.getFileName()));
                    bos.write(fileReading.getData());
                    bos.close();
                    model.presenter.notifyMessage("Se ha guardado el archivo" + fileReading.getFileName());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
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
