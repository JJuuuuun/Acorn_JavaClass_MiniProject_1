package Service;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MenuBarService implements IMenuBarService{
    private ICommonService comn;

    // 현재 로그인한 유저를 기억하기 위한 변수
    private String userId;

    public MenuBarService() {
        comn = new CommonService();
    }
    @Override
    public void eventProc(ActionEvent event) {
        String btnId = eventCheck(event);

        if(btnId.equals("Help_Btn"))
            comn.changeWindow(event, btnId);
        else if(btnId.equals("Version_Btn"))
            comn.openWindow(btnId);

    }

    private String eventCheck(ActionEvent event) {
        Button btn = (Button)event.getSource();
        return btn.getId();
    }

    @Override
    public void isCurrentUser(Parent root) {
        Label currentUser = (Label) root.lookup("#lblinfo2");
        try {
            userId = currentUser.getText().substring(5);
        } catch (NullPointerException e) {}

//        System.out.println("userId is " + userId); // test code
    }

    public String getUserId() {
        return userId;
    }

}
