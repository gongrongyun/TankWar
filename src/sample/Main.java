package sample;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
    static Group root;
    static Scene scene;
    @Override
    public void start(Stage primaryStage) throws Exception{
        root = new Group();

        Button startBtn = new Button();
        startBtn.setId("startBtn");
        startBtn.setText("Start");

        Button endBtn = new Button();
        endBtn.setId("endBtn");
        endBtn.setText("Quit");

        Text text = new Text("坦克大战");
        text.setId("text");

        startBtn.setOnAction(e -> {
            root.getChildren().removeAll(startBtn, endBtn, text);
            Loading loading = new Loading();
            loading.load();
        });

        endBtn.setOnAction(e -> {
            root.getChildren().remove(endBtn);
            primaryStage.close();
        });

        root.getChildren().addAll(startBtn, endBtn, text);

        scene = new Scene(root, BaseParam.MapWidth, BaseParam.MapHeight);
        scene.getStylesheets().add(getClass().getResource("index.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Tank war");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
