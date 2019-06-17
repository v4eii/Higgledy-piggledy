package administration;

import javafx.application.Application;
import static javafx.application.Application.STYLESHEET_CASPIAN;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author v4e
 */
public class Administration extends Application {
    
    @Override
    public void start(Stage stage) throws Exception
    {
        /*URL urlLog = getClass().getResource("/administration/view/LoginView.fxml");
        FXMLLoader loader = new FXMLLoader(urlLog);
        AnchorPane aPane = loader.load();
        LoginViewController lCtrl = loader.getController();
        Dialog dialog = new Alert(Alert.AlertType.NONE);
        dialog.setTitle("�����������");
        DialogPane dialogPane = new DialogPane();
        dialogPane.getButtonTypes().add(ButtonType.OK);
        dialogPane.getButtonTypes().add(ButtonType.CANCEL);
        dialogPane.setContent(aPane);
        dialog.setDialogPane(dialogPane);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.get() == ButtonType.OK)
        {*/
            // TODO: �������� ������ �����
            
            //DBBean.getInstance().setUser(lCtrl.getUser());
            //DBBean.getInstance().setPassword(DBBean.getInstance().getMD5String(lCtrl.getPassword()));

            Application.setUserAgentStylesheet(STYLESHEET_CASPIAN);
            Parent root = FXMLLoader.load(getClass().getResource("/administration/view/MainView.fxml"));
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("��������� ���������");
            stage.setMaximized(true);
            stage.show();
        /*}
        else
        {
            stop();
            Platform.exit();
        }*/
            
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }
    
}
