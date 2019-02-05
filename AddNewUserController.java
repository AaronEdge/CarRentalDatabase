
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author Beast
 */
public class AddNewUserController implements Initializable{
    @FXML private Label LoggedInUser;
    
    @FXML
    private JFXTextField User_Name;

    @FXML
    private JFXPasswordField Password;

    @FXML
    private JFXPasswordField Re_Password;

    @FXML
    private JFXComboBox Permission_Level;
    
    @FXML
    private void LogOut(ActionEvent event) 
    {
        MainClass.getInstance().myStageManager.GoToLogin();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        LoggedInUser.setText("Logged in as: "+MainClass.getInstance().mySystemManager.GetLoggedInUser());
        Permission_Level.getItems().addAll("Admin","Manager","Sales Assistant");
        
        if(MainClass.getInstance().mySystemManager.checkUpdate()) {
            User_Name.setText(MainClass.getInstance().mySystemManager.selectedUser.UserName.getValue());
            System.out.println(MainClass.getInstance().mySystemManager.selectedUser.PermissionLevel.getValue());
            Permission_Level.getSelectionModel().select(MainClass.getInstance().mySystemManager.selectedUser.PermissionLevel.getValue()-1);
        }
    }
    
    @FXML
    private void GoBack(ActionEvent event) {
        MainClass.getInstance().myStageManager.GoToWindow("ManageUsers.fxml");
    }
    @FXML
    private void AddNewUser(ActionEvent event) {
        boolean isValid = true;
        String reasonText = "";
        if(!Password.getText().equals(Re_Password.getText())) {
            isValid = false;
            reasonText = reasonText+"Password does not match\n";
        }
        if (User_Name.getText() == null || User_Name.getText().trim().isEmpty()) {
            isValid = false;
            reasonText = reasonText+"Enter Vallid User Name\n";
        }
        if (Password.getText() == null || Password.getText().trim().isEmpty()) {
            isValid = false;
            reasonText = reasonText+"Enter Vallid Password\n";
        }
        if (Re_Password.getText() == null || Re_Password.getText().trim().isEmpty()) {
            isValid = false;
            reasonText = reasonText+"Re-enter Password\n";
        }
        
       
        if(isValid) 
        {
            int permission = 100;
            switch(Permission_Level.getValue().toString()) 
            {
                case "Admin": permission = 1;
                break;
                case "Manager": permission = 2;
                break;
                case "Sales Assistant": permission = 3;
                break;
                default: permission = 100;
            }
            
            if(MainClass.getInstance().mySystemManager.checkUpdate()) {
                MainClass.getInstance().mySystemManager.UpdateUser(MainClass.getInstance().mySystemManager.selectedUser.ID.getValue(), User_Name.getText(), permission, Password.getText());
                MainClass.getInstance().mySystemManager.setIsUpdate(false);
                MainClass.getInstance().myStageManager.GoToWindow("ManageUsers.fxml");
                return;
            }
            MainClass.getInstance().mySystemManager.AddNewUser(User_Name.getText(), permission, Password.getText());
            MainClass.getInstance().myStageManager.GoToWindow("ManageUsers.fxml");
        } else {
            AlertDialog diag = new AlertDialog(MainClass.getInstance().myStageManager.GetStage(),reasonText, 1);
            diag.show();
        }
    }
}
