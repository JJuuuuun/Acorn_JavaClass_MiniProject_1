package Controller;

import Service.IQnAService;
import Service.QnAService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class QnAController extends AbstractController implements Initializable {
    @FXML Label title_1;
    @FXML Label title_2;
    @FXML Label title_3;
    @FXML Label title_4;
    @FXML Label title_5;

    private final String titleStr_1 = "메뉴바에 회원가입 버튼을 클릭하세요.";
    private final String titleStr_2 = "아직 구현되지 않았습니다..^^";
    private final String titleStr_3 = "차빈이에게 물어보세요!";
    private final String titleStr_4 = "담당자를 불러오겠습니다.";
    private final String titleStr_5 = "서브웨이가 땡기는구나.";

    IQnAService QnAService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        QnAService = new QnAService();
        title_1.setText(titleStr_1);
        title_2.setText(titleStr_2);
        title_3.setText(titleStr_3);
        title_4.setText(titleStr_4);
        title_5.setText(titleStr_5);
    }

    @Override
    public void setRoot(Parent root) {

    }
}
