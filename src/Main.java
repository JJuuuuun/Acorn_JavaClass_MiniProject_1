import Controller.AbstractController;
import Controller.Controller;
import Service.CommonService;
import Service.ICommonService;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application {
    ICommonService commonService;
    //CommonService 새로운 창을 띄우고 닫고, 변경하는 폼 인터페이스
    @Override
    public void start(Stage primaryStage) {
        commonService = new CommonService();
        commonService.openWindow("RootScene");

        AbstractController rootController = commonService.getController("RootScene.fxml");
        commonService.changeWindow(rootController.getRoot(), "Player", Pos.BOTTOM_CENTER);
        commonService.changeWindow(rootController.getRoot(), "Main", Pos.TOP_LEFT);

        //0512 add
        Controller mainController = (Controller)commonService.getController("main.fxml");
        mainController.setRootController(rootController);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
