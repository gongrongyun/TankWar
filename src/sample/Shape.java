package sample;

import javafx.scene.image.Image;
import javafx.scene.paint.Paint;

public class Shape {
    public String type;
    public int[] position;
    public int height;
    public int width;
    public int radius;
    public int lineWidth;
    public int[] startpos;
    public int[] endpos;
    public Paint fillStyle;
    public Paint strokeStyle;
    public int top;
    public int bottom;
    public int left;
    public int right;
    public String text;
    public Image image;
    Shape() {
        this.top = 0;
        this.bottom = 0;
        this.left = 0;
        this.right = 0;
    }

    public Shape saveRect(int x, int y, int width, int height) {
        this.type = "rect";
        this.position = new int[2];
        this.position[0] = x;
        this.position[1] = y;
        this.width = width;
        this.height = height;
        return this;
    }

    public Shape saveCircle(int x, int y, int radius) {
        this.type = "circle";
        this.position = new int[2];
        this.position[0] = x;
        this.position[1] = y;
        this.radius = radius;
        return this;
    }

    public Shape saveLine(int x1, int y1, int x2, int y2) {
        this.type = "line";
        this.startpos = new int[2];
        this.endpos = new int[2];
        this.startpos[0] = x1;
        this.startpos[1] = y1;
        this.endpos[0] = x2;
        this.endpos[1] = y2;
        return this;
    }

    public Shape saveImage(int x, int y, int width, int height, Image image) {
        this.type = "image";
        this.position = new int[2];
        this.position[0] = x;
        this.position[1] = y;
        this.width = width;
        this.height = height;
        this.image = image;
        return this;
    }

    public Shape saveStrokeRect(int x, int y, int width, int height) {
        this.type = "strokeRect";
        this.position = new int[2];
        this.position[0] = x;
        this.position[1] = y;
        this.width = width;
        this.height = height;
        return this;
    }

    public Shape saveFill(Paint fillStyle) {
        this.fillStyle = fillStyle;
        return this;
    }

    public Shape saveStroke(Paint strokeStyle, int lineWidth) {
        this.strokeStyle = strokeStyle;
        this.lineWidth = lineWidth;
        return this;
    }
    public Shape saveText(String str) {
        this.text = str;
        return this;
    }
}