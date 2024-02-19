import java.sql.*;

public class Demo {
    public static void main(String[] args) throws SQLException {
        // 1. Connect to DB
        // 2. Execute Query
        // 3. Print Result

        //Connect to DB
        String dbUrl = "jdbc:mysql://localhost:" + System.getenv("MYSQL_PORT") + "/soft_uni";
        String usernameMySQL = System.getenv("YOUR_DB_USERNAME");
        String passwordMySQL = System.getenv("YOUR_DB_PASSWORD");

        Connection connection = DriverManager.getConnection(dbUrl, usernameMySQL, passwordMySQL);

        // 2. Execute Query
        PreparedStatement updateStatement = connection.prepareStatement("UPDATE employees SET first_name = ? WHERE employee_id = ?");

//        connection.setAutoCommit(false);
        updateStatement.setString(1, "Changed");
        updateStatement.setLong(2, 3);

        int updateResult = updateStatement.executeUpdate();

//        if(updateResult > 1){
//            connection.rollback();
//        }

        System.out.println(updateResult);

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM employees WHERE salary > ? LIMIT 10");

        preparedStatement.setDouble(1,17000.0);
        ResultSet resultSet = preparedStatement.executeQuery();

        // 3. Print Result
        while(resultSet.next()){
            long id = resultSet.getLong("employee_id");
//            resultSet.getLong(1);
            String firstName = resultSet.getString("first_name");
            double salary = resultSet.getDouble("salary");
            Timestamp hireDate = resultSet.getTimestamp("hire_date");

            System.out.printf("%d - %s %.2f %s%n", id, firstName, salary, hireDate);
        }
    }
}
