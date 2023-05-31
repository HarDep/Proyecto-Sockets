package co.edu.uptc.presenter;

import co.edu.uptc.model.ModelClient;
import co.edu.uptc.model.ModelServer;
import co.edu.uptc.view.DashBoard;

public class SocketsProject {
    private Contract.ModelServer modelServer;
    private Contract.ModelClient modelClient;
    private final Contract.Presenter presenter;
    private final Contract.View view;

    public SocketsProject(boolean isServer) {
        if (isServer){
            modelServer = new ModelServer();
            presenter = new Presenter();
            view = new DashBoard(isServer);
            createServerMVP();
        }else{
            modelClient = new ModelClient();
            presenter = new Presenter();
            view = new DashBoard(isServer);
            createClientMVP();
        }
    }

    private void createClientMVP() {
        modelClient.setPresenter(presenter);
        presenter.setModelClient(modelClient);
        presenter.setView(view);
        view.setPresenter(presenter);
        view.paintInfo();
    }
    private void createServerMVP() {
        modelServer.setPresenter(presenter);
        presenter.setModelServer(modelServer);
        presenter.setView(view);
        view.setPresenter(presenter);
        view.paintInfo();
    }

    public void start(){
        presenter.start();
    }
}
