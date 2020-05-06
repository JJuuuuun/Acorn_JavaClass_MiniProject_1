package Service;

import javafx.event.ActionEvent;

public interface IDirectQuestionService {
    // field : DBConnector, IMenuBarService

    // method :
    boolean eventProc(ActionEvent event);
    // privtae eventCheck();
    // private sendToDB();
    // private getIdFromDB();
    // DBConnector's method : store, get
    // private showHistory();
    // private showAlert();
}
