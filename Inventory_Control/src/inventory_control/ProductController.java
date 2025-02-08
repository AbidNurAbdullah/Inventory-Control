package inventory_control;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ProductController {

    @FXML
    private TableView<Product> tvProduct;
    @FXML
    private TableColumn<Product, String> TvPtype;
    @FXML
    private TableColumn<Product, String> TvPname;
    @FXML
    private TableColumn<Product, Integer> tvPqnt;
    @FXML
    private TableColumn<Product, Double> TvPprice;

    @FXML
    private ComboBox<String> PLcomboboxType;
    @FXML
    private ComboBox<String> PLComboboxName;
    @FXML
    private ComboBox<Integer> PLcomboboxQuantity;

    @FXML
    private TextField TFPLprice;

    private ObservableList<Product> productList;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/inventory";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";

    public void initialize() {
        productList = FXCollections.observableArrayList();
        tvProduct.setItems(productList);
        TvPtype.setCellValueFactory(new PropertyValueFactory<>("productType"));
        TvPname.setCellValueFactory(new PropertyValueFactory<>("productName"));
        tvPqnt.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        TvPprice.setCellValueFactory(new PropertyValueFactory<>("price"));

        ObservableList<String> productTypes = FXCollections.observableArrayList("Snacks", "Drinks", "Groceries", "Skin-Care", "Baby Item");
        PLcomboboxType.setItems(productTypes);
        PLcomboboxType.setOnAction(event -> updateProductNameComboBox());

        PLcomboboxQuantity.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5));

        loadProductsFromDatabase();

        tvProduct.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                PLcomboboxType.setValue(newSelection.getProductType());
                PLComboboxName.setValue(newSelection.getProductName());
                PLcomboboxQuantity.setValue(newSelection.getQuantity());
                TFPLprice.setText(String.valueOf(newSelection.getPrice()));
            }
        });
    }

    private void updateProductNameComboBox() {
        String selectedType = PLcomboboxType.getValue();
        ObservableList<String> productNames = FXCollections.observableArrayList();

        if (selectedType != null) {
            switch (selectedType) {
                case "Snacks":
                    productNames.addAll("Popcorn", "Chocolate", "Biscuits", "Nuts");
                    break;
                case "Drinks":
                    productNames.addAll("Mango Juice", "Orange Juice", "Coca Cola", "Sprite", "Water");
                    break;
                case "Groceries":
                    productNames.addAll("Rice", "Sugar", "Salt", "Oil", "Wheat");
                    break;
                case "Skin-Care":
                    productNames.addAll("Lotion", "Soap", "Cream", "Face Wash");
                    break;
                case "Baby Item":
                    productNames.addAll("Diapers", "Baby Food", "Baby Oil", "Baby Powder");
                    break;
                default:
                    break;
            }
        }

        PLComboboxName.setItems(productNames);
        if (!productNames.isEmpty()) {
            PLComboboxName.setValue(productNames.get(0));
        } else {
            PLComboboxName.setValue(null);
        }
    }


    private void loadProductsFromDatabase() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String query = "SELECT Id, Product_Type, Product_Name, Quantity, Price FROM productlist";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("Id");
                String productType = rs.getString("Product_Type");
                String productName = rs.getString("Product_Name");
                int quantity = rs.getInt("Quantity");
                double price = rs.getDouble("Price");

                Product product = new Product(id, productType, productName, quantity, price);
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddAction() {
        String productType = PLcomboboxType.getValue();
        String productName = PLComboboxName.getValue();
        Integer quantity = PLcomboboxQuantity.getValue(); // Get Integer value
        String priceText = TFPLprice.getText();

        if (productType != null && productName != null && quantity != null && !priceText.isEmpty()) { // Check for null and empty
          try {
            double price = Double.parseDouble(priceText);
            Product newProduct = new Product(productList.size() + 1, productType, productName, quantity, price);
            productList.add(newProduct);
            saveProductToDatabase(newProduct);

            // Clear input fields after adding
            PLcomboboxType.setValue(null);
            PLComboboxName.setValue(null);
            PLcomboboxQuantity.setValue(null);
            TFPLprice.clear();

          } catch (NumberFormatException ex) {
            // Handle parsing errors (e.g., show an alert)
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid price. Please enter a number.");
            alert.showAndWait();
          }

        } else {
          Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill in all fields.");
          alert.showAndWait();
        }
    }



    private void saveProductToDatabase(Product product) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String query = "INSERT INTO productlist (Product_Type, Product_Name, Quantity, Price) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, product.getProductType());
            stmt.setString(2, product.getProductName());
            stmt.setInt(3, product.getQuantity());
            stmt.setDouble(4, product.getPrice());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUpdateAction() {
        Product selectedProduct = tvProduct.getSelectionModel().getSelectedItem();

        if (selectedProduct != null) {
            try {
                selectedProduct.setProductType(PLcomboboxType.getValue());
                selectedProduct.setProductName(PLComboboxName.getValue());
                selectedProduct.setQuantity(PLcomboboxQuantity.getValue());
                double price = Double.parseDouble(TFPLprice.getText()); // Parse price
                selectedProduct.setPrice(price);

                updateProductInDatabase(selectedProduct);
                tvProduct.refresh();
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid price. Please enter a number.");
                alert.showAndWait();
            }
        }
    }

    private void updateProductInDatabase(Product product) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String query = "UPDATE productlist SET Product_Type = ?, Product_Name = ?, Quantity = ?, Price = ? WHERE Id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, product.getProductType());
            stmt.setString(2, product.getProductName());
            stmt.setInt(3, product.getQuantity());
            stmt.setDouble(4, product.getPrice());
            stmt.setInt(5, product.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteAction() {
        Product selectedProduct = tvProduct.getSelectionModel().getSelectedItem();

        if (selectedProduct != null) {
            productList.remove(selectedProduct);
            deleteProductFromDatabase(selectedProduct);
        }
    }

    private void deleteProductFromDatabase(Product product) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String query = "DELETE FROM productlist WHERE Id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, product.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBackAction(ActionEvent event) {
        switchScene(event, "dashboard.fxml");
    }

    @FXML
    private void handleTypeSelection() {
        // Handle the event when a product type is selected
    }

    @FXML
    private void handleNameSelection() {
        // Handle the event when a product name is selected
    }

    @FXML
    private void handleQuantitySelection() {
        // Handle the event when a quantity is selected
    }

    private void switchScene(ActionEvent event, String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
