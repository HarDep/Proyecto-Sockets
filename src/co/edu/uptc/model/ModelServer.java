package co.edu.uptc.model;

import co.edu.uptc.configs.GlobalConfigs;
import co.edu.uptc.pojos.FigureInformation;
import co.edu.uptc.pojos.Info1;
import co.edu.uptc.pojos.PanelInformation;
import co.edu.uptc.presenter.Contract;

import java.awt.*;
import java.io.File;

public class ModelServer implements Contract.ModelServer {
    Contract.Presenter presenter;
    private final Rectangle rectangle;
    private final Info1 info1;
    boolean isRunning = true;
    private Server server;

    public ModelServer() {
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
    public void moveSquare(int x, int y) {
        rectangle.setLocation(x,y);
        server.putInfo();
        server.send();
    }

    @Override
    public void setColorPanel(int color) {
        this.info1.getPanelInformation().setColor(color);
        server.putInfo();
        server.send();
    }

    @Override
    public void setColorRectangle(int color) {
        this.info1.getFigureInformation().setColor(color);
        server.putInfo();
        server.send();
    }

    @Override
    public Info1 getInformation() {
        return info1;
    }

    @Override
    public void terminate() {
        isRunning = false;
    }

    @Override
    public void start() {
        server = new Server("", GlobalConfigs.PORT,this);
    }

    @Override
    public void sendFile(File file) {
        server.sendFile(file);
    }
}
