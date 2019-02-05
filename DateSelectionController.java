import javafx.scene.control.TextField;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

/**
 *
 * @author Aaron Edge (113612)
 */
public class DateSelectionController implements Initializable{
    
    @FXML private Label LoggedInUser;
    
    @FXML
    private JFXDatePicker Pick_Up_Date;

    @FXML
    private JFXDatePicker Return_Date;

    @FXML
    private JFXTimePicker Pick_Up_Time;

    @FXML
    private JFXTimePicker Return_Time;
    
    @FXML
    private void LogOut(ActionEvent event) 
    {
        MainClass.getInstance().myStageManager.GoToLogin();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        LoggedInUser.setText("Logged in as: "+MainClass.getInstance().mySystemManager.GetLoggedInUser());
        SimpleDateFormat sdfr = new SimpleDateFormat("dd/MMM/yyyy");
        if(MainClass.getInstance().mySystemManager.startDate != null)
        {
            Pick_Up_Date.setValue(MainClass.getInstance().mySystemManager.getLocalDateFromDate(MainClass.getInstance().mySystemManager.startDate));
        } else {
            Pick_Up_Date.setValue(LocalDate.now());
        }
        if(MainClass.getInstance().mySystemManager.endDate != null)
        {
            Return_Date.setValue(MainClass.getInstance().mySystemManager.getLocalDateFromDate(MainClass.getInstance().mySystemManager.endDate));
        } else {
            Return_Date.setValue(LocalDate.now().plusDays(2));
        }
    }
    
    public void GoNext(ActionEvent event)
    {
        MainClass.getInstance().mySystemManager.startDate = Date.from(Pick_Up_Date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        MainClass.getInstance().mySystemManager.endDate = Date.from(Return_Date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        MainClass.getInstance().myStageManager.GoToWindow("CarSelection.fxml");
    }
    
    public void GoBack(ActionEvent event)
    {
        MainClass.getInstance().myStageManager.GoToWindow("CustomerSelection.fxml");
    }
}
