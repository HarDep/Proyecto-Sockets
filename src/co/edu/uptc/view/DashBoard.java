package co.edu.uptc.view;

import co.edu.uptc.presenter.Contract;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class DashBoard extends JFrame implements Contract.View {
    private RectanglePanel rectanglePanel;
    Contract.Presenter presenter;
    boolean isServer;

    public DashBoard(boolean isServer) {
        this.isServer = isServer;
        putConfigs();
        createComponents();
    }

    private void createComponents() {
        rectanglePanel = new RectanglePanel(this);
        if (isServer){
            add(rectanglePanel, BorderLayout.CENTER);
            SelectionPanel selectionPanel = new SelectionPanel(this);
            add(selectionPanel,BorderLayout.WEST);
        }else
            add(rectanglePanel);
    }

    private void putConfigs() {
        setTitle("Demo");
        setLayout(new BorderLayout());
        setSize(new Dimension(700,700));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                presenter.terminate();
                System.exit(0);
            }
        });
    }

    @Override
    public void setPresenter(Contract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void start() {
        setVisible(true);
    }

    @Override
    public void paintRectangle() {
        rectanglePanel.repaint();
    }

    @Override
    public void notifyWarning(String value) {
        JOptionPane.showMessageDialog(this,value,"Demo",JOptionPane.WARNING_MESSAGE);
    }
}
