/**
 *
 * @author Aaron Edge (113612)
 */
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
public class ReturnCarController implements Initializable{
    
    @FXML private Label LoggedInUser ;
    
    public void initialize(URL url, ResourceBundle rb) {
        LoggedInUser.setText(MainClass.getInstance().mySystemManager.GetLoggedInUser());
    }
    
    @FXML
    void GoBack(ActionEvent event) {
        MainClass.getInstance().myStageManager.GoToWindow("SelectReturnCar.fxml");
    }
    @FXML
    void ReturnCar(ActionEvent event) {
        MainClass.getInstance().myStageManager.GoToWindow("Home.fxml");
    }

    @FXML
    void LogOut(ActionEvent event) {
        MainClass.getInstance().myStageManager.GoToLogin();
    }
}
