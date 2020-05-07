package Player;

import Player.Service.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends Controller implements Initializable {
    Parent root;
    ICommonService comServ;
    ImageService imgbtn;
    IPlaybackService playbackService;
    @FXML
    Button setBtn;
    @FXML
    ToggleButton likeBtn;
    @FXML
    Button aboutBtn;
    int like = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comServ = new CommonServiceImpl();
        imgbtn = new ImageServiceimpl();
        imgbtn();

    }

    public void setPlaybackService(IPlaybackService playbackService) {
        this.playbackService = playbackService;
        setup();
    }

    public void setup() {
        playbackService.getLiked(root);
        playbackService.getInfos(root);
    }

    public void settings() {
        Stage s = new Stage(); //
        comServ.showWindow(s, "../Settings.fxml");
    }

    public void like(ActionEvent event) {
        playbackService.setLiked(event);

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
