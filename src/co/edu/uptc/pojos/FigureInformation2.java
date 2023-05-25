package co.edu.uptc.pojos;

import java.awt.*;

public class FigureInformation2 {
    private int rectangle;
    private int Color;

    public FigureInformation2(int rectangle, int color) {
        this.rectangle = rectangle;
        this.Color = color;
    }

    public int getRectangle() {
        return rectangle;
    }

    public void setRectangle(int rectangle) {
        this.rectangle = rectangle;
    }

    public int getColor() {
        return Color;
    }

    public void setColor(int color) {
        this.Color = color;
    }
}
