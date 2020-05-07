package Player.Service;

import Player.Database.DataManager;
import Player.Database.IDataManager;
import Player.MusicInfo;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlaybackService implements IPlaybackService {
    private final List<MediaPlayer> players = new ArrayList<>();
    // 플레이어 구성요소
    private final ImageService imageService;
    private final Map<MediaPlayer, Integer> idMap;
    private final int REPEAT_ONLY = 1;
    private final int REPEAT_ALL = 2;
    IDataManager dataManager;
    Thread mps;
    // 현재 음악 정보
    MusicInfo currentMusicInfo;
    private int index = 0;
    // isPlaying
    private boolean isPlaying = false;
    // Volume
    private double volume = 100;
    // repeat mode : 0 => N/A, 1 => 1곡, 2 => 전체
    private int repeatMode = 0;
    // shuffle mode : false => N/A, true => Shuffle
    private boolean shuffle = false;

    // 현재 음악을 재생하는 플레이어
    private MediaPlayer playback;

    /**
     * 현재 생성자는 데이터베이스에서 모든 음악을 가져오도록 설정되어있음.
     */
    public PlaybackService() {
        imageService = new ImageServiceimpl();
        dataManager = new DataManager();
        idMap = dataManager.getAllMusics();
        idMap.forEach((mediaPlayer, integer) -> players.add(mediaPlayer));
        playback = players.get(index);
        currentMusicInfo = dataManager.getMusicInfo(idMap.get(playback));
    }

    @Override
    public void playPrevMusic(ActionEvent event) {
        if ((playback.getCurrentTime().toSeconds() / playback.getMedia().getDuration().toSeconds()) < 0.2) {
            if (index > 0 && repeatMode != REPEAT_ONLY) index--;
            else if (repeatMode == REPEAT_ALL) index = players.size() - 1;

            stop();
            playback = players.get(index);
            isPlaying = false;
        } else {
            stop();
        }
        play(event);
    }

    @Override
    public void play(ActionEvent event) {
        if (!isPlaying) {
            isPlaying = true;
            currentMusicInfo = dataManager.getMusicInfo(idMap.get(playback));
            if (playback != null) {
                Button btnPlay = (Button) getNode(event, "#playBtn");
                Label lblCurrent = (Label) getNode(event, "#sTime");
                Label lblTotal = (Label) getNode(event, "#eTime");
                if (playback.getOnReady() == null)
                    playback.setOnReady(() -> playback.setVolume(volume));
                if (playback.getOnPlaying() == null)
                    playback.setOnPlaying(() -> {
                        /*
                         * 재생 중 :
                         * 일시정지 이미지 표시
                         * */
                        dataManager.updateMusic(idMap.get(playback), playback.getTotalDuration());
                        int time = (int) playback.getTotalDuration().toSeconds();
                        lblTotal.setText(String.format("%02d", time / 60) + ":" + String.format("%02d", time % 60));
                        imageService.btnImage(btnPlay, "/img/pause.png", 30, 30);
                        addPlays();
                        mps = new Thread(() -> {
                            // progress 가져오기
                            while (isPlaying) {
                                try {
                                    Thread.sleep(300);
                                } catch (InterruptedException e) {
                                    System.out.println(e.getMessage());
                                }
                                // 음악 현재시간 설정
                                double current = playback.getCurrentTime().toSeconds();
                                double val = (current / playback.getTotalDuration().toSeconds()) * 100;
                                Platform.runLater(() -> {
                                    lblCurrent.setText(String.format("%02d", (int) (current / 60)) + ":" + String.format("%02d", (int) (current % 60)));
                                    setProgress(event, val);
                                });
                            }
                        });
                        mps.start();
                    });
                if (playback.getOnPaused() == null)
                    playback.setOnPaused(() -> {
                        /*
                         * 일시정지 :
                         * 재생 이미지 표시
                         * */
                        imageService.btnImage(btnPlay, "/img/play.png", 30, 30);
                        mps.interrupt();
                    });
                if (playback.getOnStopped() == null)
                    playback.setOnStopped(() -> {
                        /*
                         * 정지 :
                         * 재생 이미지 표시
                         * */
                        imageService.btnImage(btnPlay, "/img/play.png", 30, 30);
                        mps.interrupt();
                    });
                if (playback.getOnEndOfMedia() == null)
                    playback.setOnEndOfMedia(() -> {
                        // 재생 종료시 : 되감은 뒤 현재 미디어 정지, 다음 미디어
                        stop();
                        playNextMusic(event);
                    });
                playback.play();
            }
        } else {
            isPlaying = false;
            pause();
        }
    }

    private Node getNode(Event event, String selector) {
        Parent form = ((Node) (event.getSource())).getScene().getRoot();
        return form.lookup(selector);
    }

    @Override
    public void pause() {
        playback.pause();
    }

    /**
     * TBR stopMusic
     */
    @Override
    public void stop() {
        if (playback != null) {
            playback.stop();
        }
    }

    @Override
    public void playNextMusic(ActionEvent event) {
        // 현재 List index 를 증가시키고, 새로운 인스턴스 로드
        if (index < players.size() - 1) {
            stop();
            playback = players.get(++index);
            isPlaying = false;
            play(event);
        } else System.out.println("No more songs");
    }

    private void setProgress(Event event, double value) {
        Slider bar = (Slider) getNode(event, "#excessBar");
        bar.setValue(value);
    }

    @Override
    public void seek(Event event) {
        Slider bar = (Slider) getNode(event, "#excessBar");
        double value = bar.getValue() / 100 * playback.getTotalDuration().toSeconds();
        playback.seek(Duration.seconds(value));
    }

    @Override
    public void setRepeat(ActionEvent event) {
        /*
         * 반복 모드 변경:
         * 반복 버튼의 이미지를 바뀔 버튼의 이미지로 변경
         * 1곡 > 전체 > 안함
         * */
        Button btnRepeat = (Button) getNode(event, "#repeatBtn");

        switch (repeatMode) {
            case REPEAT_ONLY:
                repeatMode = REPEAT_ALL;
                // 전체반복 이미지로 변경
                break;
            case REPEAT_ALL:
                repeatMode = 0;
                // 반복안함 이미지로 변경
                break;
            default:
                // 한곡반복 이미지로 변경
                repeatMode = REPEAT_ONLY;
        }
    }

    /**
     * TBR setShuffle
     */
    @Override
    public void shuffle() {
        /*
         * 셔플 모드 변경:
         * 셔플 버튼의 이미지를 바뀔 버튼의 이미지로 변경
         * 토글 처리
         * */
        shuffle = !shuffle;
    }

    @Override
    public void getInfos(Parent parent) {
        Label titleLabel = (Label) parent.lookup("#title");
        Label artistLabel = (Label) parent.lookup("#artist");
        TextArea textArea = (TextArea) parent.lookup("#lyrics");
        titleLabel.setText(currentMusicInfo.getTitle());
        artistLabel.setText(currentMusicInfo.getArtist());
        textArea.setText(currentMusicInfo.getLyrics());
    }

    @Override
    public void getLiked(Parent parent) {
        ToggleButton button = (ToggleButton) parent.lookup("#likeBtn");
        boolean liked = dataManager.getLiked(idMap.get(playback));
        if (liked) {
            imageService.btnImage(button, "/img/like2.png", 40, 40);
        } else
            imageService.btnImage(button, "/img/like.png", 40, 40);
        button.setSelected(liked);
    }

    @Override
    public void setLiked(ActionEvent event) {
        ToggleButton button = (ToggleButton) getNode(event, "#likeBtn");
        boolean newLiked = !dataManager.getLiked(idMap.get(playback));
        if (newLiked) {
            imageService.btnImage(button, "/img/like2.png", 40, 40);
        } else
            imageService.btnImage(button, "/img/like.png", 40, 40);
        dataManager.setLiked(idMap.get(playback), newLiked);
        button.setSelected(newLiked);
    }

    @Override
    public void setMute(Event event) {
        ToggleButton btnMute = (ToggleButton) getNode(event, "#muteBtn");

        if (btnMute.isSelected()) {
            imageService.btnImage(btnMute, "/img/mute.png", 30, 30);
        } else {
            imageService.btnImage(btnMute, "/img/sound.png", 30, 30);
        }
        playback.setMute(btnMute.isSelected());
    }

    @Override
    public void setVolume(Event event) {
        /*
         * Volume 변경
         * */
        // 볼륨변경시 mute 해제
        ToggleButton btnMute = (ToggleButton) getNode(event, "#muteBtn");
        btnMute.setSelected(false);
        setMute(event);

        volume = playback.getVolume();
        Slider slider = (Slider) getNode(event, "#soundBar");
        playback.setVolume(slider.getValue() / 100);
    }

    /**
     * Add plays to MusicInfo
     */
    private void addPlays() {
        dataManager.addPlays(idMap.get(playback));
    }

}
