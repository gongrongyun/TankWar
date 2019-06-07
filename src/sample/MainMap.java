package sample;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;


public class MainMap {
    private int blockWidth = 30;
    private int blockHeight = 30;

    MainMap() {

    }

    public void createMap() {
        for(int i = 0; i <= BaseParam.MapHeight / this.blockHeight; i++) {
            Shape line = new Shape();
            line.saveLine(0, i*this.blockHeight, BaseParam.MapWidth, i*this.blockHeight)
                    .saveStroke(Color.rgb(135, 135, 135), 1);
            Loading.gameRender.addChild(line);
        }
        for(int i = 0; i <= BaseParam.MapWidth / this.blockWidth; i++) {
            Shape line = new Shape();
            line.saveLine(i*this.blockWidth, 0, i*this.blockWidth, BaseParam.MapHeight)
                    .saveStroke(Color.rgb(135, 135, 135), 1);
            Loading.gameRender.addChild(line);
        }
        for (int i = 0; i < 10; i++) {
            this.addObstacle(300 + i * 30, 180, "./resource/iron.gif", "iron");
            this.addObstacle(300 + i * 30, 390, "./resource/iron.gif", "iron");
        }
        for (int i = 0; i < 10; i++) {
            this.addObstacle(i * 30, 180, "./resource/wood.gif", "wood");
            this.addObstacle(i * 30, 390, "./resource/wood.gif", "wood");
            this.addObstacle(600 + i * 30, 180, "./resource/wood.gif", "wood");
            this.addObstacle(600 + i * 30, 390, "./resource/wood.gif", "wood");
        }
        for (int i = 0; i < 4; i++) {
            this.addObstacle(360, i * 30, "./resource/wood.gif", "wood");
            this.addObstacle(360, 600 - 30 * (i + 1), "./resource/wood.gif", "wood");
            this.addObstacle(510, i * 30, "./resource/wood.gif", "wood");
            this.addObstacle(510, 600 - 30 * (i + 1), "./resource/wood.gif", "wood");
        }
        for (int i = 0; i < 4; i ++) {
            this.addObstacle(390 + i * 30, 90, "./resource/wood.gif", "wood");
            this.addObstacle(390 + i * 30, 480, "./resource/wood.gif", "wood");
        }
        for (int i = 0; i < 3; i++) {
            this.addObstacle(60 + i * 90, 270, "./resource/iron.gif", "iron");
            this.addObstacle(60 + i * 90, 300, "./resource/iron.gif", "iron");
            this.addObstacle(630 + i * 90, 270, "./resource/iron.gif", "iron");
            this.addObstacle(630 + i * 90, 300, "./resource/iron.gif", "iron");
        }
    }

    private void addObstacle(int x, int y, String str, String name) {
        Obstacle obstacle = new Obstacle(x, y, this.blockWidth, this.blockHeight, new Image(str), name);
        MainController.mapBlocks.add(obstacle);
    }

}
