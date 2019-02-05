
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author Aaron Edge (113612)
 */
public class AddNewCarController implements Initializable{
    @FXML private JFXTextField OdometerReading;
    
    @FXML private Label LoggedInUser;
    
    @FXML
    private JFXTextField Licence_Plate_Number;

    @FXML
    private JFXTextField Make;

    @FXML
    private JFXTextField Model;

    @FXML
    private JFXTextField Number_Of_Seats;

    @FXML
    private JFXTextField Rental_Rate;

    @FXML
    private JFXComboBox Type;

    @FXML
    private JFXCheckBox Has_GPS;
    
    @FXML
    private void LogOut(ActionEvent event) 
    {
        MainClass.getInstance().myStageManager.GoToLogin();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        LoggedInUser.setText("Logged in as: "+MainClass.getInstance().mySystemManager.GetLoggedInUser());
        Type.getItems().addAll("Van","Hatchback","Saloon", "Estate", "SUV", "Luxury");
        
        if(MainClass.getInstance().mySystemManager.checkUpdate()){ 
            Licence_Plate_Number.setText(MainClass.getInstance().mySystemManager.selectedCar.LicenceNumber.getValue());
            Make.setText(MainClass.getInstance().mySystemManager.selectedCar.Make.getValue());
            Model.setText(MainClass.getInstance().mySystemManager.selectedCar.Model.getValue());
            //Number_Of_Seats.setText(MainClass.getInstance().mySystemManager.selectedCar..getValue());
            Rental_Rate.setText(MainClass.getInstance().mySystemManager.selectedCar.Rate.getValue().toString());
            Type.getSelectionModel().select(MainClass.getInstance().mySystemManager.selectedCar.BodyType.getValue());
            Has_GPS.setSelected(MainClass.getInstance().mySystemManager.selectedCar.HASGPS.getValue());
            OdometerReading.setText(MainClass.getInstance().mySystemManager.selectedCar.OdometerReading.getValue().toString());
        }
    }
    
    @FXML
    private void GoBack(ActionEvent event) {
        MainClass.getInstance().myStageManager.GoToWindow("ManageCars.fxml");
    }
    @FXML
    private void SaveCar(ActionEvent event) {
        boolean isValid = true;
        String reasonText = "";
        if (Licence_Plate_Number.getText() == null || Licence_Plate_Number.getText().trim().isEmpty()) {
            isValid = false;
            reasonText = reasonText+"Enter Vallid Licence Plate Number\n";
        }
        if (Make.getText() == null || Make.getText().trim().isEmpty()) {
            isValid = false;
            reasonText = reasonText+"Enter Vallid Make\n";
        }
        if (Model.getText() == null || Model.getText().trim().isEmpty()) {
            isValid = false;
            reasonText = reasonText+"Enter Vallid Model\n";
        }
        if (OdometerReading.getText() == null || OdometerReading.getText().trim().isEmpty()) {
            isValid = false;
            reasonText = reasonText+"Enter Vallid Odometer Reading\n";
        }
        if (Type.getSelectionModel().isEmpty()) {
            isValid = false;
            reasonText = reasonText+"Enter Valid Body Type\n";
        }
        if(isValid){
            if(MainClass.getInstance().mySystemManager.checkUpdate()){
                MainClass.getInstance().mySystemManager.UpdateCar(Licence_Plate_Number.getText(), Make.getText(), Model.getText(), Type.getValue().toString(), Has_GPS.isSelected(), Double.parseDouble(Rental_Rate.getText()), Integer.parseInt(OdometerReading.getText()));
                MainClass.getInstance().myStageManager.GoToWindow("ManageCars.fxml");
                MainClass.getInstance().mySystemManager.setIsUpdate(false);
                return;
            }
            MainClass.getInstance().mySystemManager.AddNewCar(Licence_Plate_Number.getText(), Make.getText(), Model.getText(), Type.getValue().toString(), Has_GPS.isSelected(), Double.parseDouble(Rental_Rate.getText()), Integer.parseInt(OdometerReading.getText()));
            MainClass.getInstance().myStageManager.GoToWindow("ManageCars.fxml");
        } else {
            AlertDialog diag = new AlertDialog(MainClass.getInstance().myStageManager.GetStage(),reasonText, 1);
            diag.show();
        }
    }
    
}
