package Player;

import Player.Service.CommonServiceImpl;
import Player.Service.ICommonService;
import Player.Service.IPlaybackService;
import Player.Service.PlaybackService;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SubController extends Controller implements Initializable {
    IPlaybackService playbackService;
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

    public void Main() {
        ICommonService comServ = new CommonServiceImpl();
        Stage mForm = new Stage();
        comServ.showWindow(mForm, "../PlayerMain.fxml");
    }

    @Override
    public void setRoot(Parent root) {
        this.root = root;

    }

    public void prev(ActionEvent event) {
        playbackService.playPrevMusic(event);
    }
}
