import java.sql.*;

public class JDBCCRUDOperation {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/students_db";
        String user = "root";
        String password = "root";

        try {

            // Load JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Create Connection
            Connection conn = DriverManager.getConnection(
                    url,
                    user,
                    password);

            System.out.println("✅ Connected to MySQL!");

            // FETCH LAST 5 RECORDS
            System.out.println("\n========== LAST 5 RECORDS ==========");

            String readQuery = "SELECT * FROM internship_registration "
                    + "ORDER BY student_id DESC "
                    + "LIMIT 5";

            PreparedStatement readPs = conn.prepareStatement(readQuery);

            ResultSet rs = readPs.executeQuery();

            while (rs.next()) {

                System.out.println(
                        rs.getInt("student_id")
                                + " | "
                                + rs.getString("full_name")
                                + " | "
                                + rs.getString("domain_name")
                                + " | ₹"
                                + rs.getDouble("internship_fees"));
            }

            // UPDATE OPERATION

            System.out.println("\n========== UPDATE OPERATION ==========");

            String updateQuery = "UPDATE internship_registration "
                    + "SET internship_fees = ?, "
                    + "domain_name = ? "
                    + "WHERE student_id = ?";

            PreparedStatement updatePs = conn.prepareStatement(updateQuery);

            updatePs.setDouble(1, 14999.99);
            updatePs.setString(2, "AIML");
            updatePs.setInt(3, 1);

            int updatedRows = updatePs.executeUpdate();

            System.out.println(
                    "✅ " + updatedRows
                            + " record updated successfully!");

            // DELETE LAST 3 RECORDS

            System.out.println("\n========== DELETE OPERATION ==========");

            String deleteQuery = "DELETE FROM internship_registration "
                    + "ORDER BY student_id DESC "
                    + "LIMIT 3";

            PreparedStatement deletePs = conn.prepareStatement(deleteQuery);

            int deletedRows = deletePs.executeUpdate();

            System.out.println(
                    "✅ " + deletedRows
                            + " records deleted successfully!");

            // SHOW ALL REMAINING DATA

            System.out.println("\n========== REMAINING RECORDS ==========");

            String finalReadQuery = "SELECT * FROM internship_registration "
                    + "ORDER BY student_id";

            PreparedStatement finalPs = conn.prepareStatement(finalReadQuery);

            ResultSet finalRs = finalPs.executeQuery();

            while (finalRs.next()) {

                System.out.println(
                        "ID: "
                                + finalRs.getInt("student_id")

                                + " | Name: "
                                + finalRs.getString("full_name")

                                + " | Email: "
                                + finalRs.getString("email_id")

                                + " | Domain: "
                                + finalRs.getString("domain_name")

                                + " | Fees: ₹"
                                + finalRs.getDouble("internship_fees")

                                + " | Certified: "
                                + finalRs.getBoolean("is_certified"));
            }

            // CLOSE RESOURCES

            rs.close();
            finalRs.close();

            readPs.close();
            updatePs.close();
            deletePs.close();
            finalPs.close();

            conn.close();

            System.out.println("\n✅ Connection Closed!");

        } catch (ClassNotFoundException e) {

            System.out.println("❌ JDBC Driver Not Found!");

        } catch (SQLException e) {

            System.out.println("❌ SQL Error!");
            System.out.println(e.getMessage());
        }
    }
}