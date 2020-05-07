package Player;

import Player.Service.CommonServiceImpl;
import Player.Service.ICommonService;
import Player.Service.IPlaybackService;
import Player.Service.PlaybackService;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SubController extends Controller implements Initializable {
    IPlaybackService playbackService;
    MainController mainController;
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
        mainController.setup();
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
        ICommonService comServ = new CommonServiceImpl();
        Stage mForm = new Stage();
        mainController = (MainController) comServ.showWindow(mForm, "../PlayerMain.fxml");
        mainController.setPlaybackService(playbackService);
    }

    @Override
    public void setRoot(Parent root) {
        this.root = root;

    }

    public void prev(ActionEvent event) {
        playbackService.playPrevMusic(event);
        mainController.setup();
    }

    public void seek(MouseEvent event) {
        playbackService.seek(event);
    }
}
