package Service;

import Controller.AbstractController;
import javafx.stage.Stage;

public interface ICommonService {
    AbstractController showWindow(Stage s, String formPath);
}
