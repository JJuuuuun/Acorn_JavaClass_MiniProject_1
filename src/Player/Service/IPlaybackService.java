package Player.Service;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.media.Media;

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
     */
    void shuffle();

    void setMute(Event event);

    void setVolume(Event event);

    void seek(Event event);

    /**
     * 가사를 전달함
     */
    String getLyrics();

    /**
     * 좋아요 상태를 설정함
     *
     * @param event
     */
    void setLiked(ActionEvent event);


    /**
     * 음악을 건네줌. 음악의 DB를 주도록 수정해야함.
     *
     * @return media
     */
    Media getMedia();

}
