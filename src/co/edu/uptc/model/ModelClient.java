package co.edu.uptc.model;

import co.edu.uptc.pojos.FigureInformation;
import co.edu.uptc.pojos.Info;
import co.edu.uptc.pojos.PanelInformation;
import co.edu.uptc.presenter.Contract;

import java.awt.*;

public class ModelClient implements Contract.ModelClient {
    Contract.Presenter presenter;
    private Rectangle rectangle;
    private Info info;
    private Client client;
    boolean isRunning = true;

    public ModelClient() {
        this.rectangle = new Rectangle(0,0,100,100);
        this.info = new Info(new FigureInformation(new Rectangle(0,0,100,100),255)
                ,new PanelInformation(0));
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
    public Info getInformation() {
        return info;
    }

    @Override
    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    @Override
    public void setInformation(Info info) {
        this.info = info;
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
        client = new Client("192.168.1.8",1234,this);
    }
}
