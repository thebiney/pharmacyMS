import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class AddSupplierInfo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection connection = null;
        Statement statement = null;

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            String url = "jdbc:mysql://localhost:3306/pharmacy_database";
            String username = "kbiney";
            String password = "1234";

            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();

            // Prompt the user to input supplier details
            System.out.println("\nEnter supplier ID:");
            String id = scanner.nextLine();
            System.out.println("\nEnter supplier name:");
            String name = scanner.nextLine();
            System.out.println("\nEnter supplier location:");
            String location = scanner.nextLine();

            // Insert the new supplier into the database
            String insertSQL = "INSERT INTO suppliers (id, name, location) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
                preparedStatement.setString(1, id);
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, location);
                preparedStatement.executeUpdate();
                System.out.println("\nSupplier details added successfully!");
            }

        } catch (ClassNotFoundException e) {
            System.err.println("Error loading MySQL JDBC driver: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        } finally {
            // Close resources
            try {
                if (statement != null) statement.close();
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
