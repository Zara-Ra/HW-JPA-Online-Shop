package ir.maktab.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBhelper {
    private static final String DB_URL = "jdbc:postgresql://localhost/OnlineShop";
    private static final String USER = "postgres";
    private static final String PASS = "zara12";

    private DBhelper() {
    }

    private static final DBhelper instance = new DBhelper();

    public static DBhelper getInstance() {
        return instance;
    }

    private Connection connection;

    public Connection getConnection() {

        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return connection;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
