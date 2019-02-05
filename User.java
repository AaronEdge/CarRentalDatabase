import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Aaron Edge (113612)
 */
public class User{
    final SimpleIntegerProperty ID;
    final SimpleStringProperty UserName;
    final SimpleIntegerProperty PermissionLevel;
    final SimpleStringProperty Password;
 
    User(int myID, String myUserName, int myPermissionLevel, String myPassword) {
        this.ID = new SimpleIntegerProperty(myID);
        this.UserName = new SimpleStringProperty(myUserName);
        this.PermissionLevel = new SimpleIntegerProperty(myPermissionLevel);
        this.Password = new SimpleStringProperty(myPassword);
    }

    public int getID() {
        return ID.get();
    }

    public String getUserName() {
        return UserName.get();
    }

    public String getPermissionLevel() {
        String PermissionString = "Unauthorised";
        switch (PermissionLevel.get()) {
            case 1: PermissionString = "Admin";
            break;
            case 2: PermissionString = "Manager";
            break;
            case 3: PermissionString = "Sales Assistant";
            break;
            default: PermissionString = "Unauthorised";
            break;
        }
                
        return PermissionString;
    }

    public String getPassword() {
        return Password.get();
    }
    
}