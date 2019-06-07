package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Prop extends GameObject{
    private Timeline follow;
    private int disappear;
    private int position;
    public boolean effective;

    Prop(int x, int y, String str, int position) {
        this.name = str;
        this.x = x;
        this.y = y;
        this.height = 25;
        this.width = 25;
        this.disappear = 180;
        this.effective = true;
        this.position = position;
        this.create();
    }

    private void create() {
        this.body = new Shape();
        this.body.saveImage(this.x, this.y, this.width, this.height, new Image("./resource/prop.png"));
        Loading.gameRender.addChild(this.body);
    }

    public void getCover(GameObject obj) {
        this.belongTo = obj.serial;
        this.effective = false;
        Role object = (obj == MainController.role0) ? MainController.role0 : MainController.role1;
        this.width = 60;
        this.height = 60;
        this.body.saveCircle(this.x + this.width / 2, this.y + this.height / 2, 30)
                .saveFill(Color.rgb(60, 160, 90, 0.8));
        this.follow = new Timeline(
            new KeyFrame(Duration.millis(16), e-> {
                if (this.disappear > 0) {
                    this.x = object.getLeft() - 10;
                    this.y = object.getTop() - 10;
                    this.body.position[0] = this.x;
                    this.body.position[1] = this.y;
                    disappear--;
                } else {
                    destroy();
                    this.follow.stop();
                }
            })
        );
        follow.setCycleCount(Timeline.INDEFINITE);
        follow.play();
    }

    public void destroy() {
        Loading.gameRender.removeChild(this.body);
        MainController.props.remove(this);
    }

    public int getPosition() {
        return this.position;
    }

    public String getName() {
        return this.name;
    }

    public int getTop() {
        return this.y;
    }

    public int getBottom() {
        return this.y + this.height - 1;
    }

    public int getLeft() {
        return this.x;
    }

    public int getRight() {
        return this.x + this.width - 1;
    }
}
