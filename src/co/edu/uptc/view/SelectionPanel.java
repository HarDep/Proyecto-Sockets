package co.edu.uptc.view;

import co.edu.uptc.configs.GlobalConfigs;

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
        JButton changeColor = new JButton("Color de panel");
        add(changeColor);
        JButton changeRectColor = new JButton("Color de rectángulo");
        add(changeRectColor);
        changeColor.addActionListener(e -> changeColor());
        changeRectColor.addActionListener(e -> changeRectColor());
        if (GlobalConfigs.infoMode == GlobalConfigs.MODE_INFO3){
            JButton select = new JButton("seleccionar archivo");
            add(select);
            fileName = new JLabel("No seleccionado");
            add(fileName);
            select.addActionListener(e -> actionSelectFile());
            JButton enviar = new JButton("Enviar archivo");
            add(enviar);
            enviar.addActionListener(e -> sendFile());
        }
    }

    private void changeRectColor() {
        JColorChooser colorChooser = new JColorChooser();
        int resultado = JOptionPane.showConfirmDialog(dashBoard, colorChooser,
                "Selecciona un color", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (resultado == JOptionPane.OK_OPTION && colorChooser.getColor()!= null)
            dashBoard.presenter.setColorRectangle(colorChooser.getColor().getRGB());
    }

    private void changeColor() {
        JColorChooser colorChooser = new JColorChooser();
        int resultado = JOptionPane.showConfirmDialog(dashBoard, colorChooser,
                "Selecciona un color", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (resultado == JOptionPane.OK_OPTION && colorChooser.getColor()!= null)
            dashBoard.presenter.setColorPanel(colorChooser.getColor().getRGB());
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

