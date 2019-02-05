import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
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
public class AddNewCustomerController implements Initializable{
    
        
    @FXML private Label LoggedInUser;
    
    @FXML private JFXTextField First_Name;

    @FXML private JFXTextField Surname;

    @FXML private JFXDatePicker DOB;

    @FXML private JFXTextField Phone_Number;

    @FXML private JFXTextField Address;

    @FXML private JFXTextField City;

    @FXML private JFXTextField County;

    @FXML private JFXTextField Post_Code;

    @FXML private JFXTextField Licence_Number;

    @FXML private JFXTextField Email;

    @FXML private JFXTextField Card_Number;

    @FXML private JFXTextField Card_Holder_Name;

    @FXML private JFXTextField CVV;

    @FXML private JFXDatePicker Expiration_Date;
    
    @FXML private void LogOut(ActionEvent event) 
    {
        MainClass.getInstance().myStageManager.GoToLogin();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LoggedInUser.setText("Logged in as: "+MainClass.getInstance().mySystemManager.GetLoggedInUser());
        if(MainClass.getInstance().mySystemManager.checkUpdate()) {
            First_Name.setText(MainClass.getInstance().mySystemManager.selectedCustomer.getFirstName());
            Surname.setText(MainClass.getInstance().mySystemManager.selectedCustomer.getLastName());
            //DOB.set(MainClass.getInstance().mySystemManager.selectedCustomer.getDOB());
            Phone_Number.setText(MainClass.getInstance().mySystemManager.selectedCustomer.getPhoneNumber());
            //Address
            String cvsSplitBy = ",";
            String[] address =MainClass.getInstance().mySystemManager.selectedCustomer.getAddress().split(cvsSplitBy);
            
            Address.setText(address[0]);
            City.setText(address[1]);
            County.setText(address[2]);
            Post_Code.setText(address[3]);
            
            
            Licence_Number.setText(MainClass.getInstance().mySystemManager.selectedCustomer.getLicenceNumber());
            //Email.setText(MainClass.getInstance().mySystemManager.selectedCustomer.));
            //Card_Number.setText(MainClass.getInstance().mySystemManager.selectedCustomer.);
            //Card_Holder_Name;
            //CVV;
            //Expiration_Date;
        }
    }
    
    @FXML
    private void GoBack(ActionEvent event) {
        MainClass.getInstance().myStageManager.GoToWindow("CustomerSelection.fxml");
    }
    @FXML
    private void SaveUser(ActionEvent event) {
        //vallidation
        boolean isValid = true;
        String reasonText = "";
        if (First_Name.getText() == null || First_Name.getText().trim().isEmpty()) {
            isValid = false;
            reasonText = reasonText+"Enter Vallid First Name\n";
        }
        if (Surname.getText() == null || Surname.getText().trim().isEmpty()) {
            isValid = false;
            reasonText = reasonText+"Enter Vallid Surname\n";
        }
        if (Phone_Number.getText() == null || Phone_Number.getText().trim().isEmpty()) {
            isValid = false;
            reasonText = reasonText+"Enter Vallid Phone Number\n";
        }
        if (Address.getText() == null || Address.getText().trim().isEmpty()) {
            isValid = false;
            reasonText = reasonText+"Enter Vallid Address\n";
        }
        if (City.getText() == null || City.getText().trim().isEmpty()) {
            isValid = false;
            reasonText = reasonText+"Enter Vallid City\n";
        }
        if (County.getText() == null || County.getText().trim().isEmpty()) {
            isValid = false;
            reasonText = reasonText+"Enter Vallid County\n";
        }
        if (Post_Code.getText() == null || Post_Code.getText().trim().isEmpty()) {
            isValid = false;
            reasonText = reasonText+"Enter Vallid post code\n";
        }
        if (Licence_Number.getText() == null || Licence_Number.getText().trim().isEmpty()) {
            isValid = false;
            reasonText = reasonText+"Enter Vallid licence number\n";
        }
        if (Card_Number.getText() == null || Card_Number.getText().trim().isEmpty()) {
            isValid = false;
            reasonText = reasonText+"Enter Vallid card number\n";
        }
        if (CVV.getText() == null || CVV.getText().trim().isEmpty()) {
            isValid = false;
            reasonText = reasonText+"Enter Vallid cvv\n";
        }
        
        if(isValid){
            if (MainClass.getInstance().mySystemManager.checkUpdate()){
                boolean saveSuccess = MainClass.getInstance().mySystemManager.UpdateCustomer(MainClass.getInstance().mySystemManager.selectedCustomer.ID.getValue(), First_Name.getText(), Surname.getText(), DOB.getValue().toString(), Phone_Number.getText(), Address.getText()+", "+City.getText()+", "+County.getText()+", "+Post_Code.getText(), Licence_Number.getText(), Expiration_Date.getValue().toString(), Card_Number.getText(), CVV.getText());
                if (saveSuccess = true) {
                    MainClass.getInstance().mySystemManager.setIsUpdate(false);
                    MainClass.getInstance().myStageManager.GoToWindow("ExistingCustomer.fxml");
                }
                return;
            }
            boolean saveSuccess = MainClass.getInstance().mySystemManager.AddNewCustomer(First_Name.getText(), Surname.getText(), DOB.getValue().toString(), Phone_Number.getText(), Address.getText()+", "+City.getText()+", "+County.getText()+", "+Post_Code.getText(), Licence_Number.getText(), Expiration_Date.getValue().toString(), Card_Number.getText(), CVV.getText());
            if (saveSuccess = true) {
                MainClass.getInstance().myStageManager.GoToWindow("CustomerSelection.fxml");
            }
        } else {
            AlertDialog diag = new AlertDialog(MainClass.getInstance().myStageManager.GetStage(),reasonText, 1);
            diag.show();
        }
        
    }
    
}
