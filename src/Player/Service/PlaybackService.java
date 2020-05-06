package Player.Service;

import Player.Database.DataManager;
import Player.Database.IDataManager;
import Player.MusicInfo;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlaybackService implements IPlaybackService {
    private static final String TAG_COLUMN_NAME = "tag";
    private static final String VALUE_COLUMN_NAME = "value";
    private final List<MediaPlayer> players = new ArrayList<>();
    // 플레이어 구성요소
    private final ImageService imageService;
    private final Map<MediaPlayer, Integer> idMap;
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
        if ((playback.getCurrentTime().toSeconds() / playback.getMedia().getDuration().toSeconds()) < 0.15) {
            if (index > 0) index--;
            else index = players.size() - 1;
            playback = players.get(index);
            play(event);
        } else playback.seek(Duration.ZERO);
    }

    @Override
    public void play(ActionEvent event) {
        if (!isPlaying) {
            isPlaying = true;
            if (playback != null) {
                Button btnPlay = (Button) getNode(event, "#playBtn");
                Label lblCurrent = (Label) getNode(event, "#sTime");
                Label lblTotal = (Label) getNode(event, "#eTime");
                if (playback.getOnReady() == null)
                    playback.setOnReady(() -> {
                        dataManager.updateMusic(idMap.get(playback), playback.getTotalDuration());
                        playback.setVolume(volume);
                        // 음악 총 재생시간 설정
                    });
                if (playback.getOnPlaying() == null)
                    playback.setOnPlaying(() -> {
                        /*
                         * 재생 중 :
                         * 일시정지 이미지 표시
                         * */
                        try {
                            System.out.println(playback.getTotalDuration());
                            String time = String.format("%d", (int)playback.getTotalDuration().toSeconds());
                            lblTotal.setText(String.valueOf(new SimpleDateFormat("mm:ss").parse(time)));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
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
                                double val = playback.getCurrentTime().toSeconds() / playback.getTotalDuration().toSeconds();
                                Platform.runLater(() -> setProgress(event, val));
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
                        playback.seek(Duration.ZERO);
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
        if (index < players.size()) {
            playback = players.get(++index);
            play(event);
        }
    }

    @Override
    public void setProgress(ActionEvent event, double value) {
        // 위치 조절하려면 Slider로 변경해야함
        ProgressBar bar = (ProgressBar) getNode(event, "#excessBar");
        bar.setProgress(value);
    }

    public void seekTo(int position) {
        playback.seek(Duration.seconds(position));
    }

    @Override
    public String getLyrics() {
        return dataManager.getLyrics(idMap.get(playback));
    }

    @Override
    public void setRepeat(ActionEvent event) {
        /*
         * 반복 모드 변경:
         * 반복 버튼의 이미지를 바뀔 버튼의 이미지로 변경
         * 1곡 > 전체 > 안함
         * */
        Button btnRepeat = (Button) getNode(event, "#repeatBtn");
        final int REPEAT_ONLY = 1;
        final int REPEAT_ALL = 2;
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
    public void setLiked(ActionEvent event) {
        /*
         * 좋아요 변경:
         * 좋아요 버튼에 상태에 따라 처리.
         *
         * */
        ToggleButton button = (ToggleButton) getNode(event, "#");
        if (button.isSelected()) {
            imageService.btnImage(button, "/img/like2.png", 40, 40);
        } else
            imageService.btnImage(button, "/img/like.png", 40, 40);
        dataManager.setLiked(idMap.get(playback), button.isSelected());
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

    @Override
    public Media getMedia() {
        return playback.getMedia();
    }

    @Override
    public void getMusicInfo(int id) {
        currentMusicInfo = dataManager.getMusicInfo(id);
        players.add(new MediaPlayer(currentMusicInfo.getMedia()));
        playback = players.get(index);
    }

    /**
     * Add plays to MusicInfo
     */
    private void addPlays() {
        dataManager.addPlays(idMap.get(playback));
    }

    private ObservableList<Map<String, Object>> convertMetadataToTableData(ObservableMap<String, Object> metadata) {
        ObservableList<Map<String, Object>> allData = FXCollections.observableArrayList();

        for (String key : metadata.keySet()) {
            Map<String, Object> dataRow = new HashMap<>();

            dataRow.put(TAG_COLUMN_NAME, key);
            dataRow.put(VALUE_COLUMN_NAME, metadata.get(key));

            allData.add(dataRow);
        }
        return allData;
    }
}
