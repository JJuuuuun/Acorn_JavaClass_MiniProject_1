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

public class DirectQuestionController extends AbstractController implements Initializable {
    @FXML ComboBox<String> Title_ComboBox;
    @FXML TextArea Question_TxtArea;
    IDirectQuestionService directQuestionService;
    final String [] titles = {"Join", "Title1", "Title2", "ETC"};


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        directQuestionService = new DirectQuestionService();

        for(String title : titles)
            Title_ComboBox.getItems().add(title);

        Title_ComboBox.setPromptText("Please check title");

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
