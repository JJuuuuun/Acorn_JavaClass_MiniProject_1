package Controller;

import Service.CommonService;
import Service.ICommonService;
import Service.IPlaybackService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;

import java.net.URL;
import java.util.ResourceBundle;

public class InfoController extends AbstractController implements Initializable {
    //
    Parent root;
    ICommonService comServ;
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

    public void about() {
        comServ.openWindow("About");
    }

    @Override
    public void setRoot(Parent root) {
        this.root = root;
    }

}
