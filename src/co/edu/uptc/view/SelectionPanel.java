package co.edu.uptc.view;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class SelectionPanel extends JPanel {
    private final DashBoard dashBoard;
    private File file;
    private JLabel fileName;

    public SelectionPanel(DashBoard dashBoard) {
        this.dashBoard = dashBoard;
        putConfigs();
        addComponents();
    }

    private void addComponents() {
        JButton select = new JButton("seleccionar archivo");
        add(select);
        fileName = new JLabel("No seleccionado");
        add(fileName);
        select.addActionListener(e -> actionSelectFile());
        JButton enviar = new JButton("Enviar archivo");
        add(enviar);
        enviar.addActionListener(e -> sendFile());
    }

    private void sendFile() {
        if (file != null)
            dashBoard.presenter.sendFile(file);
        else
            dashBoard.notifyWarning("No se ha seleccionado un archivo");
    }

    private void actionSelectFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(this);
        file = fileChooser.getSelectedFile();
        if (file != null && result != JFileChooser.CANCEL_OPTION) {
            fileName.setText(file.getName());
        }
    }

    private void putConfigs() {
        setPreferredSize(new Dimension(200,100));
        setBackground(Color.yellow);
    }
}
