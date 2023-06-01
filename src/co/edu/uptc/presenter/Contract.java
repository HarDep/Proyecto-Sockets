package co.edu.uptc.presenter;

import co.edu.uptc.pojos.FrameInformation;
import co.edu.uptc.pojos.Info1;

import java.io.File;

public interface Contract {
    interface ModelServer {
        void setPresenter(Contract.Presenter presenter);
        void moveSquare(int x,int y);
        void setColorPanel(int color);
        void setColorRectangle(int color);
        Info1 getInformation();
        void terminate();
        void start();
        void sendFile(File file);
    }
    interface ModelClient{
        void setPresenter(Contract.Presenter presenter);
        Info1 getInformation();
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
        void setColorPanel(int color);
        void setColorRectangle(int color);
        void moveSquare(int x, int y);
        Info1 getInformation();
        void paintInfo();
        void start();
        void terminate();
        void sendFile(File file);
        void notifyWarning(String value);
        void notifyMessage(String value);
        boolean notifySelection(String value);
        void setFrameInfo(FrameInformation frameInformation);
    }
    interface View{
        void setPresenter(Contract.Presenter presenter);
        void start();
        void paintInfo();
        void notifyWarning(String value);
        void notifyMessage(String value);
        boolean notifySelection(String value);
        void setFrameInfo(FrameInformation frameInformation);
    }
}
