import java.sql.*;
import java.util.Scanner;

public class Bus {
    private static final String URL = "jdbc:mysql://localhost:3306/bus_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Abu1997";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            while (true) {
                System.out.println("\n--- Bus Ticket Reservation ---");
                System.out.println("1. Book Ticket");
                System.out.println("2. View All Bookings");
                System.out.println("3. Exit");
                System.out.print("Choose option: ");
                int choice = scanner.nextInt();

                if (choice == 1) {
                    scanner.nextLine();  // consume newline
                    System.out.print("Enter passenger name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter route: ");
                    String route = scanner.nextLine();

                    System.out.print("Enter date (YYYY-MM-DD): ");
                    String date = scanner.nextLine();

                    String insertSQL = "INSERT INTO bookings (name, route, travel_date) VALUES (?, ?, ?)";
                    PreparedStatement ps = conn.prepareStatement(insertSQL);
                    ps.setString(1, name);
                    ps.setString(2, route);
                    ps.setDate(3, Date.valueOf(date));
                    int rows = ps.executeUpdate();

                    if (rows > 0) {
                        System.out.println("✅ Ticket booked successfully!");
                    }
                } else if (choice == 2) {
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM bookings");

                    System.out.println("ID | Name | Route | Date");
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String name = rs.getString("name");
                        String route = rs.getString("route");
                        Date travelDate = rs.getDate("travel_date");

                        // Simplified printing
                        System.out.println(id + " | " + name + " | " + route + " | " + travelDate);
                    }
                } else {
                    System.out.println("Exiting...");
                    break;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
