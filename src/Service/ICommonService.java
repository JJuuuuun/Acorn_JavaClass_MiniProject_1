package Service;

import javafx.event.ActionEvent;

public interface ICommonService {
    // method :
    // private Scene getScene();
    void openWindow(String btnId);
    void changeWindow(ActionEvent event, String btnId);

    // 0507 add
    void closeWindow(ActionEvent event);
}
