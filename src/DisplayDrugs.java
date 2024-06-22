import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class DisplayDrugs {
    public static void main(String[] args) {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            String url = "jdbc:mysql://localhost:3306/pharmacy_database";
            String username = "kbiney";
            String password = "1234";

            Connection connection = DriverManager.getConnection(url, username, password);

            // Create a statement
            Statement statement = connection.createStatement();

            // Execute a query
            ResultSet resultSet = statement.executeQuery("SELECT * FROM drugs");

            // Display the results
            while (resultSet.next()) {
                // Assuming the table has 'id' and 'name' columns
                //int id = resultSet.getInt("");
                String code = resultSet.getString("code");
                String name = resultSet.getString("name");
                String desc = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                String dose = resultSet.getString("dosage");


                System.out.println("Drug Details:");
                System.out.println("Code: " + code);
                System.out.println("Name: " + name);
                System.out.println("Description: " + desc);
                System.out.println("Price: $" + price);
                System.out.println("Dosage: " + dose);
            }

            // Close resources
            resultSet.close();
            statement.close();
            connection.close();

        } catch (ClassNotFoundException e) {
            System.err.println("Error loading MySQL JDBC driver: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
    }
}