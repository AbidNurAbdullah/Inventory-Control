/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package inventory_control;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author User
 */
public class ProductController implements Initializable {

    @FXML
    private Button PBadd;
    @FXML
    private TextField Pname;
    @FXML
    private TextField Pqnt;
    @FXML
    private TextField Price;
    @FXML
    private Button PBupdate;
    @FXML
    private Button PBdelete;
    @FXML
    private Button PBback;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleActionButton(ActionEvent event) {
    }
    
}
