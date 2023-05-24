package co.edu.uptc.view;


import co.edu.uptc.pojos.FigureInformation;
import co.edu.uptc.pojos.Info;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RectanglePanel extends JPanel {
    private final DashBoard dashBoard;
    private Rectangle rectangle;
    private Info info;

    public RectanglePanel(DashBoard dashBoard) {
        this.dashBoard = dashBoard;
        MouseActions mouseActions = new MouseActions();
        addMouseMotionListener(mouseActions);
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setColor(Color.RED);
        if (dashBoard.presenter != null) {
            rectangle = dashBoard.presenter.getSquare();
            /*if () {
                info = dashBoard.presenter.getInformation();
                setBackground(new Color(info.getPanelInformation().getColor()));
                graphics2D.setColor(new Color(info.getFigureInformation().getColor()));
                graphics2D.draw(info.getFigureInformation().getRectangle());
            }*/
        }
    }

    class MouseActions extends MouseAdapter{
        @Override
        public void mouseDragged(MouseEvent e) {
            dashBoard.presenter.moveSquare(e.getX(),e.getY());
            repaint();
        }
    }
}
