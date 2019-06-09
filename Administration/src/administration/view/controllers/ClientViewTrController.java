package administration.view.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author v4e
 */
public class ClientViewTrController implements Initializable {

    @FXML
    private TextField fieldFIO, fieldOrgName, fieldOrgAdress, fieldOrgAdress1, 
            fieldOGRN, fieldOGRNDate, fieldINN, fieldINNDate, fieldStatAdress,
            fieldSpec, fieldTimeBegin, fieldTimeEnd, fieldDateCreate;
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        
    }    
    
}
