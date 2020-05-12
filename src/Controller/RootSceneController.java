package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class RootSceneController extends AbstractController implements Initializable {
    @FXML
    BorderPane rootScene;

    Parent root;

    @Override
    public void setRoot(Parent root) {
        this.root = root;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public Parent getRoot() {
        return root;
    }
}
