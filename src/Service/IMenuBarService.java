package Service;

import javafx.event.ActionEvent;
import javafx.scene.Parent;

public interface IMenuBarService {
    // field : private ICommonService

    // method :
    void eventProc(ActionEvent event);
    // private void eventCheck();
    // ICommon's method : open, change

    // 0428, ���� ����ڸ� �˱� ���� �߰���
    String getUserId();
    void isCurrentUser(Parent root);
}
