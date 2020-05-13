package Service;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MenuBarService implements IMenuBarService{
    private ICommonService comn;

    // 현재 로그인한 유저를 기억하기 위한 변수
    private static String userId;

    public MenuBarService() {
        comn = new CommonService();
    }
    @Override
    public void eventProc(ActionEvent event) {
        String btnId = eventCheck(event);
        //버튼 아이디 받음
        if(btnId.equals("Help_Btn"))
            comn.changeWindow(event, btnId);
        else if(btnId.equals("Version_Btn"))
            comn.openWindow(btnId);

    }

    private String eventCheck(ActionEvent event) {
        //버튼의 이벤트 관련 메소드
        Button btn = (Button)event.getSource();
        //버튼 이벤트의 출처
        return btn.getId();//버튼 아이디를 반환
    }

    @Override
    public void isCurrentUser(Parent root) {
        Label currentUser = (Label) root.lookup("#lblinfo2");
        //라벨 정보를 받아서 공유
        try {
            userId = currentUser.getText().substring(5);
            //중복을 방지하기 위한 묶음 지정
        } catch (NullPointerException e) {}
        // 0512 수정 진행중..
//        System.out.println("userId is " + userId); // test code
    }

    public String getUserId() {
        return userId;
    }
    //호출 시 현재 로그인한 유저의 아이디 반환

}
