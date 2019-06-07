package sample;

import javafx.scene.image.Image;

public class Obstacle extends GameObject{

    private Image image;

    Obstacle(int x, int y, int width, int height, Image image, String name) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
        this.IP = BaseParam.brickIP;
        this.create();
    }

    private void create() {
        this.body = new Shape();
        this.body.saveImage(this.x, this.y, this.width, this.height, this.image);
        Loading.gameRender.addChild(this.body);
    }

    public void getHurt(int attackValue) {
        if (this.IP > 0) {
            this.IP -= attackValue;
        } else {
            MainController.mapBlocks.remove(this);
            Loading.gameRender.removeChild(this.body);
        }
    }

    public int getLeft() {
        return this.x;
    }

    public int getRight() {
        return this.x + this.width - 1;
    }

    public int getTop() {
        return this.y;
    }

    public int getBottom() {
        return this.y + this.height - 1;
    }
}
