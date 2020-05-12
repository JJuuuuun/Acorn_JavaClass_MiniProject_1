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
     *
     * @param event Event
     */
    void setShuffle(Event event);

    /**
     *
     * @param event
     */
    void setMute(Event event);

    /**
     *
     * @param event
     */
    void setVolume(Event event);

    /**
     *
     * @param event
     */
    void seek(Event event);

    /**
     * 현재 곡의 모든 정보를 가져옴
     * 정보 화면 업데이트를 위해 사용
     *
     * @param parent Parent
     */
    void getInfos(Parent parent);

    /**
     * 재생 큐를 가져옴
     * 정보 화면 업데이트를 위해 사용
     *
     * @param parent Parent
     */
    void getQueue(Parent parent);

    /**
     * 재생 큐를 가져옴
     * 셔플 버튼이 눌리는 경우 발동
     *
     * @param event Event
     */
    void getQueue(Event event);

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
    void setLiked(ActionEvent event);


}
