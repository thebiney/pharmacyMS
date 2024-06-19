import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class Drug {
    private String code;
    private String name;
    private String description;
    private double price;
    private LinkedList<Purchase> purchaseHistory;
    private ArrayList<Supplier> suppliers;

    public Drug(String code, String name, String description, double price) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.price = price;
        this.purchaseHistory = new LinkedList<>();
        this.suppliers = new ArrayList<>();
    }

    // Getters and setters
    public String getCode() { return code; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public LinkedList<Purchase> getPurchaseHistory() { return purchaseHistory; }
    public ArrayList<Supplier> getSuppliers() { return suppliers; }

    // Add a purchase to the history
    public void addPurchase(Purchase purchase) {
        purchaseHistory.add(purchase);
    }

    // Add a supplier to the drug
    public void addSupplier(Supplier supplier) {
        suppliers.add(supplier);
    }

    // Sort suppliers by name
    public void sortSuppliersByName() {
        Collections.sort(suppliers, Comparator.comparing(Supplier::getName));
    }

    // Sort suppliers by location
    public void sortSuppliersByLocation() {
        Collections.sort(suppliers, Comparator.comparing(Supplier::getLocation));
    }
}
