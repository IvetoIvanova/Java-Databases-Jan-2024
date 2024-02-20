package Exercise;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBTools {

    private final String url = "jdbc:mysql://localhost:mysql_port/";
    private Connection connection;

    public DBTools(String username, String password, String dbName) {
        Properties properties = new Properties();
        properties.setProperty("user", username);
        properties.setProperty("password", password);
        try {
            connection = DriverManager.getConnection(url + dbName, properties);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Connection getConnection() {
        return connection;
    }
}
