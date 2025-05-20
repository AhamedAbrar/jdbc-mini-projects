import java.sql.*;
import java.util.Scanner;

public class Food {
    private static final String URL = "jdbc:mysql://localhost:3306/food_order_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Abu1997";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            while (true) {
                System.out.println("\n--- Food Order System ---");
                System.out.println("1. Place Order");
                System.out.println("2. View Orders");
                System.out.println("3. Exit");
                System.out.print("Choose option: ");
                int choice = scanner.nextInt();

                if (choice == 1) {
                    scanner.nextLine();
                    System.out.print("Enter customer name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter food item: ");
                    String item = scanner.nextLine();
                    System.out.print("Enter quantity: ");
                    int qty = scanner.nextInt();

                    String sql = "INSERT INTO orders (customer_name, food_item, quantity) VALUES (?, ?, ?)";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, name);
                    ps.setString(2, item);
                    ps.setInt(3, qty);
                    ps.executeUpdate();

                    System.out.println("✅ Order placed!");
                } else if (choice == 2) {
                    ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM orders");
                    System.out.println("ID | Name | Item | Qty");
                    while (rs.next()) {
                        System.out.println(rs.getInt("id") + " | " +
                                rs.getString("customer_name") + " | " +
                                rs.getString("food_item") + " | " +
                                rs.getInt("quantity"));
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
