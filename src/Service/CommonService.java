package Service;

import Controller.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CommonService implements ICommonService {
    private Map<String, String> urlMap = new HashMap<>();

    public CommonService() {
        urlMap.put("", "../Form/MenuBar.fxml");
        urlMap.put("Help_Btn", "../Form/Help.fxml");
        urlMap.put("Version_Btn", "../Form/Version.fxml");
        urlMap.put("DirectQuestion_Btn", "../Form/DirectQuestion.fxml");
        urlMap.put("QnA_Btn", "../Form/QnA.fxml");
    }


    private Scene getScene(String btnId) {
        String url = urlMap.get(btnId);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Controller ctrler = loader.getController();
        ctrler.setRoot(root);

        return new Scene(root);
    }

    @Override
    public void openWindow(String btnId) {
        Scene scene = getScene(btnId);
        Stage s = new Stage();

        s.setTitle("About");
        s.setScene(scene);
        s.show();
    }

    @Override
    public void changeWindow(ActionEvent event, String btnId) {
        Scene scene = getScene(btnId);
        Parent root = (Parent)event.getSource();
        BorderPane border = (BorderPane)root.getScene().getRoot();

        border.setCenter(scene.getRoot());
    }


}
