package co.edu.uptc.model;

import co.edu.uptc.presenter.Contract;

import java.awt.*;

public class ModelServer implements Contract.ModelServer {
    Contract.Presenter presenter;
    private final Rectangle rectangle;
    boolean isRunning = true;
    private final Server server;

    public ModelServer() {
        rectangle = new Rectangle(0,0,100,100);
        server = new Server("10.4.65.123",1234,this);
    }

    @Override
    public void setPresenter(Contract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void moveSquare(int x, int y) {
        rectangle.setLocation(x,y);
        server.send();
    }

    @Override
    public Rectangle getSquare() {
        return (Rectangle) rectangle.clone();
    }

    @Override
    public void terminate() {
        isRunning = false;
    }
}
