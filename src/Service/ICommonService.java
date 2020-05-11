package Service;

import Controller.AbstractController;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Parent;

public interface ICommonService {
    // method :
    // private Scene getScene();
    void openWindow(String btnId);
    void changeWindow(ActionEvent event, String btnId);

    // 0507 add
    void closeWindow(ActionEvent event);

    // 0508 add
    AbstractController getController(String fxmlName);
    // add to musicPlayer to fix location
    void changeWindow(Parent root, String btnId, Pos location);
}
