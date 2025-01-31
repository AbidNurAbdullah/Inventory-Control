package inventory_control;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DashboardController implements Initializable {

    @FXML
    private Button btnproductlist;
    @FXML
    private Button btncustomerlist;
    @FXML
    private Button btnstocklist;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Dashboard initialized successfully.");
    }

    @FXML
    private void productlistAction(ActionEvent event) {
        System.out.println("Product List button clicked.");
        switchScene(event, "product.fxml");
    }

    @FXML
    private void customerlistAction(ActionEvent event) {
        System.out.println("Customer List button clicked.");
        switchScene(event, "Customer.fxml");

        
    }

    @FXML
    private void stocklistAction(ActionEvent event) {
        System.out.println("Stock List button clicked.");
        switchScene(event, "Order.fxml");
    }

    private void switchScene(ActionEvent event, String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} 