package Controller;

import Service.HelpService;
import Service.IHelpService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

import java.net.URL;
import java.util.ResourceBundle;

public class HelpController extends AbstractController implements Initializable {
    private IHelpService helpService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        helpService = new HelpService();
    }

    @FXML
    void eventProc(ActionEvent event) {
        helpService.eventProc(event);
    }

    @Override
    public void setRoot(Parent root) {

    }
}
