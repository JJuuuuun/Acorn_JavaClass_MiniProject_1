package Player;

import javafx.scene.media.Media;

public class MusicInfo {
    int id;
    Media media;

    public MusicInfo(int id, String url) {
        this.id = id;
        this.media = new Media(url);
    }

    public int getId() {
        return id;
    }

    public Media getMedia() {
        return media;
    }
}
