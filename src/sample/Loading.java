package sample;

public class Loading {
    public static GameRender gameRender;

    public void load() {
        gameRender = new GameRender();
        gameRender.createMainMap().start();
        MainController main = new MainController();
        main.createScene();
    }
}
