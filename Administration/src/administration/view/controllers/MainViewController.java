package administration.view.controllers;

import administration.beans.DBBean;
import administration.entity.GeneralCafe;
import administration.entity.GeneralTrade;
import administration.statements.IStatement;
import administration.statements.StatementCaf;
import administration.statements.StatementTr;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author v4e
 */
public class MainViewController implements Initializable {
    
    
    private List<StatementCaf> stmtCaf;
    private List<StatementTr> stmtTr;
    
    private static Stage clientStage;
    
    private ClientViewCafController clientViewCaf;
    private ClientViewTrController clientViewTr;
    
    @FXML
    private MenuItem mUnchekedStatement, 
                    mApprovedStatement, 
                    mRejectStatement,
                    mReadStatement;
    @FXML
    private ListView<IStatement> statementList;
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        stmtCaf = new ArrayList<>();
        stmtTr = new ArrayList<>();
        
        List<GeneralCafe> genCafeList = DBBean.getInstance().getGeneralCafeJpaController().findGeneralCafeEntities();
        List<GeneralTrade> genTradeList = DBBean.getInstance().getGeneralTradeJpaController().findGeneralTradeEntities();
        
        genCafeList.forEach((g) ->
        {
            stmtCaf.add(new StatementCaf(g));
        });
        genTradeList.forEach((g)->
        {
            stmtTr.add(new StatementTr(g));
        });
        
        statementList.getItems().addAll(stmtCaf);
        statementList.getItems().addAll(stmtTr);
        
        mReadStatement.addEventHandler(ActionEvent.ACTION, mReadStatementEvent);
    }    
    
    private final EventHandler<ActionEvent> mReadStatementEvent = new EventHandler<ActionEvent>()
    {
        @Override
        public void handle(ActionEvent event)
        {
            if (statementList.getSelectionModel().getSelectedItem() instanceof StatementCaf)
            {
                try
                {
                    URL url = MainViewController.this.getClass().getResource("/administration/view/ClientViewCaf.fxml");
                    FXMLLoader loader = new FXMLLoader(url);
                    AnchorPane aPane = loader.load();
                    clientViewCaf = loader.getController();
                    
                    Scene scene = new Scene(aPane);
                    clientStage = new Stage();
                    clientStage.setScene(scene);
                    clientStage.setTitle("Просмотр заявления");
                    clientStage.showAndWait();
                }
                catch (IOException ex)
                {
                    Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if (statementList.getSelectionModel().getSelectedItem() instanceof StatementTr)
            {
                try
                {
                    URL url = MainViewController.this.getClass().getResource("/administration/view/ClientViewTr.fxml");
                    FXMLLoader loader = new FXMLLoader(url);
                    AnchorPane aPane = loader.load();
                    clientViewTr = loader.getController();

                    Scene scene = new Scene(aPane);
                    clientStage = new Stage();
                    clientStage.setScene(scene);
                    clientStage.setTitle("Просмотр заявления");
                    clientStage.showAndWait();
                }
                catch (IOException ex)
                {
                    Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    };

    public static Stage getClientStage()
    {
        return clientStage;
    }

}
