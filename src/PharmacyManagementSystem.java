import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.*;
import java.sql.*;

class Drug {
    private String code;
    private String name;
    private String description;
    private double price;
    private List<Supplier> suppliers;
    private List<Purchase> purchaseHistory;

    public Drug(String code, String name, String description, double price) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.price = price;
        this.suppliers = new ArrayList<>();
        this.purchaseHistory = new ArrayList<>();
    }

    public String getCode() { return code; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public List<Supplier> getSuppliers() { return suppliers; }
    public List<Purchase> getPurchaseHistory() { return purchaseHistory; }

    public void addSupplier(Supplier supplier) { suppliers.add(supplier); }
    public void addPurchase(Purchase purchase) { purchaseHistory.add(purchase); }
    public void sortSuppliersByName() { suppliers.sort(Comparator.comparing(Supplier::getName)); }
    public void sortSuppliersByLocation() { suppliers.sort(Comparator.comparing(Supplier::getLocation)); }
}

class Supplier {
    private String id;
    private String name;
    private String location;

    public Supplier(String id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getLocation() { return location; }
}

class Customer {
    private String id;
    private String name;

    public Customer(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public String getName() { return name; }
}

class Purchase {
    private String customerId;
    private Date date;
    private int quantity;

    public Purchase(String customerId, Date date, int quantity) {
        this.customerId = customerId;
        this.date = date;
        this.quantity = quantity;
    }

    public String getCustomerId() { return customerId; }
    public Date getDate() { return date; }
    public int getQuantity() { return quantity; }
}

//Testing commenting, ignore this comment
public class PharmacyManagementSystem {
    private HashMap<String, Drug> drugs;
    private HashMap<String, Supplier> suppliers;
    private HashMap<String, Customer> customers;

    public PharmacyManagementSystem() {
        this.drugs = new HashMap<>();
        this.suppliers = new HashMap<>();
        this.customers = new HashMap<>();
    }

    // Database connection
    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/pharmacy_database";
        String user = "your_username";
        String password = "your_password";
        return DriverManager.getConnection(url, user, password);
    }

    // Add a drug to the system and database
    public void addDrug(Drug drug) {
        drugs.put(drug.getCode(), drug);
        try (Connection conn = getConnection()) {
            String sql = "INSERT INTO drugs (code, name, description, price) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, drug.getCode());
                stmt.setString(2, drug.getName());
                stmt.setString(3, drug.getDescription());
                stmt.setDouble(4, drug.getPrice());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Remove a drug from the system and database
    public void removeDrug(String drugCode) {
        drugs.remove(drugCode);
        try (Connection conn = getConnection()) {
            String sql = "DELETE FROM drugs WHERE code = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, drugCode);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get a drug by code from the system or database
    public Drug getDrug(String drugCode) {
        if (drugs.containsKey(drugCode)) {
            return drugs.get(drugCode);
        }
        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM drugs WHERE code = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, drugCode);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Drug drug = new Drug(
                                rs.getString("code"),
                                rs.getString("name"),
                                rs.getString("description"),
                                rs.getDouble("price")
                        );
                        drugs.put(drugCode, drug);
                        return drug;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Add a supplier to the system and database
    public void addSupplier(Supplier supplier) {
        suppliers.put(supplier.getId(), supplier);
        try (Connection conn = getConnection()) {
            String sql = "INSERT INTO suppliers (id, name, location) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, supplier.getId());
                stmt.setString(2, supplier.getName());
                stmt.setString(3, supplier.getLocation());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get a supplier by ID from the system or database
    public Supplier getSupplier(String supplierId) {
        if (suppliers.containsKey(supplierId)) {
            return suppliers.get(supplierId);
        }
        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM suppliers WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, supplierId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Supplier supplier = new Supplier(
                                rs.getString("id"),
                                rs.getString("name"),
                                rs.getString("location")
                        );
                        suppliers.put(supplierId, supplier);
                        return supplier;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Add a customer to the system and database
    public void addCustomer(Customer customer) {
        customers.put(customer.getId(), customer);
        try (Connection conn = getConnection()) {
            String sql = "INSERT INTO customers (id, name) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, customer.getId());
                stmt.setString(2, customer.getName());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get a customer by ID from the system or database
    public Customer getCustomer(String customerId) {
        if (customers.containsKey(customerId)) {
            return customers.get(customerId);
        }
        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM customers WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, customerId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Customer customer = new Customer(
                                rs.getString("id"),
                                rs.getString("name")
                        );
                        customers.put(customerId, customer);
                        return customer;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Search for suppliers of a specific drug based on parameters
    public List<Supplier> searchSuppliers(String drugCode, String parameter, String value) {
        Drug drug = getDrug(drugCode);
        if (drug == null) return new ArrayList<>();
        List<Supplier> matchedSuppliers = new ArrayList<>();
        for (Supplier supplier : drug.getSuppliers()) {
            if (parameter.equals("location") && supplier.getLocation().equals(value)) {
                matchedSuppliers.add(supplier);
            }
        }
        return matchedSuppliers;
    }

    // Add purchase history to a drug
    public void addPurchase(String drugCode, Purchase purchase) {
        Drug drug = getDrug(drugCode);
        if (drug != null) {
            drug.addPurchase(purchase);
        }
    }

    // Sort suppliers of a specific drug by name
    public void sortSuppliersByName(String drugCode) {
        Drug drug = getDrug(drugCode);
        if (drug != null) {
            drug.sortSuppliersByName();
        }
    }

    // Sort suppliers of a specific drug by location
    public void sortSuppliersByLocation(String drugCode) {
        Drug drug = getDrug(drugCode);
        if (drug != null) {
            drug.sortSuppliersByLocation();
        }
    }

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nWelcome to the Pharmacy Management System");
            System.out.println("Please select an option:");
            System.out.println("1. Add Drug to Inventory");
            System.out.println("2. Search for a Drug in Inventory");
            System.out.println("3. View a List of All Drugs and Supplier Details");
            System.out.println("4. View Purchase History");
            System.out.println("5. Exit\n\nPlease Enter Your Input:");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    addDrugToInventory(scanner);
                    break;
                case 2:
                    searchDrugInInventory(scanner);
                    break;
                case 3:
                    viewAllDrugsAndSuppliers();
                    break;
                case 4:
                    viewPurchaseHistory(scanner);
                    break;
                case 5:
                    System.out.println("Exiting... Thank you!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addDrugToInventory(Scanner scanner) {
        System.out.println("\nEnter drug code:");
        String code = scanner.nextLine();
        System.out.println("\nEnter drug name:");
        String name = scanner.nextLine();
        System.out.println("\nEnter drug description:");
        String description = scanner.nextLine();
        System.out.println("\nEnter drug price:");
        double price = scanner.nextDouble();
        scanner.nextLine();  // Consume newline

        Drug newDrug = new Drug(code, name, description, price);
        addDrug(newDrug);
        System.out.println("\n\nDrug added successfully!");
    }

    private void searchDrugInInventory(Scanner scanner) {
        System.out.println("\nEnter drug code to search:");
        String code = scanner.nextLine();
        Drug drug = getDrug(code);
        if (drug != null) {
            System.out.println("Drug found: " + drug.getName() + ", " + drug.getDescription() + ", $" + drug.getPrice());
        } else {
            System.out.println("Drug not found.");
        }
    }

    private void viewAllDrugsAndSuppliers() {
        if (drugs.isEmpty()) {
            System.out.println("No drugs in inventory.");
            return;
        }

        for (Drug drug : drugs.values()) {
            System.out.println("Drug: " + drug.getName() + ", Code: " + drug.getCode() + ", Price: $" + drug.getPrice());
            System.out.println("Suppliers:");
            for (Supplier supplier : drug.getSuppliers()) {
                System.out.println(" - " + supplier.getName() + ", Location: " + supplier.getLocation());
            }
        }
    }

    private void viewPurchaseHistory(Scanner scanner) {
        System.out.println("\nEnter drug code to view purchase history:");
        String code = scanner.nextLine();
        Drug drug = getDrug(code);
        if (drug != null && !drug.getPurchaseHistory().isEmpty()) {
            System.out.println("Purchase History for " + drug.getName() + ":");
            for (Purchase purchase : drug.getPurchaseHistory()) {
                System.out.println(" - Customer ID: " + purchase.getCustomerId() + ", Date: " + purchase.getDate() + ", Quantity: " + purchase.getQuantity());
            }
        } else {
            System.out.println("No purchase history for this drug.");
        }
    }

    public static void main(String[] args) {
        PharmacyManagementSystem pms = new PharmacyManagementSystem();

        // Pre-populate some data (optional)
        Drug paracetamol = new Drug("D001", "Paracetamol", "Pain reliever", 1.5);
        pms.addDrug(paracetamol);

        Supplier supplier1 = new Supplier("S001", "Supplier One", "New York");
        Supplier supplier2 = new Supplier("S002", "Supplier Two", "California");
        pms.addSupplier(supplier1);
        pms.addSupplier(supplier2);

        paracetamol.addSupplier(supplier1);
        paracetamol.addSupplier(supplier2);

        Purchase purchase1 = new Purchase("C001", new Date(), 5);
        pms.addPurchase("D001", purchase1);

        // Run the command-line interface
        pms.displayMenu();
    }
}
