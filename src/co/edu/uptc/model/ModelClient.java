package co.edu.uptc.model;

import co.edu.uptc.configs.GlobalConfigs;
import co.edu.uptc.pojos.FigureInformation;
import co.edu.uptc.pojos.Info1;
import co.edu.uptc.pojos.PanelInformation;
import co.edu.uptc.presenter.Contract;

import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ModelClient implements Contract.ModelClient {
    Contract.Presenter presenter;
    private final Rectangle rectangle;
    private final Info1 info1;
    private Client client;
    boolean isRunning = true;

    public ModelClient() {
        this.rectangle = new Rectangle(GlobalConfigs.RECTANGLE_X,GlobalConfigs.RECTANGLE_Y,
                GlobalConfigs.RECTANGLE_WIDTH,GlobalConfigs.RECTANGLE_HEIGHT);
        this.info1 = new Info1(new FigureInformation(rectangle,GlobalConfigs.RECTANGLE_COLOR)
                ,new PanelInformation(GlobalConfigs.PANEL_COLOR));
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
    public Info1 getInformation() {
        return info1;
    }

    @Override
    public void setRectangle(int x,int y, int w, int h) {
        this.rectangle.setLocation(x,y);
        this.rectangle.width = w;
        this.rectangle.height = h;
    }

    @Override
    public void setInformation(int figColor, int panelColor) {
        this.info1.getFigureInformation().setColor(figColor);
        this.info1.getPanelInformation().setColor(panelColor);
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
        client = new Client(ip, GlobalConfigs.PORT,this);
    }
}
