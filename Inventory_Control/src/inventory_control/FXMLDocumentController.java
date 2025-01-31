package inventory_control;

import java.io.IOException;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField username;
    @FXML
    private TextField usignin;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField psignin;

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
    private TableView<customer> tvcustomer;
    @FXML
    private TableColumn<customer, String> CIname;
    @FXML
    private TableColumn<customer, String> CIadress;
    @FXML
    private TableColumn<customer, String> CIphone;
    @FXML
    private TableColumn<customer, String> CIemail;
    @FXML
    private Button btnsignin;
    @FXML
    private Button btnlogin;
    @FXML
    private Button btnclose;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connect = connectDb();

    }

    // Establish Database Connection
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
    private void loginAdmin(ActionEvent event) {
        String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";
        
        if (username.getText().isEmpty() || password.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill all blank fields");
            return;
        }

        try (PreparedStatement stmt = connect.prepareStatement(sql)) {
            stmt.setString(1, username.getText());
            stmt.setString(2, password.getText());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome, " + username.getText() + "!");
                    navigateTo(event, "dashboard.fxml");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Login Failed", "Incorrect username or password.");
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while processing your request.");
        }
    }
    @FXML
    private void signAdmin(ActionEvent event) {
        String sqlCheck = "SELECT * FROM admin WHERE username = ?";
        String sqlInsert = "INSERT INTO admin (username, password) VALUES (?, ?)";

        if (usignin.getText().isEmpty() || psignin.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill all fields");
            return;
        }

        try (PreparedStatement stmtCheck = connect.prepareStatement(sqlCheck);
             PreparedStatement stmtInsert = connect.prepareStatement(sqlInsert)) {
            
            stmtCheck.setString(1, usignin.getText());
            try (ResultSet rs = stmtCheck.executeQuery()) {
                if (rs.next()) {
                    showAlert(Alert.AlertType.ERROR, "Registration Failed", "Username already exists.");
                    return;
                }
            }

            stmtInsert.setString(1, usignin.getText());
            stmtInsert.setString(2, psignin.getText());

            if (stmtInsert.executeUpdate() > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "Admin registered successfully!");
                navigateTo(event, "dashboard.fxml");
            } else {
                showAlert(Alert.AlertType.ERROR, "Registration Failed", "Failed to register the user.");
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while processing your request.");
        }
    }
    @FXML
    private void closeApp(ActionEvent event) {
        System.exit(0);
    }
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void navigateTo(ActionEvent event, String fxmlFile) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
@FXML
    private void handleAction(ActionEvent event) {
       
 
    }
    @FXML  
    private void handleUpdate(ActionEvent event){
        
    }
    @FXML
     private void handleDelete(ActionEvent event){
         
     }
     @FXML
      private void BackAction(ActionEvent event){
          
      }

}
