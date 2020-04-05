package bank;

import bank.gui.Menus;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
    public static Scene currentScene; //used for back arrow that has yet to be implemented
    private static Stage globalStage;
    public static void main(String[] args) {
        launch(args);
    }
    private Menus menu = new Menus();

    public static void update(Scene scene){
        currentScene = scene;
        globalStage.setScene(scene);
        globalStage.show();
    }
    @Override
    public void start(Stage stage) throws Exception {
        globalStage = stage;
        update(menu.mainMenu());
    }
}
