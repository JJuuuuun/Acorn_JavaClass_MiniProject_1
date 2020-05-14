package Service;

import Database.DataManager;
import Database.IDataManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PlaybackService implements IPlaybackService {

    private final List<MediaPlayer> players;
    // 플레이어 구성요소
    private final ImageService imageService;
    private final Map<MediaPlayer, Integer> idMap;
    private final int REPEAT_ONLY = 1;//한 곡 반복 재생
    private final int REPEAT_ALL = 2; //전체 반복 재생
    IDataManager dataManager;
    Thread mps;
    // 현재 음악 정보
    MusicInfo currentMusicInfo;

    // Player 화면
    Button btnRepeat;
    Button btnPlay;
    Button btnShuffle;
    Label lblCurrent;
    Label lblTotal;
    Slider slider;
    Slider volumeSlider;
    ToggleButton btnMute;

    // Info 화면
    Label titleLabel;
    Label artistLabel;
    TextArea textArea;
    ImageView imageView;
    ToggleButton likeBtn;
    ListView<MusicInfo> musicList;


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
     * 사용자 라이브러리 구현시, user id를 이용하여 가져오도록 해야함.
     */
    public PlaybackService() {
        players = new ArrayList<>();
        imageService = new ImageServiceimpl();
        dataManager = new DataManager();
        idMap = dataManager.getAllMusics();
        idMap.forEach((mediaPlayer, integer) -> players.add(mediaPlayer));
        playback = players.get(index);
        currentMusicInfo = dataManager.getMusicInfo(idMap.get(playback));
    }

    @Override
    public void playPrevMusic() {
        if ((playback.getCurrentTime().toSeconds() / playback.getMedia().getDuration().toSeconds()) < 0.2) {
            switch (repeatMode) {
                case REPEAT_ALL:
                    if (index == 0) index = players.size() - 1;
                    else index = (index - 1) % players.size();
                    break;
                case REPEAT_ONLY:
                    break;
                default:
                    if (index - 1 >= 0) index--;
                    else System.out.println("This is front");
            }
            stop();
            playback = players.get(index);
        } else {
            playback.seek(Duration.ZERO);
            stop();
        }
        isPlaying = false;
        play();
    }

    @Override
    public void play() {
        if (!isPlaying) {
            isPlaying = true;
            currentMusicInfo = dataManager.getMusicInfo(idMap.get(playback));
            if (titleLabel != null) infoProcess();
            if (playback != null) {
                setOnReady();
                setOnPlaying();
                setOnPaused();
                setOnStopped();
                setOnEndOfMedia();
                playback.play();
            }
        } else {
            isPlaying = false;
            pause();
        }
    }

    private void setOnReady() {
        if (playback.getOnReady() == null)
            playback.setOnReady(() -> playback.setVolume(volume));
    }

    private void setOnPlaying() {
        if (playback.getOnPlaying() == null)
            playback.setOnPlaying(() -> {
                /*
                 * 재생 중 :
                 * 일시정지 이미지 표시
                 * */
                dataManager.updateMusic(idMap.get(playback), playback.getTotalDuration());
                int time = (int) playback.getTotalDuration().toSeconds();
                lblTotal.setText(String.format("%02d", time / 60) + ":" + String.format("%02d", time % 60));
                imageService.setButtonImage(btnPlay, "/img/pause.png", 30, 30);
                addPlays();
                setThread();
            });
    }

    private void setOnPaused() {
        if (playback.getOnPaused() == null)
            playback.setOnPaused(() -> {
                /*
                 * 일시정지 :
                 * 재생 이미지 표시, Thread Interrupt
                 * */
                mps.interrupt();
                imageService.setButtonImage(btnPlay, "/img/play.png", 30, 30);
            });
    }

    private void setOnStopped() {
        if (playback.getOnStopped() == null)
            playback.setOnStopped(() -> {
                /*
                 * 정지 : Thread Interrupt
                 * */
                mps.interrupt();
            });
    }

    private void setOnEndOfMedia() {
        if (playback.getOnEndOfMedia() == null)
            playback.setOnEndOfMedia(() -> {
                // 재생 종료시 : 현재 미디어 정지, 다음 미디어
                playback.seek(Duration.ZERO);
                playNextMusic();
            });
    }

    private void setThread() {
        mps = new Thread(() -> {
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
                    lblCurrent.setText(String.format("%02d", (int) (current / 60)) + ":" +
                            String.format("%02d", (int) (current % 60)));
                    setProgress(val);
                });
            }
        });
        // 프로세스 종료시 모든 Thread 종료하도록 설정
        mps.setDaemon(true);
        mps.start();
    }

    @Override
    public void pause() {
        playback.pause();
    }

    @Override
    public void stop() {
        if (playback != null) {
            playback.stop();
        }
    }

    @Override
    public void playNextMusic() {
        switch (repeatMode) {
            case REPEAT_ALL:
                index = (index + 1) % players.size();
                break;
            case REPEAT_ONLY:
                break;
            default:
                if (index + 1 < players.size()) index++;
                else System.out.println("No more songs");
        }
        stop();
        playback = players.get(index);
        isPlaying = false;
        play();
    }

    private void setProgress(double value) {
        slider.setValue(value);
    }

    @Override
    public void seek() {
        double value = slider.getValue() / 100 * playback.getTotalDuration().toSeconds();
        playback.seek(Duration.seconds(value));
    }

    @Override
    public void setRepeat() {
        /*
         * 반복 모드 변경:
         * 반복 버튼의 이미지를 바뀔 버튼의 이미지로 변경
         * 1곡 > 전체 > 안함
         * */
        switch (repeatMode) {
            case REPEAT_ONLY:
                repeatMode = REPEAT_ALL;
                imageService.setButtonImage(btnRepeat, "/img/repeat_all.png", 30, 30);
                break;
            case REPEAT_ALL:
                repeatMode = 0;
                imageService.setButtonImage(btnRepeat, "/img/no_repeat.png", 30, 30);
                break;
            default:
                // 한곡반복 이미지로 변경
                repeatMode = REPEAT_ONLY;
                imageService.setButtonImage(btnRepeat, "/img/repeat_only.png", 30, 30);
        }
    }

    @Override
    public void setShuffle() {
        /*
         * 셔플 모드 변경:
         * 셔플 버튼의 이미지를 바뀔 버튼의 이미지로 변경
         * 토글 처리
         * */
        // 셔플로 바뀐 경우, 현재 음악 제외, 모두 섞고 현재 음악은 컬렉션 앞에 추가
        shuffle = !shuffle;
        if (shuffle) {
            players.remove(playback);
            Collections.shuffle(players);
            players.add(0, playback);
            queueProcess();
        }
        imageService.setButtonImage(btnShuffle, shuffle ? "/img/shuffle.png" : "/img/no_shuffle.png", 30, 30);
    }

    @Override
    public void getInfoInstance(Parent parent) {
        titleLabel = (Label) parent.lookup("#title");
        artistLabel = (Label) parent.lookup("#artist");
        textArea = (TextArea) parent.lookup("#lyrics");
        imageView = (ImageView) parent.lookup("#album");
        likeBtn = (ToggleButton) parent.lookup("#likeBtn");
        musicList = (ListView<MusicInfo>) parent.lookup("#musicQueue");
        infoProcess();
        queueProcess();
    }

    @Override
    public void setPlaybackInstance(Parent parent) {
        if (btnRepeat == null)
            btnRepeat = (Button) parent.lookup("#repeatBtn");
        if (btnPlay == null)
            btnPlay = (Button) parent.lookup("#playBtn");
        if (btnShuffle == null)
            btnShuffle = (Button) parent.lookup("#shuffleBtn");
        if (lblCurrent == null)
            lblCurrent = (Label) parent.lookup("#currentTime");
        if (lblTotal == null)
            lblTotal = (Label) parent.lookup("#totalTime");
        if (slider == null)
            slider = (Slider) parent.lookup("#timeSlider");
        if (volumeSlider == null)
            volumeSlider = (Slider) parent.lookup("#volumeSlider");
        if (btnMute == null)
            btnMute = (ToggleButton) parent.lookup("#muteBtn");
    }

    private void infoProcess() {
        titleLabel.setText(currentMusicInfo.getTitle());
        artistLabel.setText(currentMusicInfo.getArtist());
        textArea.setText(currentMusicInfo.getLyrics());
        if (currentMusicInfo.getAlbumart() != null) {
            imageView.setImage(currentMusicInfo.getAlbumart());
        } else {
            imageView.setImage(null);
        }
    }

    /**
     * 재생 큐를 가져옴
     * 정보 화면 업데이트를 위해 사용
     * 셔플 버튼이 눌리는 경우, 셔플 후 발동
     */
    private void queueProcess() {
        if (musicList != null) {
            musicList.setItems(FXCollections.observableArrayList());
            musicList.setOnMouseClicked(event -> {
                MusicInfo info = musicList.getSelectionModel().getSelectedItem();
                for (int i = 0; i < players.size(); i++) {
                    MediaPlayer player = players.get(i);
                    if (idMap.get(player) == info.getId()) {
                        index = i;
                        stop();
                        currentMusicInfo = dataManager.getMusicInfo(idMap.get(player));
                        infoProcess();
                        playback = player;
                        isPlaying = false;
                        play();
                    }
                }
            });
            players.forEach(mediaPlayer -> {
                MusicInfo info = dataManager.getMusicInfo(idMap.get(mediaPlayer));
                musicList.getItems().add(info);
            });
        }
    }

    @Override
    public void getLiked() {
        boolean liked = dataManager.getLiked(idMap.get(playback));
        imageService.setButtonImage(likeBtn, liked ? "/img/liked.png" : "/img/not_liked.png", 40, 40);
        likeBtn.setSelected(liked);
    }

    @Override
    public void setLiked() {
        boolean newLiked = !dataManager.getLiked(idMap.get(playback));
        imageService.setButtonImage(likeBtn, newLiked ? "/img/liked.png" : "/img/not_liked.png", 40, 40);
        dataManager.setLiked(idMap.get(playback), newLiked);
        likeBtn.setSelected(newLiked);
    }

    @Override
    public void setMute() {
        imageService.setButtonImage(btnMute, btnMute.isSelected() ? "/img/mute.png" : "/img/volume.png", 30, 30);
        playback.setMute(btnMute.isSelected());
    }

    @Override
    public void setVolume() {
        /*
         * Volume 변경
         * */
        // 볼륨변경시 mute 해제
        btnMute.setSelected(false);
        setMute();

        volume = volumeSlider.getValue();
        playback.setVolume(volume / 100);
    }

    /**
     * Add plays to MusicInfo
     */
    private void addPlays() {
        dataManager.addPlays(idMap.get(playback));
    }

}
