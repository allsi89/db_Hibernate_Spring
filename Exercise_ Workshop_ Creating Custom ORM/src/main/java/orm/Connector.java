package orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connector {
    public static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/";
    public static final String TIME_ZONE_STR = "?serverTimezone=UTC";

    private static Connection connection;

    public Connector() {
    }

    public static void createConnection(String username,
                                        String password, String dbName) throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", username);
        props.setProperty("password", password);
        connection = DriverManager.getConnection(CONNECTION_URL + dbName + TIME_ZONE_STR, props);
    }

    public static Connection getConnection(){
        return connection;
    }
}
