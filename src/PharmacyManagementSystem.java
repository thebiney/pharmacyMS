import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class PharmacyManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection connection = null;

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            String url = "jdbc:mysql://localhost:3306/pharmacy_database";
            String username = "kbiney";
            String password = "1234";

            connection = DriverManager.getConnection(url, username, password);

            while (true) {
                System.out.println("\n1. Add drug");
                System.out.println("2. Search for a drug");
                System.out.println("3. View all drugs and their suppliers");
                System.out.println("4. View purchase history for a drug");
                System.out.println("5. Exit");
                System.out.print("\nChoose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        addDrug(connection, scanner);
                        break;
                    case 2:
                        searchDrug(connection, scanner);
                        break;
                    case 3:
                        viewAllDrugsAndSuppliers(connection);
                        break;
                    case 4:
                        viewPurchaseHistory(connection, scanner);
                        break;
                    case 5:
                        System.out.println("Thank you for using the Pharmacy Management System");
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Error loading MySQL JDBC driver: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        } finally {
            // Close resources
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing the database resources: " + e.getMessage());
            }

            if (scanner != null) {
                scanner.close();
            }
        }
    }

    private static void addDrug(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("\nEnter drug code:");
        String code = scanner.nextLine();
        System.out.println("\nEnter drug name:");
        String name = scanner.nextLine();
        System.out.println("\nEnter drug description:");
        String description = scanner.nextLine();
        System.out.println("\nEnter drug price:");
        double price = scanner.nextDouble();
        scanner.nextLine();  // Consume the newline
        System.out.println("\nEnter drug dosage:");
        String dosage = scanner.nextLine();
        System.out.println("\nEnter supplier ID:");
        int supplierId = scanner.nextInt();
        scanner.nextLine();  // Consume the newline

        String insertSQL = "INSERT INTO drugs (code, name, description, price, dosage, supplier_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, code);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, description);
            preparedStatement.setDouble(4, price);
            preparedStatement.setString(5, dosage);
            preparedStatement.setInt(6, supplierId);
            preparedStatement.executeUpdate();
            System.out.println("\nDrug added successfully!");
        }
    }

    private static void searchDrug(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("\nEnter drug code or name to search:");
        String searchTerm = scanner.nextLine();

        String searchSQL = "SELECT * FROM drugs WHERE code = ? OR name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(searchSQL)) {
            preparedStatement.setString(1, searchTerm);
            preparedStatement.setString(2, searchTerm);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                do {
                    String code = resultSet.getString("code");
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    double price = resultSet.getDouble("price");
                    String dosage = resultSet.getString("dosage");

                    System.out.println("Code: " + code + " | Name: " + name + " | Description: " + description + " | Price: " + price + " | Dosage: " + dosage);
                } while (resultSet.next());
            } else {
                System.out.println("No drug found with the given code or name.");
            }
        }
    }

    private static void viewAllDrugsAndSuppliers(Connection connection) throws SQLException {
        String query = "SELECT d.code, d.name, s.supplier_name, s.location FROM drugs d JOIN suppliers s ON d.supplier_id = s.id ORDER BY d.name";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            System.out.println("Drugs and their suppliers:");
            while (resultSet.next()) {
                String code = resultSet.getString("code");
                String name = resultSet.getString("name");
                String supplierName = resultSet.getString("supplier_name");
                String location = resultSet.getString("location");

                System.out.println("Code: " + code + " | Name: " + name + " | Supplier: " + supplierName + " | Location: " + location);
            }
        }
    }

    private static void viewPurchaseHistory(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("\nEnter drug name or code to view purchase history:");
        String input = scanner.nextLine();
    
        String query = "SELECT d.code, d.name, ph.purchase_date, ph.buyer, SUM(ph.quantity) AS total_quantity " +
                       "FROM purchase_history ph " +
                       "JOIN drugs d ON ph.drug_code = d.code " +
                       "WHERE d.code = ? OR d.name = ? " +
                       "GROUP BY d.code, d.name, ph.purchase_date, ph.buyer " +
                       "ORDER BY ph.purchase_date";
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, input);
            preparedStatement.setString(2, input);
            ResultSet resultSet = preparedStatement.executeQuery();
    
            if (!resultSet.next()) {
                System.out.println("Error: No purchase history found for the specified drug name or code.");
                return;
            }
    
            // Output drug code, name and purchase history
            String drugCode = resultSet.getString("code");
            String drugName = resultSet.getString("name");
            System.out.println("\nCumulative purchase history for: " + drugCode + " (" + drugName + ")");
            do {
                String purchaseDate = resultSet.getString("purchase_date");
                String buyer = resultSet.getString("buyer");
                int totalQuantity = resultSet.getInt("total_quantity");
    
                System.out.println("Date: " + purchaseDate + " | Buyer: " + buyer + " | Total Quantity Bought: " + totalQuantity);
            } while (resultSet.next());
        }
    }
    
}
