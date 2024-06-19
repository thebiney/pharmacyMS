public class Supplier {
    private String id;
    private String name;
    private String location;

    public Supplier(String id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    // Getters and setters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getLocation() { return location; }
}
