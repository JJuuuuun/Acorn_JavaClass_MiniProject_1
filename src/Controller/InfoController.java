package Controller;

import Service.CommonService;
import Service.ICommonService;
import Service.IPlaybackService;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

import java.net.URL;
import java.util.ResourceBundle;

public class InfoController extends AbstractController implements Initializable {
    Parent root;
    ICommonService comServ;
    IPlaybackService playbackService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comServ = new CommonService();
    }

    public void setPlaybackService(IPlaybackService playbackService) {
        this.playbackService = playbackService;
        playbackService.getInfoInstance(root);
        playbackService.getLiked();
    }

    public void settings() {
        comServ.openWindow("Settings");
    }

    public void like(ActionEvent event) {
        playbackService.setLiked();
    }

    public void about() {
        comServ.openWindow("About");
    }

    @Override
    public void setRoot(Parent root) {
        this.root = root;
    }

}
