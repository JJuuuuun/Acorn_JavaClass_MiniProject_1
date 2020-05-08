import Controller.AbstractController;
import Controller.Controller;
import Service.CommonService;
import Service.ICommonService;
import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    ICommonService commonService;
    @Override
    public void start(Stage primaryStage) throws Exception {
        commonService = new CommonService();
        commonService.openWindow("");

        AbstractController controller = commonService.getController("main.fxml");
        if (controller == null)
            System.out.println("null");
        commonService.changeWindow(controller.getRoot(), "Player");

    }
    public static void main(String[] args) {
        launch(args);
    }
}
