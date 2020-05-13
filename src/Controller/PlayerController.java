package Controller;

import Service.CommonService;
import Service.ICommonService;
import Service.IPlaybackService;
import Service.PlaybackService;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

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

    public void shuffle(ActionEvent event) {
        playbackService.setShuffle(event);
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

    public void showPlayerInfo() {
        ICommonService comServ = new CommonService();
        comServ.openWindow("PlayInfo");
        infoController = (InfoController) comServ.getController("PlayInfo.fxml");
        infoController.setPlaybackService(playbackService);
    }

    @Override
    public void setRoot(Parent root) {
        this.root = root;

    }

    public void prev(ActionEvent event) {
        playbackService.playPrevMusic(event);
        if (infoController != null) infoController.updateForm();
    }

    public void seek(MouseEvent event) {
        playbackService.seek(event);
    }
}
