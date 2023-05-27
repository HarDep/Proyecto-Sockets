package co.edu.uptc.model;

import co.edu.uptc.presenter.Contract;

import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ModelClient implements Contract.ModelClient {
    Contract.Presenter presenter;
    private Rectangle rectangle;
    private Client client;
    boolean isRunning = true;

    public ModelClient() {
        this.rectangle = new Rectangle(0,0,100,100);
    }

    @Override
    public void setPresenter(Contract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public Rectangle getSquare() {
        return (Rectangle) rectangle.clone();
    }

    @Override
    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    @Override
    public void paintRectangle() {
        if (presenter != null)
            presenter.paintRectangle();
    }

    @Override
    public void terminate() {
        isRunning = false;
    }

    @Override
    public void start() {
        String ip;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            System.err.print("error en local host");
            throw new RuntimeException(e);
        }
        client = new Client(ip,1234,this);
    }
}
