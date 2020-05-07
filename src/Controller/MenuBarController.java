package Controller;

import Service.IMenuBarService;
import Service.MenuBarService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuBarController extends AbstractController implements Initializable {
    IMenuBarService menuBarService;

    // �α����� ����ڸ� ã�Ƴ��� ���� form
    Parent parent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        menuBarService = new MenuBarService();
    }

    @FXML
    void eventProc(ActionEvent event) {
        menuBarService.eventProc(event);
        menuBarService.isCurrentUser(parent);
    }

    @Override
    public void setRoot(Parent root) {
        parent = root;
    }
}
