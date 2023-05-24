package co.edu.uptc.presenter;

import co.edu.uptc.pojos.FigureInformation;
import co.edu.uptc.pojos.Info;

import java.awt.*;

public class Presenter implements Contract.Presenter {
    private Contract.ModelServer modelServer;
    private Contract.ModelClient modelClient;
    private Contract.View view;
    @Override
    public void setView(Contract.View view) {
        this.view = view;
    }

    @Override
    public void setModelClient(Contract.ModelClient modelClient) {
        this.modelClient = modelClient;
    }

    @Override
    public void setModelServer(Contract.ModelServer modelServer) {
        this.modelServer = modelServer;
    }

    @Override
    public void moveSquare(int x, int y) {
        if (modelServer != null)
            modelServer.moveSquare(x, y);
    }

    @Override
    public Rectangle getSquare() {
        if (modelServer != null)
            return modelServer.getSquare();
        return modelClient.getSquare();
    }

    @Override
    public Info getInformation() {
        return modelClient.getInformation();
    }

    @Override
    public void paintRectangle() {
        view.paintRectangle();
    }

    @Override
    public void start() {
        view.start();
        if (modelServer != null)
            modelServer.start();
        else
            modelClient.start();
    }

    @Override
    public void terminate() {
        if (modelServer != null)
            modelServer.terminate();
        else
            modelClient.terminate();
    }

    @Override
    public void notifyWarning(String value) {
        view.notifyWarning(value);
    }
}
