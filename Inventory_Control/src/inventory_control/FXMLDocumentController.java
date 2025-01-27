
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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
    private void productlistAction(ActionEvent event) {
    }

    @FXML
    private void customerlistAction(ActionEvent event) {
    }

    @FXML
    private void stocklistAction(ActionEvent event) {
    }


}
