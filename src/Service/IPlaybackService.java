package Service;

import javafx.event.Event;
import javafx.scene.Parent;

public interface IPlaybackService {
    /**
     * TODO: 이전 음악 재생
     */
    void playPrevMusic();

    /**
     * TODO: 재생
     */
    void play();

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
     */
    void playNextMusic();

    /**
     * 플레이어의 반복 설정
     *
     * @param event ActionEvent
     */
    void setRepeat(Event event);

    /**
     * 셔플 상태를 설정함
     *
     * @param event Event
     */
    void setShuffle(Event event);

    void setMute();

    void setVolume();

    /**
     * @param event
     */
    void seek(Event event);

    /**
     * 현재 곡의 모든 정보를 가져옴
     * 정보 화면 업데이트를 위해 사용
     *
     * @param parent Parent
     */
    void getInfoInstance(Parent parent);

    /**
     * 현재 플레이어의 인스턴스 초기화
     *
     * @param parent Parent
     */
    void getPlaybackInstance(Parent parent);

    /**
     * 현재 곡의 Liked 상태를 DB에서 가져옴
     * 정보화면 업데이트를 위해 사용
     *
     * @param parent Parent
     */
    void getLiked(Parent parent);

    /**
     * 좋아요 상태를 설정함
     *
     * @param event ActionEvent
     */
    void setLiked(Event event);


}
