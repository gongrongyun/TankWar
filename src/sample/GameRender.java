package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.util.Duration;

import javafx.scene.image.Image;
import java.util.ArrayList;

public class GameRender {
    private int interval;
    private GraphicsContext ctx;
    private Image background;
    public ArrayList<Shape> elements = new ArrayList<>();

    GameRender() {
        this.interval = 16;
        this.background = new Image("./resource/bg-grass.png");
    }

    public GameRender createMainMap() {
        Canvas canvas = new Canvas(BaseParam.MapWidth, BaseParam.MapHeight);
        this.ctx = canvas.getGraphicsContext2D();
        Main.root.getChildren().add(canvas);
        return this;
    }

    public void start() {
        Timeline animation = new Timeline(
            new KeyFrame(Duration.millis(this.interval), e-> {
                if (MainController.working) {
                    this.render();
                }
            })
        );
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }

    private void render() {
        this.refreshMap();
            for(Shape element:elements) {
                this.ctx.beginPath();

                if(element.fillStyle != null) {
                    this.ctx.setFill(element.fillStyle);
                }
                else {
                    this.ctx.setStroke(element.strokeStyle);
                    this.ctx.setLineWidth(element.lineWidth);
                }

                if (element.text != null) {
                    this.ctx.fillText(element.text, element.position[0], element.position[1]);
                }
                switch (element.type) {
                    case "rect":
                        this.ctx.fillRect(
                            element.position[0],
                            element.position[1],
                            element.width,
                            element.height
                        );
                        break;
                    case "line":
                        this.ctx.strokeLine(
                            element.startpos[0],
                            element.startpos[1],
                            element.endpos[0],
                            element.endpos[1]
                        );
                        break;
                    case "circle":
                        this.ctx.fillArc(
                            element.position[0],
                            element.position[1],
                            element.radius*2,
                            element.radius*2,
                            0,
                            360,
                            ArcType.OPEN
                        );
                        break;
                    case "image":
                        this.ctx.drawImage(
                            element.image,
                            element.position[0],
                            element.position[1],
                            element.width,
                            element.height
                        );
                    default:
                        break;
                }
                this.ctx.closePath();
            }
    }

    private void refreshMap() {
        this.ctx.beginPath();
//        this.ctx.setFill(Color.rgb(187, 187, 187));
        this.ctx.drawImage(this.background, 0, 0, BaseParam.MapWidth, BaseParam.MapHeight);
        this.ctx.closePath();
    }

    public void addChild(Shape shp) {
        this.elements.add(shp);
    }
    public void removeChild(Shape shp) {
        this.elements.remove(shp);
    }
}