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
import java.util.stream.Collectors;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author v4e
 */
public class MainViewController implements Initializable {
    
    
    private List<StatementCaf> stmtCaf;
    private List<StatementTr> stmtTr;
    
    private ClientViewTrController clientViewTr;
    private ClientViewCafController clientViewCaf;
    private IStatement activeStatement;
    
    private SimpleBooleanProperty activeButton = new SimpleBooleanProperty(false);
    
    @FXML
    private BorderPane bPane;
    @FXML
    private ListView<IStatement> deniedListView, 
                             acceptedListView, 
                             uncheckedListView;
    @FXML
    private Button btnAccept, btnDenied;
    @FXML
    private ToolBar buttonToolBar;
    @FXML
    private MenuItem mAccept, mDenied;
    @FXML
    private RadioMenuItem rmActivateButton;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {   
        stmtCaf = new ArrayList<>();
        stmtTr = new ArrayList<>();

        List<GeneralCafe> genCafeList = DBBean.getInstance().getGeneralCafeJpaController().findGeneralCafeEntities();
        List<GeneralTrade> genTradeList = DBBean.getInstance().getGeneralTradeJpaController().findGeneralTradeEntities();

        //<editor-fold defaultstate="collapsed" desc="Unchecked filter">
        List<GeneralCafe> filterCafeList = genCafeList.stream().filter((GeneralCafe t) ->
        {
            return t.getChekFlag().equals("Unchecked");
        }).collect(Collectors.toList());

        List<GeneralTrade> filterTradeList = genTradeList.stream().filter((GeneralTrade t) ->
        {
            return t.getChekFlag().equals("Unchecked");
        }).collect(Collectors.toList());

        filterCafeList.forEach((g) ->
        {
            stmtCaf.add(new StatementCaf(g));
        });

        filterTradeList.forEach((g) ->
        {
            stmtTr.add(new StatementTr(g));
        });

        uncheckedListView.getItems().addAll(FXCollections.observableArrayList(stmtCaf));
        uncheckedListView.getItems().addAll(FXCollections.observableArrayList(stmtTr));

        stmtCaf.clear();
        stmtTr.clear();
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Accepted filter">
        filterCafeList = genCafeList.stream().filter((GeneralCafe t) ->
        {
            return t.getChekFlag().equals("Accepted");
        }).collect(Collectors.toList());

        filterTradeList = genTradeList.stream().filter((GeneralTrade t) ->
        {
            return t.getChekFlag().equals("Accepted");
        }).collect(Collectors.toList());

        filterCafeList.forEach((g) ->
        {
            stmtCaf.add(new StatementCaf(g));
        });

        filterTradeList.forEach((g) ->
        {
            stmtTr.add(new StatementTr(g));
        });

        acceptedListView.getItems().addAll(FXCollections.observableArrayList(stmtCaf));
        acceptedListView.getItems().addAll(FXCollections.observableArrayList(stmtTr));

        stmtCaf.clear();
        stmtTr.clear();
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Denied filter">
        filterCafeList = genCafeList.stream().filter((GeneralCafe t) ->
        {
            return t.getChekFlag().equals("Denied");
        }).collect(Collectors.toList());

        filterTradeList = genTradeList.stream().filter((GeneralTrade t) ->
        {
            return t.getChekFlag().equals("Denied");
        }).collect(Collectors.toList());

        filterCafeList.forEach((g) ->
        {
            stmtCaf.add(new StatementCaf(g));
        });

        filterTradeList.forEach((g) ->
        {
            stmtTr.add(new StatementTr(g));
        });

        deniedListView.getItems().addAll(FXCollections.observableArrayList(stmtCaf));
        deniedListView.getItems().addAll(FXCollections.observableArrayList(stmtTr));
        //</editor-fold>

        rmActivateButton.addEventHandler(ActionEvent.ACTION, (ActionEvent event) ->
        {
            if (rmActivateButton.isSelected())
                activeButton.setValue(Boolean.TRUE);
            else
                activeButton.setValue(Boolean.FALSE);
        });
        uncheckedListView.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) ->
        {
            if (event.getClickCount() == 2)
            {
                readStatementInList(uncheckedListView);
            }
        });
        acceptedListView.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) ->
        {
            if (event.getClickCount() == 2)
            {
                readStatementInList(acceptedListView);
            }
        });
        deniedListView.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) ->
        {
            if (event.getClickCount() == 2)
            {
                readStatementInList(deniedListView);
            }
        });
        btnAccept.addEventHandler(ActionEvent.ACTION, acceptStatement);
        mAccept.addEventHandler(ActionEvent.ACTION, acceptStatement);
        btnDenied.addEventHandler(ActionEvent.ACTION, deniedStatement);
        mDenied.addEventHandler(ActionEvent.ACTION, deniedStatement);
        buttonToolBar.visibleProperty().bind(activeButton);
    }

    private void readStatementInList(ListView listView)
    {
        if (listView.getSelectionModel().getSelectedItem() instanceof StatementTr)
        {
            StatementTr selectedStatement = (StatementTr) listView.getSelectionModel().getSelectedItem();
            try
            {
                URL url = getClass().getResource("/administration/view/ClientViewTr.fxml");
                FXMLLoader loader = new FXMLLoader(url);
                AnchorPane aPane = loader.load();
                clientViewTr = loader.getController();
                clientViewTr.setStmt(selectedStatement);
                activeStatement = selectedStatement;
                clientViewTr.initData();
                bPane.setCenter(aPane);
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
        else if (listView.getSelectionModel().getSelectedItem() instanceof StatementCaf)
        {
            StatementCaf selectedStatement = (StatementCaf) listView.getSelectionModel().getSelectedItem();
            try
            {
                URL url = getClass().getResource("/administration/view/ClientViewCaf.fxml");
                FXMLLoader loader = new FXMLLoader(url);
                AnchorPane aPane = loader.load();
                clientViewCaf = loader.getController();
                clientViewCaf.setStmt(selectedStatement);
                activeStatement = selectedStatement;
                clientViewCaf.initData();
                bPane.setCenter(aPane);
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
    }
    
    private final EventHandler<ActionEvent> acceptStatement = (ActionEvent event) ->
    {
        if (activeStatement instanceof StatementTr)
        {
            try
            {
                StatementTr stmt = (StatementTr) activeStatement;
                stmt.getStatement().setChekFlag("Accepted");
                DBBean.getInstance().getGeneralTradeJpaController().edit(stmt.getStatement());
                uncheckedListView.getItems().remove(stmt);
                acceptedListView.getItems().add(stmt);
                uncheckedListView.refresh();
                acceptedListView.refresh();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
        else if (activeStatement instanceof StatementCaf)
        {
            try
            {
                StatementCaf stmt = (StatementCaf) activeStatement;
                stmt.getStatement().setChekFlag("Accepted");
                DBBean.getInstance().getGeneralCafeJpaController().edit(stmt.getStatement());
                uncheckedListView.getItems().remove(stmt);
                acceptedListView.getItems().add(stmt);
                uncheckedListView.refresh();
                acceptedListView.refresh();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    };
    
    private final EventHandler<ActionEvent> deniedStatement = (ActionEvent event) ->
    {
        if (activeStatement instanceof StatementTr)
        {
            try
            {
                StatementTr stmt = (StatementTr) activeStatement;
                stmt.getStatement().setChekFlag("Denied");
                DBBean.getInstance().getGeneralTradeJpaController().edit(stmt.getStatement());
                uncheckedListView.getItems().remove(stmt);
                deniedListView.getItems().add(stmt);
                uncheckedListView.refresh();
                deniedListView.refresh();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
        else if (activeStatement instanceof StatementCaf)
        {
            try
            {
                StatementCaf stmt = (StatementCaf) activeStatement;
                stmt.getStatement().setChekFlag("Denied");
                DBBean.getInstance().getGeneralCafeJpaController().edit(stmt.getStatement());
                uncheckedListView.getItems().remove(stmt);
                deniedListView.getItems().add(stmt);
                uncheckedListView.refresh();
                deniedListView.refresh();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    };
}
