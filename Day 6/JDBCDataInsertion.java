import java.sql.*;

public class JDBCDataInsertion {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/students_db";
        String user = "root";
        String password = "root";

        String insertQuery = "INSERT INTO internship_registration "
                + "(full_name, email_id, phone, address, college_name, department, semester, domain_name, internship_fees, is_certified, start_date, end_date, registration_date)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(url, user, password);

            System.out.println("Connected to MySQL");
            PreparedStatement ps = conn.prepareStatement(insertQuery);

            ps.setString(1, "Akshay Rao");
            ps.setString(2, "akshay@gmail.com");
            ps.setLong(3, 9856234521L);
            ps.setString(4, "Near City Engineering College, Kanakapura Road, Doddakallasandra, Bengaluru");
            ps.setString(5, "City Engineering College");
            ps.setString(6, "Computer Science & Engineering");
            ps.setInt(7, 6);
            ps.setString(8, "Java");
            ps.setDouble(9, 4999.99);
            ps.setBoolean(10, true);
            ps.setDate(11, Date.valueOf("2026-05-11"));
            ps.setDate(12, Date.valueOf("2026-05-21"));
            ps.setTimestamp(13, new Timestamp(System.currentTimeMillis()));

            ps.executeUpdate();

            ps.setString(1, "Ajay Rao");
            ps.setString(2, "ajay@gmail.com");
            ps.setLong(3, 9856235621L);
            ps.setString(4, "Near City Engineering College, Kanakapura Road, Doddakallasandra, Bengaluru");
            ps.setString(5, "City Engineering College");
            ps.setString(6, "Artificial Intelligence & Machine Learning");
            ps.setInt(7, 4);
            ps.setString(8, "Python");
            ps.setDouble(9, 5999.99);
            ps.setBoolean(10, true);
            ps.setDate(11, Date.valueOf("2026-05-15"));
            ps.setDate(12, Date.valueOf("2026-05-25"));
            ps.setTimestamp(13, new Timestamp(System.currentTimeMillis()));

            ps.executeUpdate();

            ps.setString(1, "Abhishek");
            ps.setString(2, "abhi@gmail.com");
            ps.setLong(3, 9856456621L);
            ps.setString(4, "Near NICE Road Toll, Kanakapura Road, Bengaluru");
            ps.setString(5, "City Engineering College");
            ps.setString(6, "Electronics & Communication Engineering");
            ps.setInt(7, 6);
            ps.setString(8, "AWS");
            ps.setDouble(9, 9999.99);
            ps.setBoolean(10, true);
            ps.setDate(11, Date.valueOf("2026-05-10"));
            ps.setDate(12, Date.valueOf("2026-05-30"));
            ps.setTimestamp(13, new Timestamp(System.currentTimeMillis()));

            ps.executeUpdate();

            System.out.println("3 Single Insertions Completed");

            String[] domains = { "Java", "Python", "AWS", "AIML" };
            for (int i = 1; i <= 5; i++) {
                ps.setString(1, "Student " + i);
                ps.setString(2, "student " + i + "@gmail.com");
                ps.setLong(3, 9856345145L + i);
                ps.setString(4, "City " + i);
                ps.setString(5, "College " + i);
                ps.setString(6, "Department " + i);
                ps.setInt(7, i);
                ps.setString(8, domains[i % domains.length]);
                ps.setDouble(9, 3999.99 + (i * 1000));
                ps.setBoolean(10, i % 2 == 0);

                ps.setDate(11, Date.valueOf("2026-05-01"));
                ps.setDate(12, Date.valueOf("2026-05-31"));
                ps.setTimestamp(13, new Timestamp(System.currentTimeMillis()));

                ps.addBatch();
            }

            ps.executeBatch();

            System.out.println("5 Bulk Insertions Completed!");

            ps.close();
            conn.close();

            System.out.println("Connection Closed!");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver Not Found!");
        } catch (SQLException e) {
            System.out.println("SQL Error!");
            System.out.println(e.getMessage());
        }
    }
}