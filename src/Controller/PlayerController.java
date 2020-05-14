package Controller;

import Service.CommonService;
import Service.ICommonService;
import Service.IPlaybackService;
import Service.PlaybackService;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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


    public void play() {// 플레이 버튼 구현
        playbackService.play();
    }

    public void next() {
        playbackService.playNextMusic();
    }

    public void shuffle() {
        playbackService.setShuffle();
    }


    public void repeat() {
        playbackService.setRepeat();
    }

    public void setVolume() {
        playbackService.setVolume();
    }

    public void mute() {
        playbackService.setMute();
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
        playbackService.getPlaybackInstance(root);
    }

    public void prev() {
        playbackService.playPrevMusic();
    }

    public void seek() {
        playbackService.seek();
    }
}
