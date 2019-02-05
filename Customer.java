import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Aaron Edge (113612)
 */
public class Customer{
    final SimpleIntegerProperty ID;
    //final SimpleStringProperty ID;
    final SimpleStringProperty FirstName;
    final SimpleStringProperty LastName;
    final SimpleStringProperty DOB;
    final SimpleStringProperty PhoneNumber;
    final SimpleStringProperty Address;
    final SimpleStringProperty LicenceNumber;
 
    Customer(int myID, String myFirstName, String myLastName, String myDOB, String myPhoneNumber, String myAddress, String myLicenceNumber) {
        this.ID = new SimpleIntegerProperty(myID);
        this.FirstName = new SimpleStringProperty(myFirstName);
        this.LastName = new SimpleStringProperty(myLastName);
        this.DOB = new SimpleStringProperty(myDOB);
        this.PhoneNumber = new SimpleStringProperty(myPhoneNumber);
        this.Address = new SimpleStringProperty(myAddress);
        this.LicenceNumber = new SimpleStringProperty(myLicenceNumber);
    }
    
    public int getID() {
        return ID.get();
    }
    public void setID(int myID) {
        ID.set(myID);
    }
    public String getFirstName() {
        return FirstName.get();
    }
    public void setFirstName(String myFirstName) {
        FirstName.set(myFirstName);
    }
    public String getLastName() {
        return LastName.get();
    }
    public void setLastName(String myLastName) {
        LastName.set(myLastName);
    }
    public String getDOB() {
        return DOB.get();
    }
    public void setDOB(String myDOB) {
        DOB.set(myDOB);
    }
    public String getPhoneNumber() {
        return PhoneNumber.get();
    }
    public void setPhoneNumber(String myPhoneNumber) {
        PhoneNumber.set(myPhoneNumber);
    }
    public String getAddress() {
        return Address.get();
    }
    public void AddressID(String myAddress) {
        Address.set(myAddress);
    }
    public String getLicenceNumber() {
        return LicenceNumber.get();
    }
    public void setLicenceNumber(String myLicenceNumber) {
        LicenceNumber.set(myLicenceNumber);
    }
}
