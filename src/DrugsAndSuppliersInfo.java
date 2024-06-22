import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class DrugsAndSuppliersInfo {
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

            // SQL query to retrieve all drugs and their corresponding supplier information
            String searchSQL = "SELECT d.code, d.name, d.description, d.price, d.dosage, s.id, s.name AS supplier_name, s.location " +
                               "FROM drugs d " +
                               "LEFT JOIN suppliers s ON id = s.id";

            try (PreparedStatement preparedStatement = connection.prepareStatement(searchSQL)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                // Display the results
                while (resultSet.next()) {
                    String drugCode = resultSet.getString("code");
                    String drugName = resultSet.getString("name");
                    String drugDescription = resultSet.getString("description");
                    double drugPrice = resultSet.getDouble("price");
                    String drugDosage = resultSet.getString("dosage");
                    String supplierId = resultSet.getString("id");
                    String supplierName = resultSet.getString("supplier_name");
                    String supplierLocation = resultSet.getString("location");

                    System.out.println("Drug Details:");
                    System.out.println("Code: " + drugCode);
                    System.out.println("Name: " + drugName);
                    System.out.println("Description: " + drugDescription);
                    System.out.println("Price: $" + drugPrice);
                    System.out.println("Dosage: " + drugDosage);
                    System.out.println("Supplier Details:");
                    System.out.println("ID: " + supplierId);
                    System.out.println("Name: " + supplierName);
                    System.out.println("Location: " + supplierLocation);
                    System.out.println("------------------------------------------------");
                }

                resultSet.close();
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
}
