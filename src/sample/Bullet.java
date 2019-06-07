package sample;

import javafx.scene.image.Image;

public class Bullet extends GameObject {
    private boolean collided;
    private int attack;

    Bullet(int x, int y, int dir, int belongTo, int attack) {
        super();
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.speed = BaseParam.bulletSpeed;
        this.name = "bullet";
        this.belongTo = belongTo;
        this.collided = false;
        this.attack = attack;
        this.width = 15;
        this.height = 15;
        this.createBullet();
    }

    private void createBullet() {
        this.body = new Shape();
        this.body.saveImage(this.x, this.y, this.width, this.height, new Image("./resource/bullet/bullet.png"));
        Loading.gameRender.addChild(this.body);
    }

    public void destroy() {
        Loading.gameRender.removeChild(this.body);
        MainController.bullets.remove(this);
    }

    public int getAttack() {
        return this.attack;
    }

    public int getLeft() {
        return this.x;
    }

    public int getTop() {
        return this.y;
    }

    public int getBottom() {
        return this.y + this.height - 1;
    }

    public int getRight() {
        return this.x + this.width - 1;
    }

    public void setX(int x) {
        this.body.position[0] += x;
        this.x += x;
    }

    public void setY(int y) {
        this.body.position[1] += y;
        this.y += y;
    }

    public boolean getCollided() {
        return this.collided;
    }

    public void setCollided() {
        this.collided = true;
    }
}
