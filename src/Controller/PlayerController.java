package Controller;

import Service.CommonService;
import Service.ICommonService;
import Service.IPlaybackService;
import Service.PlaybackService;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PlayerController extends AbstractController implements Initializable {
    IPlaybackService playbackService;
    InfoController infoController;
    Parent root;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        playbackService = new PlaybackService();
    }

    public void play(ActionEvent event) {// 플레이 버튼 구현
        playbackService.play(event);
    }

    public void next(ActionEvent event) {
        playbackService.playNextMusic(event);
        if (infoController != null) infoController.updateForm();
    }

    public void repeat(ActionEvent event) {
        playbackService.setRepeat(event);
    }

    public void setVolume(Event event) {
        playbackService.setVolume(event);
    }

    public void mute(ActionEvent event) {
        playbackService.setMute(event);
    }

    public void callMain() {
        ICommonService comServ = new CommonService();
        Stage mForm = new Stage();
        infoController = (InfoController) comServ.showWindow(mForm, "../FXML/PlayerMain.fxml");
        infoController.setPlaybackService(playbackService);
    }

    @Override
    public void setRoot(Parent root) {
        this.root = root;

    }

    public void prev(ActionEvent event) {
        playbackService.playPrevMusic(event);
        infoController.updateForm();
    }

    public void seek(MouseEvent event) {
        playbackService.seek(event);
    }
}
