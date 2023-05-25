package co.edu.uptc.model;

import co.edu.uptc.pojos.FigureInformation;
import co.edu.uptc.pojos.Info;
import co.edu.uptc.pojos.PanelInformation;
import co.edu.uptc.presenter.Contract;

import java.awt.*;

public class ModelServer implements Contract.ModelServer {
    Contract.Presenter presenter;
    private final Rectangle rectangle;
    private Info info;
    boolean isRunning = true;
    private Server server;

    public ModelServer() {
        //solo se puede enviar w y h hasta 64 ya que solo son 6 bits
        rectangle = new Rectangle(0,0,60,60);
        this.info = new Info(new FigureInformation(rectangle,255)
                ,new PanelInformation(0));
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
    public Info getInformation() {
        return info;
    }

    @Override
    public void terminate() {
        isRunning = false;
    }

    @Override
    public void start() {
        server = new Server("",1234,this);
    }
}
