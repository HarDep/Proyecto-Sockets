package co.edu.uptc.presenter;

import co.edu.uptc.pojos.Info;

import java.awt.*;

public interface Contract {
    interface ModelServer {
        void setPresenter(Contract.Presenter presenter);
        void moveSquare(int x,int y);
        Rectangle getSquare();
        Info getInformation();
        void terminate();
        void start();
    }
    interface ModelClient{
        void setPresenter(Contract.Presenter presenter);
        Rectangle getSquare();
        Info getInformation();
        void setRectangle(int x,int y, int w, int h);
        void setInformation(int figColor, int panelColor);
        void paintInfo();
        void terminate();
        void start();
    }
    interface Presenter{
        void setView(Contract.View view);
        void setModelClient(ModelClient modelClient);
        void setModelServer(ModelServer modelServer);
        void moveSquare(int x, int y);
        Rectangle getSquare();
        Info getInformation();
        void paintInfo();
        void start();
        void terminate();
        void notifyWarning(String value);
    }
    interface View{
        void setPresenter(Contract.Presenter presenter);
        void start();
        void paintInfo();
        void notifyWarning(String value);
    }
}
