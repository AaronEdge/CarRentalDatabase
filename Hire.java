import java.util.Date;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Aaron Edge (113612)
 */
public class Hire{
    final SimpleIntegerProperty ID;
    final SimpleStringProperty StartDate;
    final SimpleStringProperty EndDate;
    final SimpleIntegerProperty CustomerID;
    final SimpleIntegerProperty UserID;
    final SimpleStringProperty LicenceNumber;
 
    Hire(int myID, Date myStartDate, Date myEndDate, int myCustomerID, int myUserID, String myLicenceNumber ) {
        this.ID = new SimpleIntegerProperty(myID);
        this.StartDate = new SimpleStringProperty(myStartDate.toString());
        this.EndDate = new SimpleStringProperty(myEndDate.toString());
        this.CustomerID = new SimpleIntegerProperty(myCustomerID);
        this.UserID = new SimpleIntegerProperty(myUserID);
        this.LicenceNumber = new SimpleStringProperty(myLicenceNumber);
    }

    public int getID() {
        return ID.get();
    }

    public String getStartDate() {
        return StartDate.get();
    }

    public String getEndDate() {
        return EndDate.get();
    }

    public int getCustomerID() {
        return CustomerID.get();
    }

    public int getUserID() {
        return UserID.get();
    }

    public String getLicenceNumber() {
        return LicenceNumber.get();
    }

    
}