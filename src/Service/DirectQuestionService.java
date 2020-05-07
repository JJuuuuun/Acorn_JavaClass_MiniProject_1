package Service;

import DB.DBConnector;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.List;

public class DirectQuestionService implements IDirectQuestionService {

    final String SUCCESS = "����";
    final String FAIL = "����";
    final int TITLE = 0;
    final int QUESTION = 1;

    DBConnector db;
    IMenuBarService menuBarService;

    public DirectQuestionService() {
        db = new DBConnector();
        menuBarService = new MenuBarService();
    }

    @Override
    public boolean eventProc(ActionEvent event) {
        String btnId = eventCheck(event);
        String userID = getIdFromDB();  // **Ȯ���ʿ�**

        if (btnId.equals("History_Btn")) {
            List<String> historyData = db.getData(userID);
            if (showHistory(event, historyData))
                showAlert(SUCCESS, "���� ����� �����Խ��ϴ�.");
            else
                showAlert(FAIL, "�����Ͻ� ����� �����ϴ�.");
        } else if (btnId.equals("Send_Btn")) {
            List<String> sendData = sendToDB(event, userID);
            if (db.storeData(sendData)) {
                showAlert(SUCCESS, "�̿��� �ּż� �����մϴ�.");
                return true;
            }
            else
                showAlert(FAIL, "�˼��մϴ�. ���ۿ� �����Ͽ����ϴ�");
        }

        return false;
    }

    private String eventCheck(ActionEvent event) {
        Button btn = (Button) event.getSource();
        return btn.getId();
    }

    private String getIdFromDB() {
        return menuBarService.getUserId();
    }

    private List<String> sendToDB(ActionEvent event, String userID) {
        Parent root = (Parent) event.getSource();
        Parent form = root.getScene().getRoot();
        ComboBox<String> titleCmbo = (ComboBox) form.lookup("#Title_ComboBox");
        TextArea questionTxt = (TextArea) form.lookup("#Question_TxtArea");

        String title = null;
        String question = null;
        if (userID == null || titleCmbo == null || questionTxt == null)
            return null;
        else if (titleCmbo.getValue() == null || questionTxt.getText().isEmpty())
            return null;
        else {
            title = titleCmbo.getValue();
            question = questionTxt.getText();
        }

        List<String> sendData = new ArrayList<>();
        sendData.add(userID);
        sendData.add(title);
        sendData.add(question);
        return sendData;
    }

    private boolean showHistory(ActionEvent event, List<String> historyData) {
        if (historyData.size() == 0)
            return false;
        else {
            Parent root = (Parent) event.getSource();
            Parent form = root.getScene().getRoot();
            ComboBox<String> combo = (ComboBox) form.lookup("#Title_ComboBox");
            TextArea txt = (TextArea) form.lookup("#Question_TxtArea");

            combo.setPromptText("������ �����Ͻ� �����Դϴ�.");
            combo.setValue(null);
            String history = "";
            for (int i = 0; i < historyData.size(); i += 2) {
                history += historyData.get(TITLE + i);
                history += "\n";
                history += historyData.get(QUESTION + i);
                history += "\n============================================\n";
            }

            txt.setText(history);

            return true;
        }
    }

    private void showAlert(String title, String head, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(head);
        alert.setContentText(content);

        alert.show();
    }

    private void showAlert(String head, String content) {
        showAlert("���", head, content);
    }

}
