package Controller;

import Service.DirectQuestionService;
import Service.IDirectQuestionService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class DirectQuestionController extends Controller implements Initializable {
    @FXML ComboBox<String> Title_ComboBox;
    @FXML TextArea Question_TxtArea;
    IDirectQuestionService directQuestionService;
    final String [] titles = {"회원가입", "주제1", "주제2", "기타"};


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        directQuestionService = new DirectQuestionService();

        for(String title : titles)
            Title_ComboBox.getItems().add(title);

        Title_ComboBox.setPromptText("주제를 선택하세요!");

        Title_ComboBox.setOnAction(e -> clearTxt());
    }

    @FXML
    void eventProc(ActionEvent event) {
       if(directQuestionService.eventProc(event))
           clearTxt();
    }

    private void clearTxt() {
        Question_TxtArea.clear();
    }

    @Override
    public void setRoot(Parent root) {

    }
}
