package Player;

import Player.Service.CommonServiceImpl;
import Player.Service.ICommonService;
import Player.Service.ImageService;
import Player.Service.ImageServiceimpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends Controller implements Initializable {
    Parent root;
    ICommonService comServ = new CommonServiceImpl();
    ImageService imgbtn = new ImageServiceimpl();
    @FXML
    Button setBtn;
    @FXML
    Button likeBtn;
    @FXML
    Button aboutBtn;
    int like = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imgbtn();
    }

    public void settings() {
        Stage s = new Stage(); //
        comServ.showWindow(s, "../Settings.fxml");
    }

    public void like(ActionEvent event) {
        if (like == 1) {
            imgbtn.btnImage(likeBtn, "/img/like.png", 40, 40);
            like = 0;
        } else {
            imgbtn.btnImage(likeBtn, "/img/like2.png", 40, 40);
            like = 1;
        }
    }

    public void imgbtn() {
        imgbtn.btnImage(setBtn, "/img/settings.png", 20, 20);
        imgbtn.btnImage(likeBtn, "/img/like.png", 40, 40);//??
        like = 1;
        imgbtn.btnImage(aboutBtn, "/img/dots.png", 20, 20);
    }

    public void about() {
        Stage s = new Stage();
        comServ.showWindow(s, "../About.fxml");
    }

    @Override
    public void setRoot(Parent root) {
        this.root = root;

    }

}
