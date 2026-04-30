import java.sql.*;

public class DBConnection {
    public static Connection getConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/eventdb";
            String user = "root";
            String password = "root123"; // change if needed

            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, password);

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}