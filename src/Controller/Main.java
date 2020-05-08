package Controller;

import Service.CommonService;
import Service.ICommonService;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ICommonService comServ = new CommonService();
        comServ.showWindow(primaryStage, "../FXML/Player.fxml");

    }
}
