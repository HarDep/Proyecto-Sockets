package co.edu.uptc.presenter;

import java.awt.*;

public interface Contract {
    interface ModelServer {
        void setPresenter(Contract.Presenter presenter);
        void moveSquare(int x,int y);
        Rectangle getSquare();
        void terminate();
    }
    interface ModelClient{
        void setPresenter(Contract.Presenter presenter);
        Rectangle getSquare();
        void setRectangle(Rectangle rectangle);
        void paintRectangle();
        void terminate();
    }
    interface Presenter{
        void setView(Contract.View view);
        void setModelClient(ModelClient modelClient);
        void setModelServer(ModelServer modelServer);
        void moveSquare(int x, int y);
        Rectangle getSquare();
        void paintRectangle();
        void start();
        void terminate();
    }
    interface View{
        void setPresenter(Contract.Presenter presenter);
        void start();
        void paintRectangle();
    }
}
