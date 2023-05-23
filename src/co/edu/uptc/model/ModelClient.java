package co.edu.uptc.model;

import co.edu.uptc.presenter.Contract;

import java.awt.*;

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
        //client = new Client("10.4.44.43",1234,this);
        client = new Client("192.168.1.4",1234,this);
        //client = new Client("10.4.74.41",9021,this);
        //client = new Client("10.4.73.177",1234,this);
    }
}
