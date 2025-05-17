import java.sql.*;
import java.util.Scanner;

public class Library {
    private static final String URL = "jdbc:mysql://localhost:3306/library_db";
    private static final String USER = "root";
    private static final String PASSWORD = "nive0404";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            while (true) {
                System.out.println("\n--- Library Issue System ---");
                System.out.println("1. Issue Book");
                System.out.println("2. View Issued Books");
                System.out.println("3. Exit");
                System.out.print("Choice: ");
                int ch = scanner.nextInt();

                if (ch == 1) {
                    scanner.nextLine();
                    System.out.print("Enter student name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter book title: ");
                    String book = scanner.nextLine();
                    System.out.print("Enter issue date (YYYY-MM-DD): ");
                    String date = scanner.nextLine();

                    String sql = "INSERT INTO issues (student_name, book_title, issue_date) VALUES (?, ?, ?)";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, name);
                    ps.setString(2, book);
                    ps.setDate(3, Date.valueOf(date));
                    ps.executeUpdate();

                    System.out.println("✅ Book issued!");
                } else if (ch == 2) {
                    ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM issues");
                    System.out.println("ID | Student | Book | Date");
                    while (rs.next()) {
                        System.out.println(rs.getInt("id") + " | " +
                                rs.getString("student_name") + " | " +
                                rs.getString("book_title") + " | " +
                                rs.getDate("issue_date"));
                    }
                } else {
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
