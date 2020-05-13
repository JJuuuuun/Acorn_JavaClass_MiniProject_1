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
        //생성자
        this.id = id;
        this.track = track;
        this.year = year;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.lyrics = lyrics;
        String imgurl = "../resources/albumart/" + id + ".jpg";
        //이미지 절대경로
        try {
            if (getClass().getResource(imgurl) != null)
                //imgurl의 정보를 받음 널이 아니라면 처리
                this.albumart = new Image(getClass().getResource(imgurl).toURI().toURL().toString());
            //?
        } catch (MalformedURLException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public Image getAlbumart() {
        return albumart;
    } //앨범아트 정보 반환

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
    //널이 아니라면 타이틀 리턴 , 널이라면 N/A 리턴

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
