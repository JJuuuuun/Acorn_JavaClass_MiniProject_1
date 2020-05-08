package Service;

import javafx.scene.image.Image;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class MusicInfo {
    int id;
    // null 인 경우: -1 혹은 null로 처리
    int track;
    int year;
    String title;
    String artist;
    String album;
    String lyrics;
    Image albumart;

    public MusicInfo(int id, String url,
                     int track, int year,
                     String title, String artist, String album,
                     String lyrics) {
        this.id = id;
        this.track = track;
        this.year = year;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.lyrics = lyrics;
        String imgurl = "../resources/albumart/" + id + ".jpg";
        try {
            if (getClass().getResource(imgurl) != null)
                this.albumart = new Image(getClass().getResource(imgurl).toURI().toURL().toString());
        } catch (MalformedURLException | URISyntaxException e) {
            e.printStackTrace();
        }
        System.out.println(this.toString());
    }

    public Image getAlbumart() {
        return albumart;
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

}
