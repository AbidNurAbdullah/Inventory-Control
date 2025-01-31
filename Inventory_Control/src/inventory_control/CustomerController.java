/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package inventory_control;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
        // TODO
                connect = connectDb();
        tvcustomer.setItems(getCustomers());
        
              CIname.setCellValueFactory(new PropertyValueFactory<customer,String>("name"));
        CIadress.setCellValueFactory(new PropertyValueFactory<customer,String>("address"));
        CIphone.setCellValueFactory(new PropertyValueFactory<customer,String>("phone"));
        CIemail.setCellValueFactory(new PropertyValueFactory<customer,String>("email"));
        
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
        ObservableList<customer> studentList = FXCollections.observableArrayList();
        Connection conn = connectDb();
        String query = "SELECT * FROM customer";
        
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            customer students = null;
            while (rs.next()) {
                students = new customer(rs.getString("Name"), rs.getString("Address"), rs.getString("Phone"), rs.getString("Email"));
                studentList.add(students);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return studentList;
    }
    @FXML
    private void handleAction(ActionEvent event) {
               String sqlInsert = "INSERT INTO customer (Name, Address, Phone, Email) VALUES (?, ?,?,?)";

        try{
           PreparedStatement in = connect.prepareStatement(sqlInsert);
           in.setString(1,Cname.getText() );
           in.setString(2,CAdrdess.getText() );
           in.setString(3,Cphone.getText() );
           in.setString(4,Cemail.getText() );
           
           in.executeUpdate();
           tvcustomer.setItems(getCustomers());
           tvcustomer.refresh();
           
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUpdate(ActionEvent event) {
        customer cs = tvcustomer.getSelectionModel().getSelectedItem();
        String old = cs.getAddress();
        cs.setAddress(CAdrdess.getText());
        cs.setEmail(Cemail.getText());
        cs.setName(Cname.getText());
        cs.setPhone(Cphone.getText());
        System.out.println(cs);
           String sqlUpdate = "UPDATE customer SET Name = ?, Address = ?, Phone = ?, Email = ? WHERE Email = ?";

    try (PreparedStatement pstmt = connect.prepareStatement(sqlUpdate)) {
        // Set the updated values using the customer's data
        pstmt.setString(1, cs.getName());
        pstmt.setString(2, cs.getAddress());
        pstmt.setString(3, cs.getPhone());
        pstmt.setString(4, cs.getEmail());
        pstmt.setString(5, old);  // Assuming you have a method to get CustomerID
        pstmt.executeUpdate();
        tvcustomer.setItems(getCustomers());
        tvcustomer.refresh();

        // Execute the update query
        int rowsAffected = pstmt.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Customer updated successfully.");
        } else {
            System.out.println("No customer found with the specified ID.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void handleDelete(ActionEvent event) {
    }

    @FXML
    private void BackAction(ActionEvent event) {
    }
    
}
