package Service;

import javafx.event.ActionEvent;
import javafx.scene.Parent;

public interface IMenuBarService {
    // field : private ICommonService

    // method :
    void eventProc(ActionEvent event);
    // private void eventCheck();
    // ICommon's method : open, change

    // 0428, 현재 사용자를 알기 위해 추가됨
    String getUserId();
    void isCurrentUser(Parent root);
}
