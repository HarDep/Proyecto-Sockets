package co.edu.uptc.model;

import co.edu.uptc.pojos.FigureInformation;
import co.edu.uptc.pojos.Info;
import co.edu.uptc.pojos.PanelInformation;
import co.edu.uptc.presenter.Contract;

import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ModelClient implements Contract.ModelClient {
    Contract.Presenter presenter;
    private Rectangle rectangle;
    private Info info;
    private Client client;
    boolean isRunning = true;

    public ModelClient() {
        this.rectangle = new Rectangle(0,0,60,60);
        this.info = new Info(new FigureInformation(rectangle,255)
                ,new PanelInformation(0));
        //this.info = new Info(new FigureInformation(604391976,255)
          //      ,new PanelInformation(0));
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
    public void setRectangle(int x,int y, int w, int h) {
        this.rectangle.setLocation(x,y);
        this.rectangle.width = w;
        this.rectangle.height = h;
    }

    @Override
    public void setInformation(int figColor, int panelColor) {
        this.info.getFigureInformation().setColor(figColor);
        this.info.getPanelInformation().setColor(panelColor);
    }

    @Override
    public void paintInfo() {
        if (presenter != null)
            presenter.paintInfo();
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
