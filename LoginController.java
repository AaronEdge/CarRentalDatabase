import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author Aaron Edge (113612)
 */
public class LoginController {
    
    @FXML private TextField UserNameInput;
    
    @FXML private TextField PasswordInput;
    
    @FXML private Label ValidationMessage;

    @FXML
    private void UserLogin(ActionEvent event) {
        ValidationMessage.setVisible(false);
        if (MainClass.getInstance().mySystemManager.Login(UserNameInput.getText(), PasswordInput.getText())) {
            MainClass.getInstance().myStageManager.GoToWindow("Home.fxml");
        } else {
            System.out.println("Login Error");
            ValidationMessage.setText(MainClass.getInstance().mySystemManager.loginErrorString);
            ValidationMessage.setVisible(true);
        }
    }
    
    @FXML
    private void LogOut(ActionEvent event) 
    {
        MainClass.getInstance().myStageManager.GoToLogin();
    }
    
    @FXML
    void ForgotPassword(ActionEvent event) {
        AlertDialog diag = new AlertDialog(MainClass.getInstance().myStageManager.GetStage(),"Contact systems admin", 1);
        diag.show();
    }
}
