package administration.beans;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;
import javafx.stage.StageStyle;
import org.controlsfx.dialog.ExceptionDialog;

/**
 *
 * @author v4e
 */
public class DBBean {
    
    
    private static DBBean instance = new DBBean();
    private String usr, psw;
    
    /*
    @PersistenceContext(unitName = "CarRentPU")
    private EntityManagerFactory emf = null;
    */
    
    public enum RECORD_STATE
    {
        NEW,
        SAVED,
        EDIT;
    }
    
    public static DBBean getInstance()
    {
        synchronized (DBBean.class)
        {
            if (instance == null)
                instance = new DBBean();
        }
        return instance;
    }
    public String getMD5String(String password)
    {
        try
        {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(password.getBytes());
            byte md5Result[] = md5.digest();
            StringBuilder sb = new StringBuilder(16 * 2);
            for (int i = 0; i < md5Result.length; i++)
            {
                byte b = md5Result[i];
                int intValue = b;

                if (b < 0)
                {
                    intValue = 256 + b;
                }

                String s = Integer.toHexString(intValue);
                if (s.length() == 1)
                {
                    s = "0" + s;
                }
                sb.append(s);
            }

            return sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            return "";
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc="Геттеры/Сеттеры usr, psw, srv">
    
    public void setUser(String usr)
    {
        this.usr = usr;
    }
    
    public void setPassword(String psw)
    {
        this.psw = psw;
    }
    
    public String getPassword()
    {
        return psw;
    }
    
    public String getUser()
    {
        return usr;
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Диалоговые окна">
    
    public void showErrDialog(Throwable ex, String header, String content)
    {
        ExceptionDialog dialog = new ExceptionDialog(ex);
        dialog.setTitle("Ошибка");
        dialog.setHeaderText(header);
        dialog.initStyle(StageStyle.UTILITY);
        if (!content.isEmpty())
            dialog.setContentText(content);
        
        dialog.showAndWait();
    }
    
    public void showInfoDialog(String header, String content)
    {
        Dialog dialog = new Alert(Alert.AlertType.INFORMATION);
        dialog.setTitle("Информация");
        dialog.setHeaderText(header);
        dialog.initStyle(StageStyle.UTILITY);
        if (!content.isEmpty())
            dialog.setContentText(content);
        
        dialog.showAndWait();
    }
    
    public void showWarningDialog(String header, String content)
    {
        Dialog dialog = new Alert(Alert.AlertType.WARNING);
        dialog.initStyle(StageStyle.UTILITY);
        dialog.setHeaderText(header);
        dialog.setTitle("Внимание");
        if (!content.isEmpty())
            dialog.setContentText(content);
        
        dialog.showAndWait();
    }
    
    public Optional<ButtonType> showConfirmDialog(String title, String header, String content)
    {
        Dialog dialog = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK, ButtonType.CANCEL);
        dialog.initStyle(StageStyle.UTILITY);
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        if(!content.isEmpty())
            dialog.setContentText(content);
        
        return dialog.showAndWait();
    }
    
    public Optional<ButtonType> showInputDialog(String title, String header, String content)
    {
        Dialog dlg = new TextInputDialog();
        dlg.initStyle(StageStyle.UTILITY);
        dlg.setTitle(title);
        dlg.setHeaderText(header);
        if(!content.isEmpty())
            dlg.setContentText(content);
        
        return dlg.showAndWait();
    }
            
    //</editor-fold>
    
}
