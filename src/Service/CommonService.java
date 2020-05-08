package Service;

import Controller.AbstractController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
//창 열고 닫기

public class CommonService implements ICommonService {

    @Override
    public AbstractController showWindow(Stage s, String formPath) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(formPath));
        Parent root = null;
        try {
            root = loader.load();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        AbstractController controller = loader.getController();
        controller.setRoot(root);
        s.setTitle("Test");
        s.setScene(new Scene(root));
        s.show();
        return controller;
    }


}
