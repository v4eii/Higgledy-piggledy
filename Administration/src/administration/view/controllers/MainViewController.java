package administration.view.controllers;

import administration.beans.DBBean;
import administration.entity.GeneralCafe;
import administration.entity.GeneralTrade;
import administration.statements.IStatement;
import administration.statements.StatementCaf;
import administration.statements.StatementTr;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author v4e
 */
public class MainViewController implements Initializable {
    
    private double xOffset,
                   yOffset;
    
    private Stage documentStage;
    
    private List<StatementCaf> stmtCaf;
    private List<StatementTr> stmtTr;
    
    private ClientViewTrController clientViewTr;
    private ClientViewCafController clientViewCaf;
    private IStatement activeStatement;
    
    private SimpleBooleanProperty activeButton = new SimpleBooleanProperty(false);
    private SimpleBooleanProperty checkedStatement = new SimpleBooleanProperty(true);
    
    @FXML
    private BorderPane bPane;
    @FXML
    private ListView<IStatement> deniedListView, 
                             acceptedListView, 
                             uncheckedListView;
    @FXML
    private TitledPane deniedPane,
                       acceptedPane,
                       uncheckedPane;
    @FXML
    private Button btnAccept, btnDenied;
    @FXML
    private ToolBar buttonToolBar;
    @FXML
    private MenuItem mAccept, mDenied;
    @FXML
    private RadioMenuItem rmActivateButton,
                          rmCloseToESC, rmCloseToUnfocus, rmCloseToMouseExited;
    @FXML
    private VBox documentBox;
    
    private List<GeneralCafe> filterCafeList, 
                              genCafeList;
    private List<GeneralTrade> filterTradeList,
                               genTradeList;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {   
        stmtCaf = new ArrayList<>();
        stmtTr = new ArrayList<>();

        genCafeList = DBBean.getInstance().getGeneralCafeJpaController().findGeneralCafeEntities();
        genTradeList = DBBean.getInstance().getGeneralTradeJpaController().findGeneralTradeEntities();
        
        rmCloseToESC.setSelected(true);
        rmCloseToESC.selectedProperty().addListener(checkActiveRadioMenuItem);
        rmCloseToMouseExited.selectedProperty().addListener(checkActiveRadioMenuItem);
        rmCloseToUnfocus.selectedProperty().addListener(checkActiveRadioMenuItem);
        
        uncheckedPane.setOnMouseReleased((MouseEvent event) ->
        {
            // TODO: временная реализация, после поставить filteredStatement()
            if (uncheckedListView.getItems().isEmpty())
            {
                filterCafeList = genCafeList.stream().filter((GeneralCafe t) ->
                {
                    return t.getChekFlag().equals("Unchecked");
                }).collect(Collectors.toList());

                filterTradeList = genTradeList.stream().filter((GeneralTrade t) ->
                {
                    return t.getChekFlag().equals("Unchecked");
                }).collect(Collectors.toList());

                filterCafeList.forEach((g) ->
                {
                    stmtCaf.add(new StatementCaf(g));
                });

                filterTradeList.forEach((g) ->
                {   //TODO: 
                    stmtTr.add(new StatementTr(g, new Image("file:///E://INNTest.png"), new Image("file:///E://OGRNTest.png")));
                });

                uncheckedListView.getItems().addAll(FXCollections.observableArrayList(stmtCaf));
                uncheckedListView.getItems().addAll(FXCollections.observableArrayList(stmtTr));

                stmtCaf.clear();
                stmtTr.clear();
                filterCafeList.clear();
                filterTradeList.clear();
            }
        });
        
        acceptedPane.setOnMouseReleased((MouseEvent event) ->
        {
            if (acceptedListView.getItems().isEmpty())
            {
                filteredStatement(acceptedListView, "Accepted");
            }
        });
        
        deniedPane.setOnMouseReleased((MouseEvent event) ->
        {
            if (deniedListView.getItems().isEmpty())
            {
                filteredStatement(deniedListView, "Denied");
            }
        });
        
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
                showStatementInList(uncheckedListView);
                checkedStatement.setValue(Boolean.FALSE);   
            }
        });
        acceptedListView.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) ->
        {
            if (event.getClickCount() == 2)
            {
                showStatementInList(acceptedListView);
                checkedStatement.setValue(Boolean.TRUE);
            }
        });
        deniedListView.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) ->
        {
            if (event.getClickCount() == 2)
            {
                showStatementInList(deniedListView);
                checkedStatement.setValue(Boolean.TRUE);
            }
        });
        btnAccept.addEventHandler(ActionEvent.ACTION, acceptStatement);
        mAccept.addEventHandler(ActionEvent.ACTION, acceptStatement);
        btnDenied.addEventHandler(ActionEvent.ACTION, deniedStatement);
        mDenied.addEventHandler(ActionEvent.ACTION, deniedStatement);
        buttonToolBar.visibleProperty().bind(activeButton);
        btnAccept.disableProperty().bind(checkedStatement);
        btnDenied.disableProperty().bind(checkedStatement);
        mAccept.disableProperty().bind(checkedStatement);
        mDenied.disableProperty().bind(checkedStatement);
    }
    
    private final ChangeListener<Boolean> checkActiveRadioMenuItem = (ObservableValue<? extends Boolean> observable, 
            Boolean oldValue, Boolean newValue) ->
    {
        if (!rmCloseToESC.isSelected() && !rmCloseToMouseExited.isSelected() && !rmCloseToUnfocus.isSelected())
        {
            DBBean.getInstance().showWarningDialog("Внимание", 
                    "Должен быть выбран хотя бы один вариант закрытия окна предпросмотра");
            rmCloseToESC.setSelected(true);
        }
    };

    private void filteredStatement(ListView listView, String key)
    {
        filterCafeList = genCafeList.stream().filter((GeneralCafe t) ->
        {
            return t.getChekFlag().equals(key);
        }).collect(Collectors.toList());

        filterTradeList = genTradeList.stream().filter((GeneralTrade t) ->
        {
            return t.getChekFlag().equals(key);
        }).collect(Collectors.toList());

        filterCafeList.forEach((g) ->
        {
            stmtCaf.add(new StatementCaf(g));
        });

        filterTradeList.forEach((g) ->
        {
            stmtTr.add(new StatementTr(g));
        });

        listView.getItems().addAll(FXCollections.observableArrayList(stmtCaf));
        listView.getItems().addAll(FXCollections.observableArrayList(stmtTr));

        stmtCaf.clear();
        stmtTr.clear();
        filterCafeList.clear();
        filterTradeList.clear();
    }
    
    private void showStatementInList(ListView listView)
    {
        documentBox.getChildren().clear();
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
                showDocuments(selectedStatement.getINN(), selectedStatement.getOGRN());
                bPane.setCenter(aPane);
                listView.getSelectionModel().select(null);
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
                listView.getSelectionModel().select(null);
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
    
    private void showDocuments(Image INN, Image OGRN)
    {
        ImageView imageViewINN = new ImageView(INN);
        imageViewINN.setFitWidth(120);
        imageViewINN.setFitHeight(150);
        ImageView imageViewOGRN = new ImageView(OGRN);
        imageViewOGRN.setFitWidth(120);
        imageViewOGRN.setFitHeight(150);
        
        ToggleButton tgBtnINN = new ToggleButton("", imageViewINN);
        ToggleButton tgBtnOGRN = new ToggleButton("", imageViewOGRN);
        ToggleGroup tgGroup = new ToggleGroup();
        tgBtnINN.setToggleGroup(tgGroup);
        tgBtnOGRN.setToggleGroup(tgGroup);
        
        tgBtnINN.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) ->
        {
            if (tgBtnINN.isSelected())
            {
                showFullDocument(tgBtnINN, INN);
            }
            else if (documentStage != null)
            {
                documentStage.close();
            }
        });
        tgBtnOGRN.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) ->
        {
            if (tgBtnOGRN.isSelected())
            {
                showFullDocument(tgBtnOGRN, OGRN);
            }
            else if (documentStage != null)
            {
                documentStage.close();
            }
        });
        documentBox.getChildren().add(tgBtnINN);
        documentBox.getChildren().add(tgBtnOGRN);
    }
    
    private void showFullDocument(ToggleButton tgBtn, Image img)
    {
        BorderPane tmpPane = new BorderPane();
        ImageView imageViewFull = new ImageView(img);
        imageViewFull.setFitWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2);
        imageViewFull.setFitHeight(Toolkit.getDefaultToolkit().getScreenSize().getHeight()
                - (Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 10));
        tmpPane.setCenter(imageViewFull);
        Scene sc = new Scene(tmpPane);
        documentStage = new Stage();
        documentStage.setTitle("Предпросмотр");
        
        sc.setOnMousePressed((MouseEvent event) ->
        {
            xOffset = documentStage.getX() - event.getScreenX();
            yOffset = documentStage.getY() - event.getScreenY();
        });
        sc.setOnMouseDragged((MouseEvent event) ->
        {
            documentStage.setX(event.getScreenX() + xOffset);
            documentStage.setY(event.getScreenY() + yOffset);
        });
        sc.setOnMouseExited((MouseEvent event2) ->
        {
            if (rmCloseToMouseExited.isSelected())
            {
                documentStage.close();
                tgBtn.setSelected(false);
            }
        });
        sc.setOnKeyPressed((KeyEvent event2) ->
        {
            if (rmCloseToESC.isSelected())
            {
                if (event2.getCode() == KeyCode.ESCAPE)
                {
                    documentStage.close();
                    tgBtn.setSelected(false);
                }
            }
        });
        documentStage.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, 
                Boolean oldValue, Boolean newValue) ->
        {
            if (rmCloseToUnfocus.isSelected())
            {
                if (!documentStage.isFocused())
                {
                    documentStage.close();
                    tgBtn.setSelected(false);
                }
            }
        });
        documentStage.setScene(sc);
        documentStage.initStyle(StageStyle.UNDECORATED);
        documentStage.showAndWait();
    }

    public BorderPane getbPane()
    {
        return bPane;
    }
    
}
