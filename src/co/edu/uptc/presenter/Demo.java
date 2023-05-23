package co.edu.uptc.presenter;

import co.edu.uptc.model.ModelClient;
import co.edu.uptc.model.ModelServer;
import co.edu.uptc.view.DashBoard;

public class Demo {
    private Contract.ModelServer modelServer;
    private Contract.ModelClient modelClient;
    private final Contract.Presenter presenter;
    private final Contract.View view;

    public Demo(boolean isServer) {
        if (isServer){
            modelServer = new ModelServer();
            presenter = new Presenter();
            view = new DashBoard();
            createServerMVP();
        }else{
            modelClient = new ModelClient();
            presenter = new Presenter();
            view = new DashBoard();
            createClientMVP();
        }
    }

    private void createClientMVP() {
        modelClient.setPresenter(presenter);
        presenter.setModelClient(modelClient);
        presenter.setView(view);
        view.setPresenter(presenter);
        view.paintRectangle();
    }
    private void createServerMVP() {
        modelServer.setPresenter(presenter);
        presenter.setModelServer(modelServer);
        presenter.setView(view);
        view.setPresenter(presenter);
        view.paintRectangle();
    }

    public void start(){
        presenter.start();
    }
}
