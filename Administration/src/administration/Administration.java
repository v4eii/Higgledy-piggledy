package administration;

import com.sun.javafx.application.LauncherImpl;
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
        Application.setUserAgentStylesheet(STYLESHEET_CASPIAN);
        Parent root = FXMLLoader.load(getClass().getResource("/administration/view/MainView.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("Обработка заявлений");
        stage.setMaximized(true);
        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        LauncherImpl.launchApplication(Administration.class, Preload.class, args);
//        launch(args);
    }
    
}
