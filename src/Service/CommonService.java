package Service;

import Controller.AbstractController;
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
//        urlMap.put("", "../Form/MenuBar.fxml");

        // HomePage Main Form
        urlMap.put("", "../FXML/main.fxml");
        urlMap.put("btnhome", "../FXML/main.fxml");
        urlMap.put("btnlogin", "../FXML/login.fxml");
        urlMap.put("btnjoin", "../FXML/join.fxml");
        urlMap.put("btnchart", "../FXML/chart.fxml");
        urlMap.put("btnmagazine", "../FXML/magazine.fxml");
        urlMap.put("btnmv", "../FXML/mv.fxml");

        // login page Form
        urlMap.put("btncancel", "../FXML/main.fxml");
        urlMap.put("LoginSuccess", "../FXML/loginmain.fxml");
        urlMap.put("btnlogout", "../FXML/main.fxml");

        // loginMain page Form
        urlMap.put("btnhome1", "../FXML/loginmain.fxml");

        // Help & Version Form
        urlMap.put("Help_Btn", "../Form/Help.fxml");
        urlMap.put("Version_Btn", "../Form/Version.fxml");
        urlMap.put("DirectQuestion_Btn", "../Form/DirectQuestion.fxml");
        urlMap.put("QnA_Btn", "../Form/QnA.fxml");
    }


    private Scene getScene(String btnId) {
        String url = urlMap.get(btnId);
//        System.out.println(url);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AbstractController ctrler = loader.getController();
        ctrler.setRoot(root);

        return new Scene(root);
    }

    @Override
    public void openWindow(String btnId) {
        Scene scene = getScene(btnId);
        Stage s = new Stage();

        s.setTitle("MusicWebPlayer");
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

    @Override
    public void closeWindow(ActionEvent event) {
        Parent root = (Parent)event.getSource();
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }


}
