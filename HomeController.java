
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import com.jfoenix.controls.JFXButton;
import javafx.scene.layout.VBox;

/**
 *
 * @author Aaron Edge (113612)
 */
public class HomeController implements Initializable{
    
    @FXML private Label LoggedInUser;
    @FXML private JFXButton manageUsersButton;
    @FXML private JFXButton manageCarsButton;
    @FXML private JFXButton dataAnalysisButton;
    @FXML private VBox MainMenu;
    @FXML private VBox SettingsMenu;
    
    @FXML
    private void LogOut(ActionEvent event) 
    {
        MainClass.getInstance().myStageManager.GoToLogin();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MainMenu.setVisible(true);
        MainMenu.setDisable(false);
        SettingsMenu.setVisible(false);
        SettingsMenu.setDisable(true);
        LoggedInUser.setText(MainClass.getInstance().mySystemManager.GetLoggedInUser());
        if(MainClass.getInstance().mySystemManager.GetPermissionLevel()<2)
        {
            manageUsersButton.setDisable(false);
            manageCarsButton.setDisable(false);
            dataAnalysisButton.setDisable(false);
            
        } else
        {
            manageUsersButton.setDisable(true);
            manageCarsButton.setDisable(true);
            dataAnalysisButton.setDisable(true);
        }
    }
    
    @FXML
    private void MakeNewHire(ActionEvent event)
    {
        MainClass.getInstance().mySystemManager.ClearSelection();
        MainClass.getInstance().myStageManager.GoToWindow("CustomerSelection.fxml");
    }
    @FXML
    private void EditExistingHire(ActionEvent event)
    {
        MainClass.getInstance().myStageManager.GoToWindow("ExistingHires.fxml");
    }
    @FXML
    private void EditExistingCustomer(ActionEvent event)
    {
        MainClass.getInstance().myStageManager.GoToWindow("ExistingCustomer.fxml");
    }
    @FXML
    private void ManageUsers(ActionEvent event)
    {
        MainClass.getInstance().myStageManager.GoToWindow("ManageUsers.fxml");
    }
    @FXML
    private void ManageCars(ActionEvent event)
    {
        MainClass.getInstance().myStageManager.GoToWindow("ManageCars.fxml");
    }
    @FXML
    private void ViewData(ActionEvent event)
    {
        MainClass.getInstance().myStageManager.GoToWindow("DataAnalysis.fxml");
    }
    
    @FXML
    void OpenSettings(ActionEvent event) {
        MainMenu.setVisible(false);
        MainMenu.setDisable(true);
        SettingsMenu.setVisible(true);
        SettingsMenu.setDisable(false);
    }
    @FXML
    void CloseSettings(ActionEvent event) {
        MainMenu.setVisible(true);
        MainMenu.setDisable(false);
        SettingsMenu.setVisible(false);
        SettingsMenu.setDisable(true);
    }
    
    @FXML
    void ReturnCar(ActionEvent event) {
        MainClass.getInstance().myStageManager.GoToWindow("SelectReturnCar.fxml");
    }
    @FXML
    void TestStuff(ActionEvent event) {
        AlertPromptDialog alert = new AlertPromptDialog();
        alert.show(MainClass.getInstance().myStageManager.GetStage(),"This is a popup");
    }
    
    @FXML
    void TestDiag(ActionEvent event) {
        AlertDialog diag = new AlertDialog(MainClass.getInstance().myStageManager.GetStage(),"This is an info popup", 0);
        diag.show();
    }
    
    
}
