package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.util.Duration;


public class Role extends GameObject{
    private int addSpeed;
    private int addAttack;
    private int attack;
    private boolean isEnemy;
    private Timeline enemyLunch;
    public boolean moving;
    private Image[] images;
    private Image image;

    Role(int x, int y, int serial, int dir, boolean isEnemy) {
        super();
        this.isEnemy = isEnemy;
        this.dir = dir;
        this.width = BaseParam.roleWidth;
        this.height = BaseParam.roleHeight;
        this.x = x;
        this.y = y;
        this.IP = BaseParam.roleIP;
        this.speed = BaseParam.roleSpeed;
        this.name = "role";
        this.serial = serial;
        this.attack = BaseParam.attackValue;
        this.addSpeed = 0;
        this.addAttack = 0;
        this.moving = false;
        this.images = new Image[4];
        this.setImage();
        this.create();
    }

    public void lunch() {
        Bullet bullet = new Bullet(this.x+this.width/2-7, this.y+this.height/2-7, this.dir, this.serial, this.attack);
        MainController.bullets.add(bullet);
    }

    private void create() {
        if (this.isEnemy) {
            this.speed = BaseParam.enemySpeed;
            this.IP = BaseParam.enemyIP;
            this.enemyLunch();
        }
        this.body = new Shape();
        this.body.saveImage(this.x, this.y, this.width, this.height, this.image);
        this.blood = new Shape();
        this.blood.saveRect(this.x, this.y - 20, this.width, 10)
                .saveFill(Color.rgb(255, 198, 109))
                .saveText(this.IP.intValue() + "/" + this.IP.intValue());
        Loading.gameRender.addChild(this.body);
        Loading.gameRender.addChild(this.blood);
        this.animation();
    }

    private void enemyLunch() {
        this.enemyLunch = new Timeline(
            new KeyFrame(Duration.seconds(2), e->{
                if (Math.random() > 0.6) {
                    lunch();
                }
                if (Math.random() > 0.5) {
                    this.dir = (int)(Math.random() * 4) + 1;
                }
            })
        );
        this.enemyLunch.setCycleCount(Timeline.INDEFINITE);
        this.enemyLunch.play();
    }

    private void setImage() {
        if (!this.isEnemy) {
            this.images[0] = new Image("./resource/tank-u.jpg");
            this.images[1] = new Image("./resource/tank-l.jpg");
            this.images[2] = new Image("./resource/tank-d.jpg");
            this.images[3] = new Image("./resource/tank-r.jpg");
        } else {
            this.images[0] = new Image("./resource/enemy-u.png");
            this.images[1] = new Image("./resource/enemy-l.png");
            this.images[2] = new Image("./resource/enemy-d.png");
            this.images[3] = new Image("./resource/enemy-r.png");
        }
    }

    private void animation() {
        Timeline animation = new Timeline(
                new KeyFrame(Duration.millis(32), e-> {
                    if (MainController.working) {
                        this.body.image = this.images[this.dir - 1];
                    }
                })
        );
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }



    public void getHurt(int attackValue) {
        this.IP -= attackValue;
        this.resetBlood();
        if (this.IP <= 40) {
                this.blood.fillStyle = Color.rgb(255, 30, 20);
        }
            if (this.IP <= 0) {
                if (!this.isEnemy) {
                    MainController.working = false;
                    this.newStage(this.serial);
                } else {
                    this.enemyLunch.stop();
                    MainController.enemys.remove(this);
                    Loading.gameRender.removeChild(this.body);
                    Loading.gameRender.removeChild(this.blood);
                }
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

    public int getAddSpeed() {
        return this.addSpeed;
    }

    public int getAddAttack() {
        return this.addAttack;
    }

    public void setAttack() {
        this.attack = BaseParam.attackValue + this.addAttack / 100;
    }

    public void setSpeed() {
        this.speed = BaseParam.roleSpeed + this.addSpeed / 200;
    }

    public void setIP(int IP) {
        if (this.IP + IP <= BaseParam.roleIP) {
            this.IP += IP;
        } else {
            this.IP = BaseParam.roleIP;
            System.out.print(this.IP + "\n");
        }
        this.resetBlood();
    }

    private void resetBlood() {
        int ip = this.isEnemy ? BaseParam.enemyIP.intValue() : BaseParam.roleIP.intValue();
        this.blood.text = this.IP.intValue() + "/" + ip;
        this.blood.width = Math.max((int)(this.IP / ip * BaseParam.roleWidth), 0);
        if (this.IP > 40) {
            this.blood.fillStyle = Color.rgb(255, 198, 109);
        }
    }

    public void setAddSpeed(int addSpeed) {
        if (this.addSpeed + addSpeed < 0) {
            this.addSpeed = 0;
        } else {
            this.addSpeed += addSpeed;
        }
    }

    public void setAddAttack(int addAttack) {
        if (this.addAttack + addAttack < 0) {
            this.addAttack = 0;
        } else {
            this.addAttack += addAttack;
        }
    }

    public void setX(int x) {
        this.body.position[0] += x;
        this.blood.position[0] += x;
        this.x += x;
    }

    public void setY(int y) {
        this.body.position[1] += y;
        this.blood.position[1] += y;
        this.y += y;
    }
}
