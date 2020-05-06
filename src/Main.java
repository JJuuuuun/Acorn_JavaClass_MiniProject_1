import Service.CommonService;
import Service.ICommonService;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    ICommonService commonService;

    @Override
    public void start(Stage primaryStage) throws Exception {
        commonService = new CommonService();
        commonService.openWindow("");
    }
    public static void main(String[] args) {
        launch(args);
    }
}
