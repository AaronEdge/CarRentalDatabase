import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Aaron Edge (113612)
 */
public class Car{
    final SimpleStringProperty LicenceNumber;
    final SimpleStringProperty Make;
    final SimpleStringProperty Model;
    final SimpleBooleanProperty HASGPS;
    final SimpleDoubleProperty Rate;
    final SimpleStringProperty BodyType;
    final SimpleIntegerProperty OdometerReading;
 
    Car(String myLicenceNumber, String myMake, String myModel, boolean myHasGPS, double myRate, String myBodyType,int myOdometerReading) {
        this.LicenceNumber = new SimpleStringProperty(myLicenceNumber);
        this.Make = new SimpleStringProperty(myMake);
        this.Model = new SimpleStringProperty(myModel);
        this.HASGPS = new SimpleBooleanProperty(myHasGPS);
        this.Rate = new SimpleDoubleProperty(myRate);
        this.BodyType = new SimpleStringProperty(myBodyType);
        this.OdometerReading = new SimpleIntegerProperty(myOdometerReading);
    }

    public String getLicenceNumber() {
        return LicenceNumber.get();
    }

    public String getMake() {
        return Make.get();
    }

    public String getModel() {
        return Model.get();
    }

    public boolean getHASGPS() {
        return HASGPS.get();
    }

    public double getRate() {
        return Rate.get();
    }

    public String getBodyType() {
        return BodyType.get();
    }
    
    public int getOdometerReading() {
        return OdometerReading.get();
    }
    
    
}