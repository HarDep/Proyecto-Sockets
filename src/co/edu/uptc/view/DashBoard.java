package co.edu.uptc.view;

import co.edu.uptc.configs.GlobalConfigs;
import co.edu.uptc.presenter.Contract;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class DashBoard extends JFrame implements Contract.View {
    private RectanglePanel rectanglePanel;
    Contract.Presenter presenter;
    private final boolean isServer;

    public DashBoard(boolean isServer) {
        this.isServer = isServer;
        putConfigs();
        createComponents();
    }

    private void createComponents() {
        rectanglePanel = new RectanglePanel(this);
        if (isServer && (GlobalConfigs.infoMode == GlobalConfigs.MODE_INFO2 ||
        GlobalConfigs.infoMode == GlobalConfigs.MODE_INFO3 || GlobalConfigs.infoMode == GlobalConfigs.MODE_INFO1)){
            add(rectanglePanel, BorderLayout.CENTER);
            SelectionPanel selectionPanel = new SelectionPanel(this);
            add(selectionPanel,BorderLayout.WEST);
        }else
            add(rectanglePanel);
    }

    private void putConfigs() {
        setTitle(GlobalConfigs.TITLE);
        setSize(GlobalConfigs.FRAME_DIMENSION);
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
    public void paintInfo() {
        rectanglePanel.repaint();
    }

    @Override
    public void notifyWarning(String value) {
        JOptionPane.showMessageDialog(this,value,GlobalConfigs.TITLE,JOptionPane.WARNING_MESSAGE);
    }

    @Override
    public void notifyMessage(String value) {
        JOptionPane.showMessageDialog(this,value);
    }

    @Override
    public boolean notifySelection(String value) {
        return JOptionPane.showConfirmDialog(this,value,"",JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION;
    }
}
