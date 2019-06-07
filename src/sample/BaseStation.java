package sample;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BaseStation extends GameObject{
    private Paint color;
    private Shape stationStroke;

    BaseStation(int x, int y, Paint color, int beLongTo) {
        this.name = "baseStation";
        this.width = BaseParam.homeWidth;
        this.height = BaseParam.homeHeight;
        this.IP = BaseParam.homeIP;
        this.x = x;
        this.y = y;
        this.belongTo = beLongTo;
        this.color = color;
        this.stationStroke = new Shape();
        this.body = new Shape();
        this.createStation();
    }

    private void createStation() {
        this.stationStroke.saveStrokeRect(this.x, this.y, 120, this.height).saveStroke(this.color, 1);
        this.body.saveRect(this.x, this.y, this.width, this.height).saveFill(this.color);
        Loading.gameRender.addChild(this.body);
        Loading.gameRender.addChild(this.stationStroke);
    }

    public void getHurt(int attackValue) {
        this.IP -= attackValue;
        this.body.width = Math.max((int)(this.IP / BaseParam.homeIP * this.width), 0);
        if (this.IP <= 0) {
            MainController.working = false;
            this.newStage(this.belongTo);
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
