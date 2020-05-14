package Service;

import javafx.scene.Parent;

public interface IPlaybackService {
    /**
     * 이전 음악 재생
     */
    void playPrevMusic();

    /**
     * 재생
     */
    void play();

    /**
     * 일시 정지
     */
    void pause();

    /**
     * 정지
     */
    void stop();


    /**
     * 다음 음악 재생
     */
    void playNextMusic();

    /**
     * 플레이어의 반복 설정
     */
    void setRepeat();

    /**
     * 셔플 상태를 설정함
     */
    void setShuffle();

    void setMute();

    void setVolume();

    /**
     * 현재 슬라이더의 위치로 음악 탐색
     */
    void seek();

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
     */
    void getLiked();

    /**
     * 좋아요 상태를 설정함
     */
    void setLiked();


}
