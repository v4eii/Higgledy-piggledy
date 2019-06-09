package administration.view.controllers;

import administration.beans.DBBean;
import administration.statements.IStatement;
import administration.statements.StatementTr;
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
    private TextField fieldFIO, fieldOrgName, fieldOrgAdress, fieldPhoneNumber, 
            fieldOGRN, fieldOGRNDate, fieldINN, fieldINNDate, fieldStatAdress,
            fieldSpec, fieldTimeBegin, fieldTimeEnd, fieldDateCreate;
    
    private StatementTr stmt;
            
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        fieldFIO.setText(stmt.getStatement().getFio());
        fieldOrgName.setText(DBBean.getInstance().getStatementJpaController().findStatement(stmt.getStatement().getIdUnion()).getOrg());
        fieldPhoneNumber.setText(DBBean.getInstance().getStatementJpaController().findStatement(stmt.getStatement().getIdUnion()).getContact());
        fieldOrgAdress.setText(stmt.getStatement().getIdAdr().getIdCity().getIdReg().getRegion() + " " + 
                stmt.getStatement().getIdAdr().getIdCity().getCity() + " " + 
                stmt.getStatement().getIdAdr().getStreet() + " " + 
                DBBean.getInstance().getStatementJpaController().findStatement(stmt.getStatement().getIdUnion()).getHouse() + " " +
                DBBean.getInstance().getStatementJpaController().findStatement(stmt.getStatement().getIdUnion()).getApartment());
        fieldOGRN.setText(DBBean.getInstance().getStatementJpaController().findStatement(stmt.getStatement().getIdUnion()).getOgrn());
        fieldOGRNDate.setText(DBBean.getInstance().getStatementJpaController().findStatement(stmt.getStatement().getIdUnion()).getDateOgrn().toString());
        fieldINN.setText(DBBean.getInstance().getStatementJpaController().findStatement(stmt.getStatement().getIdUnion()).getInn());
    }    

    public IStatement getStmt()
    {
        return stmt;
    }

    public void setStmt(StatementTr stmt)
    {
        this.stmt = stmt;
    }
    
    
}
