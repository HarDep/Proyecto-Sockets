package co.edu.uptc.view;


import co.edu.uptc.pojos.Info1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RectanglePanel extends JPanel {
    private final DashBoard dashBoard;

    public RectanglePanel(DashBoard dashBoard) {
        this.dashBoard = dashBoard;
        MouseActions mouseActions = new MouseActions();
        addMouseMotionListener(mouseActions);
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D graphics2D = (Graphics2D) g;
        if (dashBoard.presenter != null) {
            Info1 info1 = dashBoard.presenter.getInformation();
            setBackground(new Color(info1.getPanelInformation().getColor()));
            graphics2D.setColor(new Color(info1.getFigureInformation().getColor()));
            graphics2D.fill(info1.getFigureInformation().getRectangle());
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
