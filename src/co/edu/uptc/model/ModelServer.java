package co.edu.uptc.model;

import co.edu.uptc.configs.GlobalConfigs;
import co.edu.uptc.pojos.FigureInformation;
import co.edu.uptc.pojos.Info;
import co.edu.uptc.pojos.PanelInformation;
import co.edu.uptc.presenter.Contract;

import java.awt.*;

public class ModelServer implements Contract.ModelServer {
    Contract.Presenter presenter;
    private final Rectangle rectangle;
    private final Info info;
    boolean isRunning = true;
    private Server server;

    public ModelServer() {
        this.rectangle = new Rectangle(GlobalConfigs.RECTANGLE_X,GlobalConfigs.RECTANGLE_Y,
                GlobalConfigs.RECTANGLE_WIDTH,GlobalConfigs.RECTANGLE_HEIGHT);
        this.info = new Info(new FigureInformation(rectangle,GlobalConfigs.RECTANGLE_COLOR)
                ,new PanelInformation(GlobalConfigs.PANEL_COLOR));
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
        server = new Server("", GlobalConfigs.PORT,this);
    }
}
