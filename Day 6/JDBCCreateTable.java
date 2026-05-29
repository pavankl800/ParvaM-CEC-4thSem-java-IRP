import java.lang.Thread.State;
import java.sql.*;

public class JDBCCreateTable {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/students_db";
        String user = "root";
        String password = "root";

        String query = "CREATE TABLE IF NOT EXISTS internship_registration ("
                + "student_id INT PRIMARY KEY AUTO_INCREMENT, "
                + "full_name VARCHAR(100) NOT NULL, "
                + "email_id VARCHAR(120) UNIQUE NOT NULL, "
                + "phone BIGINT UNIQUE, "
                + "address TEXT, "
                + "college_name VARCHAR(150), "
                + "department VARCHAR(100), "
                + "semester INT, "
                + "domain_name ENUM('Java', 'Python', 'AWS', 'AIML') NOT NULL, "
                + "internship_fees DECIMAL(10, 2), "
                + "is_certified BOOLEAN DEFAULT FALSE, "
                + "start_date DATE, "
                + "end_date DATE, "
                + "registration_date DATETIME, "
                + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                + "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, "
                + "deleted_at TIMESTAMP NULL DEFAULT NULL"
                + ")";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to MySQL!");

            Statement stmt = conn.createStatement();

            stmt.executeUpdate(query);

            System.out.println("internship_registration table created successfully!");

            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver Not Found!");
        } catch (SQLException e) {
            System.out.println("SQL Error!");
            System.out.println(e.getMessage());
        }
    }
}