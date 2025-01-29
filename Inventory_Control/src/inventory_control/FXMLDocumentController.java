/*
package inventory_control;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Connection connect;
    @FXML
    private PreparedStatement prepare;
    @FXML
    private ResultSet result;
    @FXML        
    private TextField usignin;
    @FXML
    private TextField psignin;
    
    Stage stage;
    Scene scene;
    @FXML
    private Button btnproductlist;
    @FXML
    private Button btncustomerlist;
    @FXML
    private Button btnstocklist;
    @FXML
    public void loginAdmin(ActionEvent event) throws IOException {
        String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";
        connect = loginInfo.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, username.getText());
            prepare.setString(2, password.getText());

            result = prepare.executeQuery();

            Alert alert;
            
            if (username.getText().isEmpty() || password.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                
                if (result.next()) {
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Login Successful");
                    alert.setHeaderText(null);
                    alert.setContentText("Welcome, " + username.getText() + "!");
                    alert.showAndWait();
                   
                } else {
                    
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Login Failed");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect username or password.");
                    alert.showAndWait();
                }
            }
        } catch (SQLException e) {
            
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while connecting to the database.");
            alert.showAndWait();
        } finally {
            
            try {
                if (result != null) result.close();
                if (prepare != null) prepare.close();
                if (connect != null) connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
         Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
         stage = (Stage)((Node)event.getSource()).getScene().getWindow();
         scene = new Scene(root);
         stage.setScene(scene);
         stage.show();
    }
  @FXML
    private void close(ActionEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
     @FXML
    private void signAdmin(ActionEvent event) throws IOException {
    String sqlCheck = "SELECT * FROM admin WHERE username = ?";
    String sqlInsert = "INSERT INTO admin (username, password) VALUES (?, ?)";

    connect = loginInfo.connectDb();

    try {
        
        prepare = connect.prepareStatement(sqlCheck);
        prepare.setString(1, usignin.getText());
        result = prepare.executeQuery();

        Alert alert;
        if (usignin.getText().isEmpty() || psignin.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all fields");
            alert.showAndWait();
        } else if (result.next()) {
            
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Registration Failed");
            alert.setHeaderText(null);
            alert.setContentText("Username already exists.");
            alert.showAndWait();
        } else {
            
            prepare = connect.prepareStatement(sqlInsert);
            prepare.setString(1, usignin.getText());
            prepare.setString(2, psignin.getText());
            int rowsAffected = prepare.executeUpdate();

            if (rowsAffected > 0) {
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Registration Successful");
                alert.setHeaderText(null);
                alert.setContentText("Admin registered successfully!");
                alert.showAndWait();
                
          
            } else {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Registration Failed");
                alert.setHeaderText(null);
                alert.setContentText("Failed to register the user.");
                alert.showAndWait();
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Database Error");
        alert.setHeaderText(null);
        alert.setContentText("An error occurred while connecting to the database.");
        alert.showAndWait();
    } finally {
        try {
            if (result != null) result.close();
            if (prepare != null) prepare.close();
            if (connect != null) connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
     Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
         stage = (Stage)((Node)event.getSource()).getScene().getWindow();
         scene = new Scene(root);
         stage.setScene(scene);
         stage.show();

}
     @FXML
    private void productlistAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("product.fxml"));
         stage = (Stage)((Node)event.getSource()).getScene().getWindow();
         scene = new Scene(root);
         stage.setScene(scene);
         stage.show();
    }
     @FXML
    private void customerlistAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Customer.fxml"));
         stage = (Stage)((Node)event.getSource()).getScene().getWindow();
         scene = new Scene(root);
         stage.setScene(scene);
         stage.show();
    }

    @FXML
    private void stocklistAction(ActionEvent event) {
    }




}*/
package inventory_control;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
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
    private TableColumn<?, ?> TPnme;
    @FXML
    private TableColumn<?, ?> Tqn;
    @FXML
    private TableColumn<?, ?> Tprice;
    @FXML
    private Button PBadd;
    @FXML
    private TextField Pname;
    @FXML
    private TextField Pqnt;
    @FXML
    private TextField pprice;
    @FXML
    private Button PBupdate;
    @FXML
    private Button PBdelete;
    @FXML
    private Button PBback;
    @FXML
    private ComboBox<?> btnproducttype;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Any necessary initialization logic
    }
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
}
