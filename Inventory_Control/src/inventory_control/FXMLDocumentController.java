
package inventory_control;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable {
    @FXML
    private TextField username;
     @FXML
    private PasswordField password;
      @FXML
    private TextField usignin;
       @FXML
    private TextField psignin;
    
    
    private Connection connect;
    
    private PreparedStatement prepare;
    
    private ResultSet result;
    
    private Stage stage;
    private Scene scene;
    @FXML
    private TextField Cadd;
    @FXML
    private Button Cupdate;
    @FXML
    private Button Cdelete;
    @FXML
    private Button Cback;
      @FXML       
    private TableView<Product> tvProduct;
       @FXML
    private TextField TFPLprice;
        @FXML
    private ComboBox<String> PLcomboboxType;
         @FXML
    private ComboBox<String> PLComboboxName;
          @FXML
    private ComboBox<Integer> PLcomboboxQuantity;
    
    private Connection conn;
    private PreparedStatement pst;
    @FXML
    private TextField Cname;
    @FXML
    private TextField Cphone;
    @FXML
    private TextField Cemail;
    @FXML
    private TableView<customer> tvcustomer;
    @FXML
    private TableColumn<customer, String> CIname;
    @FXML
    private TableColumn<customer, String> CIadress;
    @FXML
    private TableColumn<customer, Integer> CIphone;
    @FXML
    private TableColumn<customer, String> CIemail;


  
  
   
    
     @FXML
    private void loginAdmin(ActionEvent event) throws IOException {
        String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";
        connect = loginInfo.connectDb();

        try {
            if (username.getText().isEmpty() || password.getText().isEmpty()) {
                showAlert(AlertType.ERROR, "Error Message", "Please fill all blank fields");
                return;
            }

            prepare = connect.prepareStatement(sql);
            prepare.setString(1, username.getText());
            prepare.setString(2, password.getText());

            result = prepare.executeQuery();

            if (result.next()) {
                showAlert(AlertType.INFORMATION, "Login Successful", "Welcome, " + username.getText() + "!");
                navigateTo(event, "dashboard.fxml");
            } else {
                showAlert(AlertType.ERROR, "Login Failed", "Incorrect username or password.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Database Error", "An error occurred while connecting to the database.");
        } finally {
            closeResources();
        }
    }
     @FXML
    private void signAdmin(ActionEvent event) throws IOException {
        String sqlCheck = "SELECT * FROM admin WHERE username = ?";
        String sqlInsert = "INSERT INTO admin (username, password) VALUES (?, ?)";

        connect = loginInfo.connectDb();

        try {
            if (usignin.getText().isEmpty() || psignin.getText().isEmpty()) {
                showAlert(AlertType.ERROR, "Error Message", "Please fill all fields");
                return;
            }

            prepare = connect.prepareStatement(sqlCheck);
            prepare.setString(1, usignin.getText());
            result = prepare.executeQuery();

            if (result.next()) {
                showAlert(AlertType.ERROR, "Registration Failed", "Username already exists.");
            } else {
                prepare = connect.prepareStatement(sqlInsert);
                prepare.setString(1, usignin.getText());
                prepare.setString(2, psignin.getText());

                if (prepare.executeUpdate() > 0) {
                    showAlert(AlertType.INFORMATION, "Registration Successful", "Admin registered successfully!");
                    navigateTo(event, "dashboard.fxml");
                } else {
                    showAlert(AlertType.ERROR, "Registration Failed", "Failed to register the user.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Database Error", "An error occurred while connecting to the database.");
        } finally {
            closeResources();
        }
    }
     @FXML
    private void productlistAction(ActionEvent event) throws IOException {
        navigateTo(event, "product.fxml");
    }
     @FXML
    private void customerlistAction(ActionEvent event) throws IOException {
        
        navigateTo(event, "Customer.fxml");
    }
     @FXML
    private void stocklistAction(ActionEvent event) throws IOException {
        navigateTo(event, "stock.fxml");
    }
     @FXML
    private void close(ActionEvent event) {
        System.exit(0);
    }

    // Utility methods
    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void navigateTo(ActionEvent event, String fxmlFile) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    private void closeResources() {
        try {
            if (result != null) result.close();
            if (prepare != null) prepare.close();
            if (connect != null) connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleAction(ActionEvent event) {
        Object source = event.getSource();

    if (source == Cadd) {
        // Logic for "Add" button
        System.out.println("Add button clicked");
    } else if (source == Cupdate) {
        // Logic for "Update" button
        System.out.println("Update button clicked");
    } else if (source == Cdelete) {
        // Logic for "Delete" button
        System.out.println("Delete button clicked");
    } else if (source == Cback) {
        // Logic for "Back" button
        System.out.println("Back button clicked");
    }
    }
@FXML
    void ProductAction(ActionEvent event) {

    }

    @FXML
    private void BackAction(ActionEvent event) throws IOException {
        navigateTo(event, "dashboard.fxml");
    }
    
    
    public void connect() {
    try {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory", "root", "");
    } catch (Exception e) {
        e.printStackTrace();
        showAlert("Database Error", "Connection failed.");
    }
}

public void loadTable() {
    if (conn == null) {
        System.out.println("Connection is null.");
        return;
    }

    ObservableList<Product> productlist = FXCollections.observableArrayList();
    Statement stmt = null;
    ResultSet rs = null;
    try {
        stmt = conn.createStatement();
        rs = stmt.executeQuery("SELECT * FROM productlist");
        while (rs.next()) {
            System.out.println("ID: " + rs.getInt("id") + ", Type: " + rs.getString("productType"));
            productlist.add(new Product(rs.getInt("id"), rs.getString("productType"), rs.getString("productName"),
                    rs.getInt("quantity"), rs.getDouble("price")));
        }
        tvProduct.setItems(productlist);
    } catch (SQLException e) {
        e.printStackTrace();
        showAlert("Database Error", "Error loading data from the database.");
    } finally {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

// Add Product
public void addProduct(ActionEvent event) {
    String type = PLcomboboxType.getValue();
    String name = PLComboboxName.getValue();
    Integer quantity = PLcomboboxQuantity.getValue();
    String priceText = TFPLprice.getText();

    if (type == null || name == null || quantity == null || priceText.isEmpty()) {
        showAlert("Input Error", "All fields must be filled out.");
        return;
    }

    double price;
    try {
        price = Double.parseDouble(priceText);
    } catch (NumberFormatException e) {
        showAlert("Input Error", "Invalid price input.");
        return;
    }

    PreparedStatement pst = null;
    try {
        String query = "INSERT INTO productlist (productType, productName, quantity, price) VALUES (?, ?, ?, ?)";
        pst = conn.prepareStatement(query);
        pst.setString(1, type);
        pst.setString(2, name);
        pst.setInt(3, quantity);
        pst.setDouble(4, price);
        pst.executeUpdate();
        loadTable();
    } catch (SQLException e) {
        e.printStackTrace();
        showAlert("Database Error", "Error adding product.");
    } finally {
        try {
            if (pst != null) pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

// Update Product
public void updateProduct(ActionEvent event) {
    Product selected = tvProduct.getSelectionModel().getSelectedItem();
    if (selected == null) {
        showAlert("No product selected", "Please select a product to update.");
        return;
    }

    String type = PLcomboboxType.getValue();
    String name = PLComboboxName.getValue();
    Integer quantity = PLcomboboxQuantity.getValue();
    String priceText = TFPLprice.getText();

    if (type == null || name == null || quantity == null || priceText.isEmpty()) {
        showAlert("Input Error", "All fields must be filled out.");
        return;
    }

    double price;
    try {
        price = Double.parseDouble(priceText);
    } catch (NumberFormatException e) {
        showAlert("Input Error", "Invalid price input.");
        return;
    }

    PreparedStatement pst = null;
    try {
        String query = "UPDATE productlist SET productType=?, productName=?, quantity=?, price=? WHERE id=?";
        pst = conn.prepareStatement(query);
        pst.setString(1, type);
        pst.setString(2, name);
        pst.setInt(3, quantity);
        pst.setDouble(4, price);
        pst.setInt(5, selected.getId());
        pst.executeUpdate();
        loadTable();
    } catch (SQLException e) {
        e.printStackTrace();
        showAlert("Database Error", "Error updating product.");
    } finally {
        try {
            if (pst != null) pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

// Delete Product
public void deleteProduct(ActionEvent event) {
    Product selected = tvProduct.getSelectionModel().getSelectedItem();
    if (selected == null) {
        showAlert("No product selected", "Please select a product to delete.");
        return;
    }

    PreparedStatement pst = null;
    try {
        String query = "DELETE FROM productlist WHERE id=?";
        pst = conn.prepareStatement(query);
        pst.setInt(1, selected.getId());
        pst.executeUpdate();
        loadTable();
    } catch (SQLException e) {
        e.printStackTrace();
        showAlert("Database Error", "Error deleting product.");
    } finally {
        try {
            if (pst != null) pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

// Alert Box
private void showAlert(String title, String content) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setContentText(content);
    alert.showAndWait();
}


 
        /* ObservableList<Integer> quantities = FXCollections.observableArrayList(
            1, 2, 3, 4,5);
         PLcomboboxQuantity.setItems(quantities);
    // Add product types to the first ComboBox
    PLcomboboxType.getItems().addAll("Snacks", "Drinks", "SkinCare Product", "Desert", "Baby Item");
}*/
@FXML
private void PTCombo(ActionEvent event) {
    /*String selectedType = PLcomboboxType.getValue();
    PLComboboxName.getItems().clear(); // Clear previous items

    if (selectedType == null) return;

    if (selectedType.equals("Snacks")) {
        PLComboboxName.getItems().addAll("Chips", "Biscuit", "Popcorn");
    } else if (selectedType.equals("Drinks")) {
        PLComboboxName.getItems().addAll("Coca-cola", "Mojo", "Pepsi");
    } else if (selectedType.equals("SkinCare Product")) {
        PLComboboxName.getItems().addAll("FaceWash", "Moisturizers", "SunScreen");
    } else if (selectedType.equals("Desert")) {
        PLComboboxName.getItems().addAll("Donut", "Ice Cream", "Pastry");
    } else if (selectedType.equals("Baby Item")) {
        PLComboboxName.getItems().addAll("Toys", "Baby Lotion", "Diaper");
    }*/
}
private final String[] listType = {"Snacks", "Drinks", "SkinCare Product", "Dessert", "Baby Item"};
 

    @FXML
    void PNcombo(ActionEvent event) {

    }

    @FXML
    void PQcombo(ActionEvent event) {

    }
   /* @Override
    public void initialize(URL url, ResourceBundle rb) {
   ObservableList<String> typeList = FXCollections.observableArrayList(listType);
        PLcomboboxType.setItems(typeList);
        
        // Initialize quantity ComboBox
        ObservableList<Integer> quantities = FXCollections.observableArrayList(1, 2, 3, 4, 5);
        PLcomboboxQuantity.setItems(quantities);
        
      
    
    }*/
   public void initialize() {
       ObservableList<String> typeList = FXCollections.observableArrayList(listType);
        PLcomboboxType.setItems(typeList);
        
        // Initialize quantity ComboBox
        ObservableList<Integer> quantities = FXCollections.observableArrayList(1, 2, 3, 4, 5);
        PLcomboboxQuantity.setItems(quantities);
   }

}

    


