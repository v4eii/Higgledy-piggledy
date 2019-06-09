package administration.view.controllers;

import administration.statements.IStatement;
import administration.statements.StatementCaf;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author v4e
 */
public class ClientViewCafController implements Initializable {

    private StatementCaf stmt;
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    
    
    public IStatement getStmt()
    {
        return stmt;
    }

    public void setStmt(StatementCaf stmt)
    {
        this.stmt = stmt;
    }
    
}
