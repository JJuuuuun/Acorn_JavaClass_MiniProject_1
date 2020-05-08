package Controller;

import Service.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;

import java.net.URL;
import java.util.ResourceBundle;

public class InfoController extends AbstractController implements Initializable {
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comServ = new CommonService();
        imgbtn = new ImageServiceimpl();
        imgbtn();

    }

    public void setPlaybackService(IPlaybackService playbackService) {
        this.playbackService = playbackService;
        updateForm();
    }

    public void updateForm() {
        playbackService.getLiked(root);
        playbackService.getInfos(root);
    }

    public void settings() {
        comServ.openWindow("Settings");
    }

    public void like(ActionEvent event) {
        playbackService.setLiked(event);
    }

    public void imgbtn() {
        imgbtn.btnImage(setBtn, "/img/settings.png", 20, 20);
        imgbtn.btnImage(aboutBtn, "/img/dots.png", 20, 20);
    }

    public void about() {
        comServ.openWindow("About");
    }

    @Override
    public void setRoot(Parent root) {
        this.root = root;

    }

}
