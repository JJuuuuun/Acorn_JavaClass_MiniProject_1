package Database;

import Service.MusicInfo;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.nio.file.Paths;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DataManager implements IDataManager {
    private final static String DRIVER = "org.sqlite.JDBC";
    private final static String DB = "jdbc:sqlite:src/resources/core.db";
    private Connection connect;

    public DataManager() {
        try {
            Class.forName(DRIVER);
            connect = DriverManager.getConnection(DB);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public MusicInfo getMusicInfo(int id) {
        String sql = "select * from musicInfo where id = ?";
        MusicInfo result = null;
        try {
            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = new MusicInfo(
                        resultSet.getInt("id"),
                        resultSet.getString("fileurl"),
                        resultSet.getInt("track"),
                        resultSet.getInt("year"),
                        resultSet.getString("title"),
                        resultSet.getString("artist"),
                        resultSet.getString("album"),
                        resultSet.getString("lyrics")
                );
            }
            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Map<MediaPlayer, Integer> getAllMusics() {
        String sql = "select * from musicInfo";
        Map<MediaPlayer, Integer> result = new HashMap<>();
        try {
            PreparedStatement statement = connect.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.put(new MediaPlayer(new Media(
                                Paths.get(resultSet.getString("fileurl")).toUri().toString())),
                        resultSet.getInt("id"));
            }
            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void addPlays(int id) {
        String sql = "update recommendInfo " +
                "set play = play + 1 " +
                "where id = ?";
        try {
            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setInt(1, id);

            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateMusic(int id, Duration duration) {
        String sql = "update musicInfo set duration = ? where id = ?";
        try {
            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setInt(1, (int) duration.toSeconds());
            statement.setInt(2, id);

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean getLiked(int id) {
        boolean liked = false;
        String sql = "select * from userLovedInfo where user_id = 1 and music_id = ?";
        try {
            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet result = statement.executeQuery();
            if (result.next())
                if (result.getInt("user_loved") == 1) liked = true;

            result.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liked;
    }

    /**
     * 좋아요 값을 변경함.
     * 조건 : 좋아요 버튼 액션
     * user_id value를 login > member 테이블의 ids를 참조하도록 해야함 (primary key 전환 후)
     *
     * @param id        해당 음악 id
     * @param activated 바뀐 좋아요 상태 활성화 여부
     */
    @Override
    public void setLiked(int id, boolean activated) {
        // activated 상태에 따라 userLikedInfo table의 해당 id 튜플 (isActive : int) 속성 변경
        boolean exists = false;
        String sql = "select * from userLovedInfo where user_id = 1 and music_id = ?";
        try {
            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet result = statement.executeQuery();
            if (result.next()) exists = true;

            result.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (exists) updateLikedInfo(id, activated);
        else insertLikedInfo(id, activated);
    }

    /**
     * userLovedInfo 튜플 추가
     * user_id value를 login > member 테이블의 ids를 참조하도록 해야함 (primary key 전환 후)
     *
     * @param id        ID of Music
     * @param activated Liked Status
     */
    private void insertLikedInfo(int id, boolean activated) {
        String sql = "insert into userLovedInfo(user_id, music_id, user_loved) VALUES ('1', ?, ?)";
        try {
            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setInt(1, id);
            statement.setInt(2, activated ? 1 : 0);

            statement.execute();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * userLovedInfo 튜플 추가
     * user_id value를 login > member 테이블의 ids를 참조하도록 해야함 (primary key 전환 후)
     *
     * @param id        ID of Music
     * @param activated Liked Status
     */
    private void updateLikedInfo(int id, boolean activated) {
        String sql = "update userLovedInfo set user_loved = ? where user_id = 1 and music_id = ?";
        String sql2 = "update recommendInfo set liked = " +
                (activated ? "liked + 1 " : "liked - 1 ") +
                "where id = ?";
        try {
            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setInt(1, activated ? 1 : 0);
            statement.setInt(2, id);
            statement.execute();
            statement.clearParameters();

            statement = connect.prepareStatement(sql2);
            statement.setInt(1, id);
            statement.execute();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
