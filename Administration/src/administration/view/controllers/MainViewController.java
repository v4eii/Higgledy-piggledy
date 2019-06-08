package administration.view.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;

/**
 *
 * @author v4e
 */
public class MainViewController implements Initializable {
    
    @FXML
    private MenuItem mUnchekedStatement, 
                    mApprovedStatement, 
                    mRejectStatement,
                    mReadStatement;
    @FXML
    private ListView<String> statementList;
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        statementList.getItems().add("Что-то");
    }    
    
}
