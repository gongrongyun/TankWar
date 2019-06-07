package sample;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameObject {
    public int x;
    public int y;
    public int width;
    public int height;
    public String name;
    public Shape body;
    public Shape blood;
    public Double IP;
    public int belongTo;
    public int serial;
    public int dir;
    public int speed;
    GameObject() {

    }

    public void destroy() { }

    public void getHurt(int attackValue) { }

    public int getAttack() { return 0; }

    public int getLeft() { return 0; }

    public int getRight() { return 0; }

    public int getTop() { return 0; }

    public int getBottom() { return 0; }

    public String getName() { return this.name; }

    public void setX(int x) {}

    public void setY(int y) {}

    public void setCollided() { }

    public int getAddSpeed() { return 0; }

    public int getAddAttack() {
        return 0;
    }

    public void setAttack() { }

    public void setSpeed() { }

    public void setIP(int IP) { }

    public void setAddSpeed(int addSpeed) { }

    public void setAddAttack(int addAttack) { }
//    public boolean CircleToRect(GameObject obj) {
//        if (distance(this.x, this.y, obj)) {
//            this.correctCircle(obj);
//            return true;
//        }
//        return false;
//    }
//
//    private void correctCircle(GameObject obj) {
//        switch (this.dir) {
//            case 1:
//                for (int i = BaseParam.bulletSpeed; i > 0; i--) {
//                    if (!distance(this.x, this.y - i, obj)) {
//                        this.setY(-i);
//                        return;
//                    }
//                }
//                break;
//            case 2:
//                for (int i = BaseParam.bulletSpeed; i > 0; i--) {
//                    if (!distance(this.x - i, this.y, obj)) {
//                        this.setX(-i);
//                        return;
//                    }
//                }
//                break;
//            case 3:
//                for (int i = BaseParam.bulletSpeed; i > 0; i--) {
//                    if (!distance(this.x, this.y + i, obj)) {
//                        this.setY(i);
//                        return;
//                    }
//                }
//                break;
//            case 4:
//                for (int i = BaseParam.bulletSpeed; i > 0; i--) {
//                    if (!distance(this.x + i, this.y, obj)) {
//                        this.setX(i);
//                        return;
//                    }
//                }
//                break;
//        }
//    }

//    private boolean distance(int x, int y, GameObject obj) {
//        int X, Y;
//        if (x < obj.getLeft()) {
//            X = obj.getLeft();
//        }
//        else if (x > obj.getRight()) {
//            X = obj.getRight();
//        } else {
//            X = x;
//        }
//        if (y < obj.getTop()) {
//            Y = obj.getTop();
//        } else if (y > obj.getBottom()) {
//            Y = obj.getBottom();
//        } else {
//            Y = y;
//        }
//        int distance = (int)Math.sqrt((X - x) * (X - x) + (Y - y) * (Y - y));
//        return distance <= this.radius;
//    }

    public boolean RectToRect(GameObject obj, boolean correct) {
        int left = this.getLeft(), right = this.getRight(), top = this.getTop(), bottom = this.getBottom();
        switch (this.dir) {
            case 1:
                top -= this.speed;
                bottom -= this.speed;
                break;
            case 2:
                left -= this.speed;
                right -= this.speed;
                break;
            case 3:
                top += this.speed;
                bottom += this.speed;
                break;
            case 4:
                left += this.speed;
                right += this.speed;
                break;

        }
        if (!(left > obj.getRight() ||
            right < obj.getLeft() ||
            top > obj.getBottom() ||
            bottom < obj.getTop())) {
            if (correct) {
                correct(this, obj);
            }
            return true;
        }
        return false;
    }

    public boolean collisionBorder() {
        switch (this.dir) {
            case 1:
                if (this.getTop() - this.speed < 0) {
                    this.setY(-this.getTop());
                    return true;
                }
                break;
            case 2:
                if (this.getLeft() - this.speed < 0) {
                    this.setX(-this.getLeft());
                    return true;
                }
                break;
            case 3:
                if (this.getBottom() + this.speed >= BaseParam.MapHeight) {
                    this.setY(BaseParam.MapHeight - this.getBottom() - 1);
                    return true;
                }
                break;
            case 4:
                if (this.getRight() + this.speed >= BaseParam.MapWidth) {
                    this.setX(BaseParam.MapWidth - this.getRight() - 1);
                    return true;
                }
                break;
        }
        return false;
    }

    private void correct(GameObject host, GameObject guest) {
        switch (host.dir) {
            case 1:
                host.setY(guest.getBottom() - host.getTop() + 1);
                break;
            case 2:
                host.setX(guest.getRight() - host.getLeft() + 1);
                break;
            case 3:
                host.setY(guest.getTop() - host.getBottom() - 1);
                break;
            case 4:
                host.setX(guest.getLeft() - host.getRight() - 1);
                break;
        }
    }
    public void newStage(int value) {
        Stage stage = new Stage();
        Group group = new Group();
        Button reStart = new Button("ReStart");
        reStart.setId("startBtn");
        reStart.setStyle("-fx-background-color: rgb(58, 181, 230)");
        reStart.setLayoutX(60);
        reStart.setLayoutY(80);
        Button quit = new Button("Quit");
        quit.setId("endBtn");
        quit.setStyle("-fx-background-color: rgb(221, 79, 67)");
        quit.setLayoutX(200);
        quit.setLayoutY(80);
        Text text = new Text();
        if (value == 1) {
            text.setText("下面赢了, 上面的那个操作有点像cxk啊");
        } else {
            text.setText("上面赢了, 下面的那个操作有点像cxk啊");
        }
        text.setLayoutX(50);
        text.setLayoutY(40);
        reStart.setOnAction(e->{
            Loading loading = new Loading();
            loading.load();
            group.getChildren().removeAll(reStart, quit);
            stage.close();
        });
        quit.setOnAction(e->{
            group.getChildren().removeAll(reStart, quit);
            stage.close();
        });
        group.getChildren().addAll(reStart, quit, text);
        Scene scene = new Scene(group, 300, 150);
        stage.setScene(scene);
        stage.setTitle("Game Over");
        stage.show();
    }

}
