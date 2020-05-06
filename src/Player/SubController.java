package Player;

import Player.Service.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SubController extends Controller implements Initializable {
    ImageService imgServ = new ImageServiceimpl();
    IPlaybackService playbackService;
    Parent root;
    @FXML
    Slider soundBar;
    @FXML
    ProgressBar excessBar;
    @FXML
    Button playBtn;
    @FXML
    Button nextBtn;
    @FXML
    Button backBtn;
    @FXML
    Button shuffleBtn;
    @FXML
    ToggleButton muteBtn;
    int i = 0;

    int min;
    int sec;
    @FXML
    Button repeatBtn;
    @FXML
    Label sTime;
    @FXML
    Label eTime;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        playbackService = new PlaybackService();
        btnimg();//버튼 이미지 입히기
    }

    public void play(ActionEvent event) {// 플레이 버튼 구현
        playbackService.play(event);
    }

    public void repeat(ActionEvent event) {
        playbackService.setRepeat(event);
    }

    public void excess() {
    }

    public void setVolume(Event event) {
        playbackService.setVolume(event);
    }

    public void mute(ActionEvent event) {
        playbackService.setMute(event);
    }

    public void btnimg() {

        imgServ.btnImage(playBtn, "/img/play.png", 30, 30);
        imgServ.btnImage(nextBtn, "/img/next.png", 30, 30);
        imgServ.btnImage(backBtn, "/img/next.png", 30, 30);
        backBtn.setRotate(180);
        imgServ.btnImage(repeatBtn, "/img/repeat.png", 30, 30);
        imgServ.btnImage(muteBtn, "/img/sound.png", 30, 30);
        imgServ.btnImage(shuffleBtn, "/img/shuffle.png", 30, 30);
        //프로그래스 바 색 변경
        soundBar.setStyle("-fx-accent: lightgreen;-fx-background-color: ffffff; -fx-padding: 0;");
        excessBar.setStyle("-fx-accent: violet;");


    }

    public void startTime() {
        min = i / 60;
        sec = i % 60;
        if (i < 10) sTime.setText(min + ":" + "0" + sec);
        else sTime.setText(min + ":" + sec);

    }

    public void endTime() {
        eTime.setText((2 - min) + ":" + (60 - sec));

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

}
