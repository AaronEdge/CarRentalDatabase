
import com.jfoenix.controls.JFXListView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;

/**
 *
 * @author Aaron Edge (113612)
 */
public class DataAnalysisController implements Initializable{
        
    @FXML private Label LoggedInUser;
    @FXML private JFXListView<Label> CommissionList;
    
    @FXML private Label BestSellingCar;
    @FXML private Label BestSalesPerson;
    
    
    @FXML private void LogOut(ActionEvent event) 
    {
        MainClass.getInstance().myStageManager.GoToLogin();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        LoggedInUser.setText("Logged in as: "+MainClass.getInstance().mySystemManager.GetLoggedInUser());
        String[] itemlist = MainClass.getInstance().mySystemManager.CalculateCommission();
        for(int i = 0; i < itemlist.length; i++) {
            CommissionList.getItems().add(new Label(itemlist[i]));
        }
        System.out.println(MainClass.getInstance().mySystemManager.GetMostHiredCarName());
        BestSalesPerson.setText("Best sales person: "+MainClass.getInstance().mySystemManager.GetBestSalesPerson());
        BestSellingCar.setText("Best selling car: "+MainClass.getInstance().mySystemManager.GetMostHiredCarName());
    }
    
    @FXML
    private void GoBack(ActionEvent event) {
        MainClass.getInstance().myStageManager.GoToWindow("Home.fxml");
    }
    
}
