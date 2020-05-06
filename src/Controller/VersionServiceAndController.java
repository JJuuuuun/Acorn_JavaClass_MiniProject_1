package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class VersionServiceAndController extends Controller implements Initializable {
    @FXML
    private Label Version_Lbl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showVersion();
    }

    private void showVersion() {
        Version_Lbl.setText("1.00");
    }

    @Override
    public void setRoot(Parent root) {

    }
}
