
import com.jfoenix.controls.JFXCheckBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 *
 * @author Aaron Edge (113612)
 */
public class RentalAgreementController implements Initializable{
    
    private final String rentalAgreementText = "Section 1.1 - Rental car details\n" +
"\n" +
"Make: "+MainClass.getInstance().mySystemManager.selectedCar.getMake()+"    Model: "+MainClass.getInstance().mySystemManager.selectedCar.getModel()+"\n"+
"Has GPS: "+MainClass.getInstance().mySystemManager.selectedCar.getHASGPS()+"\n"+
"License Plate Number: "+MainClass.getInstance().mySystemManager.selectedCar.getLicenceNumber()+"\n" +
"Odometer Reading: @ Check Out "+MainClass.getInstance().mySystemManager.selectedCar.getOdometerReading()+" \n\n" +
"Section 1.2 - Rental details\n" +
"\n" +
"Car Rental Rate: "+MainClass.getInstance().mySystemManager.selectedCar.getRate()+"\n\n" +
"Total Cost of Hire: "+MainClass.getInstance().mySystemManager.CalculateCostOfHire()+"\n\n"+
"Vehicle Check Out: "+MainClass.getInstance().mySystemManager.startDate.toString()+"\n" +
"Vehicle Is Due Back: "+MainClass.getInstance().mySystemManager.endDate+"PM\n" +
"\n" +
"Section 2.1 - Customer details\n" +
"\n" +
"Driver’s Name: " + MainClass.getInstance().mySystemManager.selectedCustomer.getFirstName() + " " + MainClass.getInstance().mySystemManager.selectedCustomer.getLastName() + "\n" +
"Driver’s phone number: "+MainClass.getInstance().mySystemManager.selectedCustomer.getPhoneNumber()+"\n"+ 
"Home Address: "+MainClass.getInstance().mySystemManager.selectedCustomer.Address+"\n" +
"Driver’s License Number: "+MainClass.getInstance().mySystemManager.selectedCustomer.getLicenceNumber()+" 	Expiration: todo"+"\n" +
"Date of Birth:"+MainClass.getInstance().mySystemManager.selectedCustomer.getDOB()+"\n" +
"Credit Card Type:  todo"+"\n" +
"Credit Card Number: todo "+" Name on the Card: todo\n" +
"Cvv number: todo\n" +
"Expiration Date: ____/____/______ Security Code: ______\n" +
"\n" +
"Section 3.1 - Agreement details\n" +
"\n" +
"Only the person(s) that are listed on this Car Rental Agreement and above the age of twenty-one may drive this vehicle. The above mentioned Car Renter is hereby responsible for all collision damage to the vehicle regardless if someone else is at fault or the cause is not known. The Car Renter is fully responsible for the cost of any repair up to the value of the vehicle. The Car Renter’s Insurance may cover all or only part of the financial liability for the rented vehicle. Car Renter should check with their insurance company regarding their coverage and what they are and are not liable for. If there is no breach of this contract the Car Renter and any authorized driver is provided liability insurance and is limited to the minimum financial responsibility as required by state law. Liability Insurance will only be in excess over any and all additional collectible insurance. The above mentioned Car Renter hereby waives all uninsured and underinsured motorists, no fault and any other optional additional coverage. If such additional coverage cannot be waived or excluded then the Car Renter agrees that such coverage will be limited to only the minimum state requirements.\n" +
"The Car Renter is hereby bound by the terms and conditions of this Car Rental Agreement. The vehicle must be returned to the same location in which it was picked up for rental and on or before the above indicated due back date and time. There will be additional fees due if the vehicle is not returned as specified above. Where it is permitted by law the Car Renter hereby authorizes us to process their credit card information in their name for all Car Rental charges, including the full vehicle value of any vehicle that is not returned to the Car Rental Company, all fines, towing, any court costs, penalties, and or administrative fees that we may incur for parking, traffic and or other violations that may be incurred by the Car Renter during the Car Rental term period as stated above and to apply any payments made to the charges in whatever order that the Car Rental Company sees as necessary. By signing below Car Renter is also signing their Car Rental credit card voucher.\n" +
"The individual mentioned above in this Car Rental Contract hereby agrees to fill the fuel tank at the above indicated level upon returning the car. Failure to fill the tank at the prescribed level will result in an additional penalty charge of £20.00 per quarter tank of fuel.\n" +
"It is the Car Renter’s responsibility for all lost car keys and / or a lockout situation.";
    
    Text text1 = new Text(rentalAgreementText);
    
    @FXML private Label LoggedInUser;
    @FXML private TextFlow agreementDisplay;
    @FXML private JFXCheckBox AgreementSigned;
    @FXML
    private void LogOut(ActionEvent event) 
    {
        MainClass.getInstance().myStageManager.GoToLogin();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        LoggedInUser.setText("Logged in as: "+MainClass.getInstance().mySystemManager.GetLoggedInUser());
        agreementDisplay.getChildren().add(text1);
    }
    
    @FXML
    public void GoNext(ActionEvent event)
    {
        if(AgreementSigned.isSelected()) {
            MainClass.getInstance().mySystemManager.MakeNewHire();
            MainClass.getInstance().myStageManager.GoToWindow("Home.fxml");
        } else {
            AlertDialog diag = new AlertDialog(MainClass.getInstance().myStageManager.GetStage(),"Agreement not signed", 0);
            diag.show();
        }
    }
    
    @FXML
    public void GoBack(ActionEvent event)
    {
        MainClass.getInstance().myStageManager.GoToWindow("CarSelection.fxml");
    }
    
    @FXML
    public void PrintRentalAgreement() {
        // Printing disabled for development
        AlertDialog diag = new AlertDialog(MainClass.getInstance().myStageManager.GetStage(),"Rental Agreement printed", 0);
        diag.show();
    }
}
