package co.edu.uptc.pojos;

import java.awt.*;

public class FigureInformation {
    private Rectangle rectangle;
    private int Color;

    public FigureInformation(Rectangle rectangle, int color) {
        this.rectangle = rectangle;
        this.Color = color;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public int getColor() {
        return Color;
    }

    public void setColor(int color) {
        this.Color = color;
    }
}
