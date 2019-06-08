package administration.view.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author v4e
 */
public class MainViewController implements Initializable {
    
    @FXML
    private MenuItem mUnchekedStatement, 
                    mApprovedStatement, 
                    mRejectStatement;
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            URL urlLog = getClass().getResource("/administration/view/LoginView.fxml");
            FXMLLoader loader = new FXMLLoader(urlLog);
            AnchorPane aPane = loader.load();
            LoginViewController lCtrl = loader.getController();
            Dialog dialog = new Alert(Alert.AlertType.NONE);
            dialog.setTitle("Подключение");
            DialogPane dialogPane = new DialogPane();
            dialogPane.getButtonTypes().add(ButtonType.OK);
            dialogPane.getButtonTypes().add(ButtonType.CANCEL);
            dialogPane.setContent(aPane);
            dialog.setDialogPane(dialogPane);
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.get() == ButtonType.OK)
            {
                //Проверка данных юзера
            }
        }
        catch (IOException ex)
        {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
