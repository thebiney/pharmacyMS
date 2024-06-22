import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class SearchDrug {
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

            // Prompt the user to input the drug code
            System.out.println("Enter drug code to search:");
            String drugCode = scanner.nextLine();

            // Search the database for the specified drug
            String searchSQL = "SELECT * FROM drugs WHERE code = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(searchSQL)) {
                preparedStatement.setString(1, drugCode);
                ResultSet resultSet = preparedStatement.executeQuery();

                // Display the results
                if (resultSet.next()) {
                    String code = resultSet.getString("code");
                    String name = resultSet.getString("name");
                    String desc = resultSet.getString("description");
                    double price = resultSet.getDouble("price");
                    String dose = resultSet.getString("dosage");

                    System.out.println("\nDrug Details:");
                    System.out.println("\nCode: " + code);
                    System.out.println("\nName: " + name);
                    System.out.println("\nDescription: " + desc);
                    System.out.println("\nPrice: $" + price);
                    System.out.println("\nDosage: " + dose);
                } else {
                    System.out.println("\nDrug not found.");
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
