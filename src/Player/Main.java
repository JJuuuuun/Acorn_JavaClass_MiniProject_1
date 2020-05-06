package Player;

import Player.Service.CommonServiceImpl;
import Player.Service.ICommonService;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ICommonService comServ = new CommonServiceImpl();
        comServ.showWindow(primaryStage, "../Player.fxml");

    }

}
