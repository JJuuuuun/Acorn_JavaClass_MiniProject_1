package Database;

import Service.MusicInfo;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.Map;

public interface IDataManager {
    /**
     * Database가 가진 모든 음악을 [player(Mediaplayer), id(Integer)] 쌍 형태로 추가한 맵 반환
     *
     * @return [player(Mediaplayer), id(Integer)] 형태의 맵
     */
    Map<MediaPlayer, Integer> getAllMusics();

    /**
     * MusicInfo 반환
     *
     * @param id 음악 id
     * @return MusicInfo
     */
    MusicInfo getMusicInfo(int id);

    /**
     * 해당 id의 음악 플레이 횟수를 증가시킴
     *
     * @param id 음악의 ID
     */
    void addPlays(int id);

    /**
     * 해당 id의 음악 길이를 해당 값으로 업데이트
     *
     * @param id       음악의 ID
     * @param duration 음악 길이
     */
    void updateMusic(int id, Duration duration);

    /**
     * 해당 id의 음악, 해당 userId의 플레이어의 곡 선호 정보를 가져옴
     * 좋아요 버튼의 상태를 확인하기 위해 사용함
     *
     * @param id 음악의 ID
     * @return 유저의 곡 선호 정보
     */
    boolean getLiked(int id);

    /**
     * 해당 id의 음악, 해당 userId의 플레이어의 곡 선호 정보를 해당 값으로 업데이트
     * 좋아요 버튼의 상태를 반영하기 위해 사용함
     *
     * @param id        음악의 ID
     * @param activated 업데이트된 유저의 곡 선호 정보
     */
    void setLiked(int id, boolean activated);
}
