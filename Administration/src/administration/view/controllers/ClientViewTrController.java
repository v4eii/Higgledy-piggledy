package administration.view.controllers;

import administration.beans.DBBean;
import administration.statements.IStatement;
import administration.statements.StatementTr;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    @FXML
    private Button btnAccepted, btnDenied, btnCancel;
    
    private StatementTr stmt;
            
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        btnDenied.addEventHandler(ActionEvent.ACTION, (ActionEvent event) ->
        {
            try
            {
                stmt.getStatement().setChekFlag("Denied");
                DBBean.getInstance().getGeneralTradeJpaController().edit(stmt.getStatement());
                MainViewController.getClientStage().close();
            }
            catch (Exception ex)
            {
                Logger.getLogger(ClientViewTrController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btnAccepted.addEventHandler(ActionEvent.ACTION, (ActionEvent event) ->
        {
            try
            {
                stmt.getStatement().setChekFlag("Accepted");
                DBBean.getInstance().getGeneralTradeJpaController().edit(stmt.getStatement());
                MainViewController.getClientStage().close();
            }
            catch (Exception ex)
            {
                Logger.getLogger(ClientViewTrController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btnCancel.addEventHandler(ActionEvent.ACTION, (ActionEvent event) ->
        {
            MainViewController.getClientStage().close();
        });
    }    

    public void initData()
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
        fieldINNDate.setText(DBBean.getInstance().getStatementJpaController().findStatement(stmt.getStatement().getIdUnion()).getDateINN().toString());
        fieldStatAdress.setText(stmt.getStatement().getIdAdr().getIdCity().getIdReg().getRegion() + " " + 
                stmt.getStatement().getIdAdr().getIdCity().getCity() + " " + 
                stmt.getStatement().getIdAdr().getStreet() + " " + 
                stmt.getStatement().getHouse() + " " + 
                stmt.getStatement().getApartment());
        fieldSpec.setText(DBBean.getInstance().getSpecializationJpaController().findSpecialization(stmt.getStatement().getIdSt().getIdSt()).getSpec());
        fieldTimeBegin.setText(stmt.getStatement().getTimeFirst().toString());
        fieldTimeEnd.setText(stmt.getStatement().getTimeLast().toString());
        fieldDateCreate.setText(stmt.getStatement().getDateCreate().toString());
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
