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

    private final String titleStr_1 = "�޴��ٿ� ȸ������ ��ư�� Ŭ���ϼ���.";
    private final String titleStr_2 = "���� �������� �ʾҽ��ϴ�..^^";
    private final String titleStr_3 = "�����̿��� �������!";
    private final String titleStr_4 = "����ڸ� �ҷ����ڽ��ϴ�.";
    private final String titleStr_5 = "������̰� ����±���.";

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
