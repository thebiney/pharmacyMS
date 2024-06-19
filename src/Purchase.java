import java.util.Date;

public class Purchase {
    private String customerId;
    private Date date;
    private int quantity;

    public Purchase(String customerId, Date date, int quantity) {
        this.customerId = customerId;
        this.date = date;
        this.quantity = quantity;
    }

    // Getters and setters
    public String getCustomerId() { return customerId; }
    public Date getDate() { return date; }
    public int getQuantity() { return quantity; }
}
