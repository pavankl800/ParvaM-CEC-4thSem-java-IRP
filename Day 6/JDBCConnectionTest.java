import java.sql.*;

public class JDBCConnectionTest {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/testing_db";
        String user = "root";
        String password = "root";
    
        System.out.println("Attempting to connect to MySQL....");
    
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            if (conn != null){
                System.out.println("Connection Successful!");
                System.out.println("Database: " + conn.getCatalog());
                System.out.println("Driver: " + conn.getMetaData().getDriverName());
            }
        } catch(SQLException e){
            System.out.println("Connection Failed!");
            System.out.println("Error: " + e.getMessage());
        }
    }
}