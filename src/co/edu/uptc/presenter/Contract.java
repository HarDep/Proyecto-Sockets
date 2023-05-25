package co.edu.uptc.presenter;

import co.edu.uptc.pojos.FigureInformation;
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
        void setRectangle(Rectangle rectangle);
        void setInformation(Info info);
        void paintRectangle();
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
        void paintRectangle();
        void start();
        void terminate();
        void notifyWarning(String value);
    }
    interface View{
        void setPresenter(Contract.Presenter presenter);
        void start();
        void paintRectangle();
        void notifyWarning(String value);
    }
}
