package Player.Database;

import Player.MusicInfo;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.Map;

public interface IDataManager {
    Map<MediaPlayer, Integer> getAllMusics();
    /**
     * MusicInfo 반환
     *
     * @param id 음악 id
     * @return MusicInfo
     */
    MusicInfo getMusicInfo(int id);

    String getLyrics(int id);

    void addPlays(int id);

    void updateMusic(int id, Duration duration);

    boolean getLiked(int id);

    void setLiked(int id, boolean activated);
}
