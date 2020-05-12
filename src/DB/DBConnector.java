package DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnector {
    final String DRIVER = "org.sqlite.JDBC";
    final String DB = "jdbc:sqlite:src/resources/Question.db";
    final String storeSQL = "Insert into Question " +
                            "(userID, title, question) " +
                            "Values (?, ?, ?);";
    final String selectSQL = "Select * " +
                             "from Question " +
                             "where userID = ? ;";

    final int USER_ID = 0;
    final int TITLE = 1;
    final int QUESTION = 2;

    Connection connection;

    public DBConnector() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public boolean storeData(List<String> storeData) {
        if(storeData == null)
            return false;

        try {
            connection = DriverManager.getConnection(DB);
            PreparedStatement preparedStatement = connection.prepareStatement(storeSQL);

            preparedStatement.setString(USER_ID+1, storeData.get(USER_ID));
            preparedStatement.setString(TITLE+1, storeData.get(TITLE));
            preparedStatement.setString(QUESTION+1, storeData.get(QUESTION));

            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<String> getData(String userID) {

        List<String> getDataLst = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(DB);
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(USER_ID+1, userID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                getDataLst.add(resultSet.getString(TITLE + 1));
                getDataLst.add(resultSet.getString(QUESTION + 1));
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
//            e.printStackTrace();
            return null;
        }
        return getDataLst;
    }
}
