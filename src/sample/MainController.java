package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Stack;

public class MainController {
    static boolean working;
    private ArrayList<BaseStation> baseStations = new ArrayList<>();
    static ArrayList<Role> enemys = new ArrayList<>();
    static ArrayList<Prop> props = new ArrayList<>();
    static ArrayList<Bullet> bullets = new ArrayList<>();
    static ArrayList<GameObject> mapBlocks = new ArrayList<>();
    private Map<KeyCode, Integer> direction = new HashMap<>();
    private Map<Integer, Boolean> propsCreate = new HashMap<>();
    private Stack<KeyCode> role0Pressed;
    private Stack<KeyCode> role1Pressed;
    private KeyCode[] keyCode;
    private int interval;
    private int[][] propPos;
    private String[] propName;
    static Role role1;
    static Role role0;

    MainController() {
        working = true;
        this.interval = 16;
        this.keyCode = new KeyCode[]{
                KeyCode.UP, KeyCode.LEFT, KeyCode.DOWN, KeyCode.RIGHT, KeyCode.W,
                KeyCode.A, KeyCode.S, KeyCode.D, KeyCode.CONTROL, KeyCode.SPACE
        };
        this.role1Pressed = new Stack<>();
        this.role0Pressed = new Stack<>();
        this.propPos = new int[][]{{100, 100}, {780, 100}, {450, 300}, {100, 480}, {780, 480}};
        this.propName = new String[]{"cover", "speed", "Ip", "attack"};
    }

    public void createScene() {
        MainMap map = new MainMap();
        map.createMap();
        this.createObject();
        this.resetKeyCode();

        Main.scene.setOnKeyPressed(e -> {
            addEventListener("pressed", e.getCode());
        });
        Main.scene.setOnKeyReleased(e -> {
            addEventListener("released", e.getCode());
        });

        Timeline animation = new Timeline(
            new KeyFrame(Duration.millis(this.interval), e->{
                if (working) {
                    this.update();
                }
            })
        );
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
        Timeline updateProps = new Timeline(
            new KeyFrame(Duration.seconds(5), e-> {
                createProps(propPos, propName);
            })
        );
        updateProps.setCycleCount(Timeline.INDEFINITE);
        updateProps.play();
    }

    private void addEventListener(String type, KeyCode e) {
        if (type == "pressed") {
            if (e == KeyCode.SPACE) {
                role1.lunch();
            } else if (e == KeyCode.CONTROL) {
                role0.lunch();
            } else {
                if (e == KeyCode.DOWN || e == KeyCode.UP || e == KeyCode.LEFT || e == KeyCode.RIGHT) {
                    if (!this.role0Pressed.empty() && this.role0Pressed.peek() != e) {
                        this.role0Pressed.push(e);
                    }
                    role0.moving = true;
                    role0.dir = this.direction.get(e);
                } else if (e == KeyCode.W || e == KeyCode.A || e == KeyCode.S || e == KeyCode.D) {
                    if (!this.role1Pressed.empty() && this.role1Pressed.peek() != e) {
                        this.role1Pressed.push(e);
                    }
                    role1.moving = true;
                    role1.dir = this.direction.get(e);
                }
            }
        } else {
            if (e == KeyCode.DOWN || e == KeyCode.UP || e == KeyCode.LEFT || e == KeyCode.RIGHT) {
                if (this.role0Pressed.empty()) {
                    role0.moving = false;
                }

            } else if (e == KeyCode.W || e == KeyCode.A || e == KeyCode.S || e == KeyCode.D) {
                if (role1Pressed.empty()) {
                    role1.moving = false;
                }
            }
        }
    }

    private void resetKeyCode() {
        for (int i = 0; i < 8; i++) {
            this.direction.put(this.keyCode[i], i % 4 + 1);
        }
    }

    private void createObject() {
        BaseStation baseStation1 = new BaseStation(390, 0, Color.rgb(255, 0, 0), 1);
        BaseStation baseStation2 = new BaseStation(390, BaseParam.MapHeight - BaseParam.homeHeight, Color.rgb(0, 0, 255), 0);
        this.baseStations.add(baseStation1);
        this.baseStations.add(baseStation2);
        role1 = new Role(430, 130, 1, 3, false);
        role0 = new Role(430, 430, 0, 1, false);
        enemys.add(new Role(10, 10, 2, 3, true));
        enemys.add(new Role(850, 10, 3, 3, true));
        enemys.add(new Role(10, 550, 4, 1, true));
        enemys.add(new Role(850, 550, 5, 1, true));
        for (int i = 0; i < 5; i++) {
            props.add(new Prop(propPos[i][0], propPos[i][1], propName[(int)(Math.random()*4)], i));
            propsCreate.put(i, true);
        }
    }

    private void createProps(int[][] pos, String[] str) {
        for (int i = 0; i < 5; i++) {
            if (!this.propsCreate.get(i)) {
                props.add(new Prop(pos[i][0], pos[i][1], str[(int)(Math.random()*4)], i));
                this.propsCreate.put(i, true);
            }
        }
    }

    private void update() {
        this.updateRoleParam();

        if (role0.moving) {
            this.collision(role0);
        }
        if (role1.moving) {
            this.collision(role1);
        }

        for (int i = 0; i < enemys.size(); i++) {
            this.enemyCollision(enemys.get(i));
        }

        for (int i = 0; i < bullets.size(); i++) {
            if (bullets.get(i).getCollided()) {
                bullets.get(i).destroy();
                break;
            }
            this.bulletCollision(bullets.get(i));
        }
    }

    private void updateRoleParam() {
        if (role0.getAddSpeed() > 0) {
            role0.setAddSpeed(-4);
            role0.setSpeed();
        }
        if (role1.getAddSpeed() > 0) {
            role1.setAddSpeed(-4);
            role1.setSpeed();
        }
        if (role0.getAddAttack() > 0) {
            role0.setAddAttack(-8);
            role0.setAttack();
        }
        if (role1.getAddAttack() > 0) {
            role1.setAddAttack(-8);
            role1.setAttack();
        }
    }

    private void enemyCollision(GameObject obj) {
        if (obj.collisionBorder()) {
            obj.dir = (int)(Math.random() * 4) + 1;
            return;
        }
        if (obj.RectToRect(role1, true)) {
            obj.dir = (int)(Math.random() * 4) + 1;
            return;
        }
        if (obj.RectToRect(role0, true)) {
            obj.dir = (int)(Math.random() * 4) + 1;
            return;
        }
        for (int i = 0; i < this.baseStations.size(); i++) {
            if (obj.RectToRect(this.baseStations.get(i), true)) {
                obj.dir = (int)(Math.random() * 4) + 1;
                return;
            }
        }
        for (int i = 0; i < mapBlocks.size(); i++) {
            if (obj.RectToRect(mapBlocks.get(i), true)) {
                obj.dir = (int)(Math.random() * 4) + 1;
                return;
            }
        }

        for (int i = 0; i < enemys.size(); i++) {
            if (enemys.get(i) != obj && obj.RectToRect(enemys.get(i), true)) {
                return;
            }
        }
        move(obj);
    }

    private void collision(GameObject obj) {
        if (obj.collisionBorder()) {
            return;
        }
        if (obj == role0) {
            if (obj.RectToRect(role1, true)) {
                return;
            }
        } else {
            if (obj.RectToRect(role0, true)) {
                return;
            }
        }
        for (int i = 0; i < this.baseStations.size(); i++) {
            if (obj.RectToRect(this.baseStations.get(i), true)) {
                return;
            }
        }

        for (int i = 0; i < mapBlocks.size(); i++) {
            if (obj.RectToRect(mapBlocks.get(i), true)) {
                return;
            }
        }

        for (int i = 0; i < enemys.size(); i++) {
            if (obj.RectToRect(enemys.get(i), true)) {
                return;
            }
        }

        for (int i = 0; i < props.size(); i++) {
            if (props.get(i).effective) {
                if (obj.RectToRect(props.get(i), false)) {
                    propsCreate.put(props.get(i).getPosition(), false);
                    switch (props.get(i).getName()) {
                        case "cover":
                            props.get(i).getCover(obj);
                            break;
                        case "speed":
                            obj.setAddSpeed(BaseParam.potionSpeed);
                            props.get(i).destroy();
                            break;
                        case "Ip":
                            obj.setIP(BaseParam.potionIP.intValue());
                            System.out.print("ip\n");
                            props.get(i).destroy();
                            break;
                        case "attack":
                            obj.setAddAttack(BaseParam.potionAttack);
                            props.get(i).destroy();
                            break;
                    }
                }
            }

        }

        move(obj);
    }

    private void bulletCollision(GameObject obj) {
        if (obj.collisionBorder()) {
            obj.setCollided();
            return;
        }

        for (int i = 0; i < this.baseStations.size(); i++) {
            if (obj.RectToRect(this.baseStations.get(i), true)) {
                obj.setCollided();
                this.baseStations.get(i).getHurt(obj.getAttack());
                return;
            }
        }

        for (int i = 0; i < mapBlocks.size(); i++) {
            if (obj.RectToRect(mapBlocks.get(i), true)) {
                obj.setCollided();
                if (mapBlocks.get(i).getName() == "wood") {
                    mapBlocks.get(i).getHurt(obj.getAttack());
                }
                return;
            }
        }

        if (obj.belongTo != role0.serial) {
            if (obj.RectToRect(role0, true)) {
                role0.getHurt(obj.getAttack());
                obj.setCollided();
                return;
            }
        }
        if (obj.belongTo != role1.serial) {
            if (obj.RectToRect(role1, true)) {
                role1.getHurt(obj.getAttack());
                obj.setCollided();
                return;
            }
        }


        for (int i = 0; i < enemys.size(); i++) {
            if (obj.belongTo != enemys.get(i).serial) {
                if (obj.RectToRect(enemys.get(i), true)) {
                    enemys.get(i).getHurt(obj.getAttack());
                    obj.setCollided();
                    return;
                }
            }
        }

        for (int i = 0; i < props.size(); i++) {
            if (!props.get(i).effective
                && obj.belongTo != props.get(i).belongTo
                && obj.RectToRect(props.get(i), true)
            ) {
                obj.setCollided();
                return;
            }
        }
        move(obj);
    }

    private void move(GameObject obj) {
        switch (obj.dir) {
            case 1:
                obj.setY(-obj.speed);
                break;
            case 2:
                obj.setX(-obj.speed);
                break;
            case 3:
                obj.setY(obj.speed);
                break;
            case 4:
                obj.setX(obj.speed);
                break;
        }
    }
}
