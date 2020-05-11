package Service;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Parent;

public interface IPlaybackService {
    /**
     * TODO: 이전 음악 재생
     *
     * @param event ActionEvent
     */
    void playPrevMusic(ActionEvent event);

    /**
     * TODO: 재생
     *
     * @param event ActionEvent
     */
    void play(ActionEvent event);

    /**
     * TODO: 일시 정지
     */
    void pause();

    /**
     * TODO: 정지
     */
    void stop();


    /**
     * TODO: 다음 음악 재생
     *
     * @param event ActionEvent
     */
    void playNextMusic(ActionEvent event);

    /**
     * 플레이어의 반복 설정
     *
     * @param event ActionEvent
     */
    void setRepeat(ActionEvent event);

    /**
     * 셔플 상태를 설정함
     * @param event Event
     */
    void shuffle(Event event);

    void setMute(Event event);

    void setVolume(Event event);

    void seek(Event event);

    void getInfos(Parent parent);


    void getLiked(Parent parent);

    /**
     * 좋아요 상태를 설정함
     *
     * @param event
     */
    void setLiked(ActionEvent event);


}
