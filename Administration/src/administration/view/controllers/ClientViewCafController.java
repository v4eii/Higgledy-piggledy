package administration.view.controllers;

import administration.beans.DBBean;
import administration.statements.IStatement;
import administration.statements.StatementCaf;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author v4e
 */
public class ClientViewCafController implements Initializable {

    @FXML
    private TextField fieldFIO, fieldOrgName, fieldOrgAdress, fieldPhoneNumber,
            fieldOGRN, fieldINN, fieldStatAdress, fieldSpec, fieldKadastrNum,
            fieldOkato, fieldSquare;
    
    @FXML
    private DatePicker dFieldOGRNDate, dFieldINNDate, dFieldTimeBegin,
            dFieldTimeEnd, dFieldDateCreate;

    private StatementCaf stmt;
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
    }    
    
    public void initData() {
        fieldFIO.setText(stmt.getStatement().getFio());
        fieldOrgName.setText(DBBean.getInstance().getStatementJpaController().findStatement(stmt.getStatement().getIdUnion()).getOrg());
        fieldPhoneNumber.setText(DBBean.getInstance().getStatementJpaController().findStatement(stmt.getStatement().getIdUnion()).getContact());
        fieldOrgAdress.setText(stmt.getStatement().getIdAdr().getIdCity().getIdReg().getRegion() + " "
                + stmt.getStatement().getIdAdr().getIdCity().getCity() + " "
                + stmt.getStatement().getIdAdr().getStreet() + " "
                + DBBean.getInstance().getStatementJpaController().findStatement(stmt.getStatement().getIdUnion()).getHouse() + " "
                + DBBean.getInstance().getStatementJpaController().findStatement(stmt.getStatement().getIdUnion()).getApartment());
        fieldOGRN.setText(DBBean.getInstance().getStatementJpaController().findStatement(stmt.getStatement().getIdUnion()).getOgrn());
        dFieldOGRNDate.setValue(new java.sql.Date(DBBean.getInstance().getStatementJpaController().findStatement(stmt.getStatement().getIdUnion()).getDateOgrn().getTime()).toLocalDate());        
        fieldINN.setText(DBBean.getInstance().getStatementJpaController().findStatement(stmt.getStatement().getIdUnion()).getInn());
        dFieldINNDate.setValue(new java.sql.Date(DBBean.getInstance().getStatementJpaController().findStatement(stmt.getStatement().getIdUnion()).getDateINN().getTime()).toLocalDate());
        fieldStatAdress.setText(stmt.getStatement().getIdAdr().getIdCity().getIdReg().getRegion() + " "
                + stmt.getStatement().getIdAdr().getIdCity().getCity() + " "
                + stmt.getStatement().getIdAdr().getStreet() + " "
                + stmt.getStatement().getHouse() + " "
                + stmt.getStatement().getApartment());
        fieldSpec.setText(DBBean.getInstance().getSpecializationJpaController().findSpecialization(stmt.getStatement().getIdSt().getIdSt()).getSpec());
        dFieldTimeBegin.setValue(new java.sql.Date(stmt.getStatement().getTimeFirst().getTime()).toLocalDate());
        dFieldTimeEnd.setValue(new java.sql.Date(stmt.getStatement().getTimeLast().getTime()).toLocalDate());
        dFieldDateCreate.setValue(new java.sql.Date(stmt.getStatement().getDateCreate().getTime()).toLocalDate());
        fieldKadastrNum.setText(stmt.getStatement().getKadastr());
        fieldSquare.setText(stmt.getStatement().getSquare().toString());
        fieldOkato.setText(stmt.getStatement().getOkato());
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
