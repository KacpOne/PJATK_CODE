package zad1;

import java.sql.*;


public class Database {

    private String url;
    private TravelData travelData;

    Database(String url, TravelData travelData) {
        this.travelData = travelData;
        this.url = url;
    }

    void create() {

        Connection connection;
        try {
            connection = DriverManager.getConnection(url);
            Statement stmt = connection.createStatement();
            try {
                stmt.executeUpdate("drop table Tabela");
            }catch (SQLException ignored){}
            stmt.executeUpdate("CREATE TABLE Tabela(info VARCHAR (100))");
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("insert into traveldata (info) values (?)");

            for (Info i : travelData.getInfo()) {
                preparedStatement.setString(1, i.toString());
                preparedStatement.execute();
            }

        } catch (SQLException ignored) {
        }
    }

    public void showGui() {
        new GUI(travelData.getInfo());
    }
}