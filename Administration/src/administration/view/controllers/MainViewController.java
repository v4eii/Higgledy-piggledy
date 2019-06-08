package administration.view.controllers;

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
        // Подключение к базе + заполнение листов
        stmtCaf = new ArrayList<>();
        stmtTr = new ArrayList<>();
        //tmp
        stmtTr.add(new StatementTr("Что-то"));
        statementList.getItems().add(stmtTr.get(0));
        //
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
                    URL url = MainViewController.this.getClass().getResource("/administration/view/ClientView.fxml");
                    FXMLLoader loader = new FXMLLoader(url);
                    AnchorPane aPane = loader.load();
                    //clientViewController = loader.getController();
//                  initData();
                    Scene scene = new Scene(aPane);
                    clientStage = new Stage();
                    clientStage.setScene(scene);
                    clientStage.setTitle("Просмотр заявки");
                    clientStage.showAndWait();
                }
                catch (IOException ex)
                {
                    Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else
            {
                System.out.println("Петуч");
            }
        }
    };

    public static Stage getClientStage()
    {
        return clientStage;
    }
    
    

}
