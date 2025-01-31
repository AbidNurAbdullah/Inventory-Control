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

        // Initialize the combo boxes with mock data
        PLcomboboxType.setItems(FXCollections.observableArrayList("Electronics", "Groceries", "Clothing"));
        PLComboboxName.setItems(FXCollections.observableArrayList("Product 1", "Product 2", "Product 3"));
        PLcomboboxQuantity.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5));

        loadProductsFromDatabase();

        // Add a selection listener to the TableView
        tvProduct.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Populate the ComboBoxes and TextField with the selected item's data
                PLcomboboxType.setValue(newSelection.getProductType());
                PLComboboxName.setValue(newSelection.getProductName());
                PLcomboboxQuantity.setValue(newSelection.getQuantity());
                TFPLprice.setText(String.valueOf(newSelection.getPrice()));
            }
        });
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
        int quantity = PLcomboboxQuantity.getValue();
        double price = Double.parseDouble(TFPLprice.getText());

        if (productType != null && productName != null) {
            Product newProduct = new Product(productList.size() + 1, productType, productName, quantity, price);
            productList.add(newProduct);
            saveProductToDatabase(newProduct);
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
            selectedProduct.setProductType(PLcomboboxType.getValue());
            selectedProduct.setProductName(PLComboboxName.getValue());
            selectedProduct.setQuantity(PLcomboboxQuantity.getValue());
            selectedProduct.setPrice(Double.parseDouble(TFPLprice.getText()));

            updateProductInDatabase(selectedProduct);
            tvProduct.refresh(); // Refresh table view to reflect changes
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
