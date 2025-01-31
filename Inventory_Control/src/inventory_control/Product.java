package inventory_control;

public class Product {
    private int id;
    private String productType;
    private String productName;
    private int quantity;
    private double price;

    // Constructor
    public Product(int id, String productType, String productName, int quantity, double price) {
        this.id = id;
        this.productType = productType;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getProductType() {
        return productType;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    // Setters
    public void setProductType(String productType) {
        this.productType = productType;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Optional: Override toString() for better representation
    @Override
    public String toString() {
        return "Product [id=" + id + ", productType=" + productType + ", productName=" + productName + ", quantity=" + quantity + ", price=" + price + "]";
    }
}
