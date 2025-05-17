import java.sql.*;
import java.util.Scanner;

public class Car {
    private static final String URL = "jdbc:mysql://localhost:3306/car_rental_db";
    private static final String USER = "root";
    private static final String PASSWORD = "nive0404";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            while (true) {
                System.out.println("\n--- Car Rental System ---");
                System.out.println("1. Rent a Car");
                System.out.println("2. View Rentals");
                System.out.println("3. Exit");
                System.out.print("Choose option: ");
                int choice = scanner.nextInt();

                if (choice == 1) {
                    scanner.nextLine(); // clear buffer
                    System.out.print("Enter customer name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter car model: ");
                    String car = scanner.nextLine();
                    System.out.print("Enter rental date (YYYY-MM-DD): ");
                    String date = scanner.nextLine();

                    String sql = "INSERT INTO rentals (customer_name, car_model, rental_date) VALUES (?, ?, ?)";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, name);
                    ps.setString(2, car);
                    ps.setDate(3, Date.valueOf(date));
                    ps.executeUpdate();

                    System.out.println("✅ Car rented successfully!");
                } else if (choice == 2) {
                    ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM rentals");
                    System.out.println("ID | Name | Car | Date");
                    while (rs.next()) {
                        System.out.println(rs.getInt("id") + " | " +
                                rs.getString("customer_name") + " | " +
                                rs.getString("car_model") + " | " +
                                rs.getDate("rental_date"));
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
