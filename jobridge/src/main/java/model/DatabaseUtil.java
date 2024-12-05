package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
	private static final String URL = "jdbc:h2:D:\\DB/mental_tool";
//    private static final String URL = "jdbc:mariadb://dion.mydns.jp:3306/mental_tool";
    private static final String USER = "jobridge";
    private static final String PASSWORD = "";

    static {
        try {
        	Class.forName("org.h2.Driver");
            // MariaDB JDBC ドライバのロード
//            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MariaDB JDBC Driver not found. Please add the driver to your classpath.", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
