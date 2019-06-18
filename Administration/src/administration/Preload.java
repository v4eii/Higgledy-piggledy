package administration;

import administration.beans.DBBean;
import administration.view.controllers.LoginViewController;
import java.net.URL;
import java.util.Optional;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.application.Preloader.StateChangeNotification;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.security.auth.login.AccountException;

/**
 * Simple Preloader Using the ProgressBar Control
 *
 * @author мвидео
 */
public class Preload extends Preloader {
    
    Stage stage;
    
    @Override
    public void start(Stage stage) throws Exception
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
            DBBean.getInstance().setUser(lCtrl.getUser());
            DBBean.getInstance().setPassword(DBBean.getInstance().getMD5String(lCtrl.getPassword()));
            this.stage = stage;
            BorderPane p = FXMLLoader.load(getClass().getResource("/administration/view/Preload.fxml"));
            Scene scene = new Scene(p);
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
            
            if (DBBean.getInstance().getUsersJpaController().findUsers(DBBean.getInstance().getUser()) != null &&
                    DBBean.getInstance().getUsersJpaController().getUserPassword(DBBean.getInstance().getUser())
                            .equals(DBBean.getInstance().getPassword()))
            {
                // TODO: Что делать?
            }
            else
            {
                DBBean.getInstance().showErrDialog(new AccountException("Нет записи"), "Ошибка", "Неверно указаны логин/пароль");
                stop();
                Platform.exit();
            }
        }
        else
        {
            stop();
            Platform.exit();
        }
        
        
    }
    
    @Override
    public void handleApplicationNotification(PreloaderNotification notification) {
        if (notification instanceof StateChangeNotification) {
            stage.hide();
        }
    }
    
    @Override
    public void handleStateChangeNotification(StateChangeNotification scn)
    {
        if (scn.getType() == StateChangeNotification.Type.BEFORE_INIT)
        {
            stage.hide();
        }
    }    
    
}
