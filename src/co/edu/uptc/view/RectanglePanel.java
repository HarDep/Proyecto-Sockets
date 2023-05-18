package co.edu.uptc.view;


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
        graphics2D.setColor(Color.RED);
        Rectangle rectangle = dashBoard.presenter.getSquare();
        if (rectangle != null)
            graphics2D.draw(rectangle);
    }

    class MouseActions extends MouseAdapter{
        @Override
        public void mouseDragged(MouseEvent e) {
            dashBoard.presenter.moveSquare(e.getX(),e.getY());
            repaint();
        }
    }
}
