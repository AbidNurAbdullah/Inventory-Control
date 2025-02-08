/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package inventory_control;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import java.sql.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class OrderController {

    @FXML
    private TableView<Order> tvOrder;
    @FXML
    private TableColumn<Order, String> tv_type;
    @FXML
    private TableColumn<Order, String> tv_productName;
    @FXML
    private TableColumn<Order, Integer> tv_quantity;
    @FXML
    private TableColumn<Order, Double> tv_price;
    @FXML
    private ComboBox<String> productType;
    @FXML
    private ComboBox<String> productName;
    @FXML
    private TextField price;
    @FXML
    private TextField quantity1;
    @FXML
    private Button add;
    @FXML
    private Button delete;

    private ObservableList<Order> orderList = FXCollections.observableArrayList();
    private Connection connection;

    public OrderController() {
        // Initialize database connection
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Initialize the TableView and ComboBoxes
    public void initialize() {
        // Initialize columns in TableView
        tv_type.setCellValueFactory(cellData -> cellData.getValue().productTypeProperty());
        tv_productName.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        tv_quantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        tv_price.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty().asObject());

        // Set the data for TableView
        tvOrder.setItems(orderList);

        // Load orders from database
        loadOrdersFromDatabase();

        // Add example data to ComboBoxes
        addExampleDataToComboBoxes();

        // Set action on "Add" button
        add.setOnAction(event -> addOrder());

        // Set action on "Delete" button
        delete.setOnAction(event -> deleteOrder());
    }

    private void loadOrdersFromDatabase() {
        String sql = "SELECT * FROM orders";

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            // Clear the existing order list
            orderList.clear();

            // Populate the order list with data from the database
            while (resultSet.next()) {
                String productType = resultSet.getString("product_type");
                String productName = resultSet.getString("product_name");
                int quantity = resultSet.getInt("quantity");
                double price = resultSet.getDouble("price");
                double totalPrice = resultSet.getDouble("total_price");

                // Create a new Order object and add it to the order list
                Order order = new Order(productType, productName, quantity, price, totalPrice);
                orderList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addExampleDataToComboBoxes() {
        // Example product types
        ObservableList<String> productTypes = FXCollections.observableArrayList(
                "Snacks", "Drinks", "Groceries", "Skin-Care", "Baby Item"
        );

        // Populate productType ComboBox
        productType.setItems(productTypes);

        // Listen for changes in the product type selection
        productType.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateProductNamesBasedOnType(newValue);
        });
    }

    private void updateProductNamesBasedOnType(String type) {
        ObservableList<String> productNames = FXCollections.observableArrayList();

        // Clear the productName ComboBox when the product type changes
        productName.setItems(FXCollections.observableArrayList());

        if (type != null) {
            // Based on the selected type, populate the productName ComboBox
            switch (type) {
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

        // Set the updated product names in the productName ComboBox
        productName.setItems(productNames);
    }

    private void addOrder() {
        String selectedType = productType.getValue();
        String selectedProductName = productName.getValue();
        String quantityText = quantity1.getText();
        String priceText = price.getText();

        // Validate input
        if (selectedType == null || selectedProductName == null || quantityText.isEmpty() || priceText.isEmpty()) {
            showAlert("Please fill in all fields.");
            return;
        }

        try {
            double priceValue = Double.parseDouble(priceText);
            int quantityValue = Integer.parseInt(quantityText);

            // Calculate total price (price * quantity)
            double totalPrice = priceValue * quantityValue;

            // Create a new Order object
            Order newOrder = new Order(selectedType, selectedProductName, quantityValue, priceValue, totalPrice);

            // Add the new order to the TableView
            orderList.add(newOrder);

            // Save the order to the database
            saveOrderToDatabase(newOrder);

            // Clear the input fields
            clearInputs();

        } catch (NumberFormatException e) {
            showAlert("Please enter valid numbers for price and quantity.");
        }
    }

    private void saveOrderToDatabase(Order order) {
        String sql = "INSERT INTO orders (product_type, product_name, quantity, price, total_price) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, order.getProductType());
            statement.setString(2, order.getProductName());
            statement.setInt(3, order.getQuantity());
            statement.setDouble(4, order.getPrice());
            statement.setDouble(5, order.getTotalPrice());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteOrder() {
        // Get selected order from TableView
        Order selectedOrder = tvOrder.getSelectionModel().getSelectedItem();

        if (selectedOrder != null) {
            // Remove the selected order from TableView
            orderList.remove(selectedOrder);

            // Delete the order from the database
            deleteOrderFromDatabase(selectedOrder);
        } else {
            showAlert("Please select an order to delete.");
        }
    }

    private void deleteOrderFromDatabase(Order order) {
        String sql = "DELETE FROM orders WHERE product_name = ? AND product_type = ? AND quantity = ? AND price = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, order.getProductName());
            statement.setString(2, order.getProductType());
            statement.setInt(3, order.getQuantity());
            statement.setDouble(4, order.getPrice());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearInputs() {
        productType.setValue(null);
        productName.setValue(null);
        quantity1.clear();
        price.clear();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void deleteAction(ActionEvent event) {
    }

    @FXML
    private void handleBackAction(ActionEvent event) {
        switchScene(event, "dashboard.fxml");
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

