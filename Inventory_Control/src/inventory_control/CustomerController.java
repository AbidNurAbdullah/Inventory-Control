/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package inventory_control;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class CustomerController implements Initializable {

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Stage stage;
    private Scene scene;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/inventory";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";

    @FXML
    private TextField Cname;
    @FXML
    private TextField CAdrdess;
    @FXML
    private TextField Cphone;
    @FXML
    private TextField Cemail;
    @FXML
    private Button Cadd;
    @FXML
    private Button Cupdate;
    @FXML
    private Button Cdelete;
    @FXML
    private Button Cback;
    @FXML
    private TableView<customer> tvcustomer;
    @FXML
    private TableColumn<customer, String> CIname;
    @FXML
    private TableColumn<customer, String> CIadress;
    @FXML
    private TableColumn<customer, String> CIphone;
    @FXML
    private TableColumn<customer, String> CIemail;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connect = connectDb();
        tvcustomer.setItems(getCustomers());

        CIname.setCellValueFactory(new PropertyValueFactory<>("name"));
        CIadress.setCellValueFactory(new PropertyValueFactory<>("address"));
        CIphone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        CIemail.setCellValueFactory(new PropertyValueFactory<>("email"));

        tvcustomer.refresh();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Connection connectDb() {
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Connection Error", "Failed to connect to the database.");
            return null;
        }
    }

    public ObservableList<customer> getCustomers() {
        ObservableList<customer> customerList = FXCollections.observableArrayList();
        String query = "SELECT * FROM customer";

        try (Statement st = connect.createStatement(); ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                customer cust = new customer(
                        rs.getString("Name"),
                        rs.getString("Address"),
                        rs.getString("Phone"),
                        rs.getString("Email")
                );
                customerList.add(cust);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerList;
    }

    @FXML
    private void handleAction(ActionEvent event) {
        String sqlInsert = "INSERT INTO customer (Name, Address, Phone, Email) VALUES (?, ?, ?, ?)";

        try (PreparedStatement in = connect.prepareStatement(sqlInsert)) {
            in.setString(1, Cname.getText());
            in.setString(2, CAdrdess.getText());
            in.setString(3, Cphone.getText());
            in.setString(4, Cemail.getText());

            in.executeUpdate();
            tvcustomer.setItems(getCustomers());
            tvcustomer.refresh();

            showAlert(Alert.AlertType.INFORMATION, "Success", "Customer added successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add customer.");
        }
    }

    @FXML
    private void handleUpdate(ActionEvent event) {
        customer cs = tvcustomer.getSelectionModel().getSelectedItem();
        if (cs == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "No customer selected.");
            return;
        }

        String sqlUpdate = "UPDATE customer SET Name = ?, Address = ?, Phone = ? WHERE Email = ?";

        try (PreparedStatement pstmt = connect.prepareStatement(sqlUpdate)) {
            pstmt.setString(1, Cname.getText());
            pstmt.setString(2, CAdrdess.getText());
            pstmt.setString(3, Cphone.getText());
            pstmt.setString(4, cs.getEmail());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Customer updated successfully.");
                tvcustomer.setItems(getCustomers());
                tvcustomer.refresh();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "No customer found with the specified email.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to update customer.");
        }
    }

    @FXML
    private void handleDelete(ActionEvent event) {
        customer cs = tvcustomer.getSelectionModel().getSelectedItem();
        if (cs == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "No customer selected.");
            return;
        }

        String sqlDelete = "DELETE FROM customer WHERE Email = ?";

        try (PreparedStatement pstmt = connect.prepareStatement(sqlDelete)) {
            pstmt.setString(1, cs.getEmail());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Customer deleted successfully.");
                tvcustomer.setItems(getCustomers());
                tvcustomer.refresh();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "No customer found with the specified email.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete customer.");
        }
    }

    private void BackAction(ActionEvent event) {
        // Implement navigation logic to go back to the previous scene or close the current window
        if (stage != null) {
            stage.close();
        }
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