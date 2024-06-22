import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class AddDrugs {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            String url = "jdbc:mysql://localhost:3306/pharmacy_database";
            String username = "kbiney";
            String password = "1234";

            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();

            // Prompt the user to input drug details
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

            // Insert the new drug into the database
            String insertSQL = "INSERT INTO drugs (code, name, description, price, dosage) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
                preparedStatement.setString(1, code);
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, description);
                preparedStatement.setDouble(4, price);
                preparedStatement.setString(5, dosage);
                preparedStatement.executeUpdate();
                System.out.println("\nDrug added successfully!");
            }

            /*  Execute a query to retrieve and display all drugs
            resultSet = statement.executeQuery("SELECT * FROM drugs");

             Display the results
            System.out.println("Drugs in the database:");
            while (resultSet.next()) {
                code = resultSet.getString("code");
                name = resultSet.getString("name");
                description = resultSet.getString("description");
                price = resultSet.getDouble("price");
                dosage = resultSet.getString("dosage");

                System.out.println("Code: " + code + " | Name: " + name + " | Description: " + description + " | Price: " + price + " | Dosage: " + dosage);
            }*/

        } catch (ClassNotFoundException e) {
            System.err.println("Error loading MySQL JDBC driver: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        } finally {
            // Close resources
            try {
                if (resultSet != null) resultSet.close();
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
