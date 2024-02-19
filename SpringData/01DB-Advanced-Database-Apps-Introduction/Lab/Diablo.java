import java.sql.*;
import java.util.Scanner;

public class Diablo {
    public static void main(String[] args) throws SQLException {
        // Connect to DB
//        Connection connection = DriverManager.getConnection(
//                "jdbc:mysql://localhost:mysql_port/diablo", "your_db_username", "your_db_password");
        String dbUrl = "jdbc:mysql://localhost:" + System.getenv("MYSQL_PORT") + "/diablo";
        String usernameMySQL = System.getenv("YOUR_DB_USERNAME");
        String passwordMySQL = System.getenv("YOUR_DB_PASSWORD");

        Connection connection = DriverManager.getConnection(dbUrl, usernameMySQL, passwordMySQL);
        // 2. Execute Query
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT u.id, u.first_name, u.last_name, COUNT(ug.game_id) " +
                "FROM users AS u " +
                "JOIN users_games AS ug ON ug.user_id = u.id " +
                "WHERE u.user_name = ? " + "GROUP BY u.id;");
        preparedStatement.setString(1, username);
        ResultSet result = preparedStatement.executeQuery();

        // 3. Print Result
        result.next();
        Object userId = result.getObject(1);

        if (userId != null) { // Has valid user data
            System.out.printf("User: %s%n%s %s has played %d games",
                    username,
                    result.getString(2),
                    result.getString(3),
                    result.getInt(4));
        }else{ // No user data
            System.out.println("No such user exists");
        }
    }
}
