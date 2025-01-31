
package inventory_control;

import javafx.beans.property.*;

public class Product {
    private final IntegerProperty id;
    private final StringProperty productType;
    private final StringProperty productName;
    private final IntegerProperty quantity;
    private final DoubleProperty price;

    public Product(int id, String productType, String productName, int quantity, double price) {
        this.id = new SimpleIntegerProperty(id);
        this.productType = new SimpleStringProperty(productType);
        this.productName = new SimpleStringProperty(productName);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.price = new SimpleDoubleProperty(price);
    }

    public int getId() { return id.get(); }
    public String getProductType() { return productType.get(); }
    public String getProductName() { return productName.get(); }
    public int getQuantity() { return quantity.get(); }
    public double getPrice() { return price.get(); }

    public IntegerProperty idProperty() { return id; }
    public StringProperty productTypeProperty() { return productType; }
    public StringProperty productNameProperty() { return productName; }
    public IntegerProperty quantityProperty() { return quantity; }
    public DoubleProperty priceProperty() { return price; }
}

