package Player;

import javafx.scene.media.Media;

public class MusicInfo {
    int id;
    Media media;

    // null 인 경우: -1 혹은 null로 처리
    int track;
    int year;
    String title;
    String artist;
    String album;
    String lyrics;

    public MusicInfo(int id, String url,
                     int track, int year,
                     String title, String artist, String album,
                     String lyrics) {
        System.out.println(url);
        this.id = id;
        this.media = new Media(url);
        this.track = track;
        this.year = year;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.lyrics = lyrics;
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return "MusicInfo{" +
                "id=" + id +
                ", track=" + track +
                ", year=" + year +
                ", title='" + this.getTitle() + '\'' +
                ", artist='" + this.getArtist() + '\'' +
                ", album='" + this.getAlbum() + '\'' +
                ", lyrics='" + this.getLyrics() + '\'' +
                '}';
    }

    public int getTrack() {
        return track;
    }

    public int getYear() {
        return year;
    }

    public String getTitle() {
        return title != null ? title : "N/A";
    }

    public String getArtist() {
        return artist != null ? artist : "N/A";
    }

    public String getAlbum() {
        return album != null ? album : "N/A";
    }

    public String getLyrics() {
        return lyrics != null ? lyrics : "가사 없음";
    }

    public int getId() {
        return id;
    }

    public Media getMedia() {
        return media;
    }
}
