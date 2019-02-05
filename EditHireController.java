import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class EditHireController implements Initializable{

    @FXML
    private Label LoggedInUser;

    @FXML
    private JFXDatePicker Pick_Up_Date;

    @FXML
    private JFXDatePicker Return_Date;

    @FXML
    private JFXTimePicker Pick_Up_Time;

    @FXML
    private JFXTimePicker Return_Time;

    @FXML
    private TableView<Customer> CustomerTable;

    @FXML
    private TableView<Car> CarTable;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableColumn<Car, String> cell_Licence = new TableColumn<>("Licence Number");
        cell_Licence.setPrefWidth(100);
        cell_Licence.setCellValueFactory(new PropertyValueFactory<>("LicenceNumber"));
        
        TableColumn<Car, String> cell_Make = new TableColumn<>("Make");
        cell_Make.setPrefWidth(100);
        cell_Make.setCellValueFactory(new PropertyValueFactory<>("Make"));
        
        TableColumn<Car, String> cell_Model = new TableColumn<>("Model");
        cell_Model.setPrefWidth(100);
        cell_Model.setCellValueFactory(new PropertyValueFactory<>("Model"));
        
        TableColumn<Car, String> cell_HasGPS = new TableColumn<>("GPS");
        cell_HasGPS.setPrefWidth(50);
        cell_HasGPS.setCellValueFactory(new PropertyValueFactory<>("HASGPS"));
        
        TableColumn<Car, String> cell_Rate = new TableColumn<>("Rate");
        cell_Rate.setPrefWidth(50);
        cell_Rate.setCellValueFactory(new PropertyValueFactory<>("Rate"));
        
        TableColumn<Car, String> cell_BodyType = new TableColumn<>("Body Type");
        cell_BodyType.setPrefWidth(75);
        cell_BodyType.setCellValueFactory(new PropertyValueFactory<>("BodyType"));
        
        ObservableList<Car> CarList;
        CarList = MainClass.getInstance().mySystemManager.getAllCarsList();
        
        CarTable.setItems(CarList);
        
        CarTable.getColumns().addAll(cell_Licence, cell_Make, cell_Model, cell_HasGPS, cell_Rate, cell_BodyType);
        
        LoggedInUser.setText("Logged in as: "+MainClass.getInstance().mySystemManager.GetLoggedInUser());
        
        // Setup Customer list
        TableColumn<Customer, String> cell_ID = new TableColumn<>("ID");
        cell_ID.setPrefWidth(50);
        cell_ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        
        TableColumn<Customer, String> cell_FirstName = new TableColumn<>("First Name");
        cell_FirstName.setPrefWidth(100);
        cell_FirstName.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        
        TableColumn<Customer, String> cell_LastName = new TableColumn<>("Last Name");
        cell_LastName.setPrefWidth(100);
        cell_LastName.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        
        TableColumn<Customer, Date> cell_DOB = new TableColumn<>("D.O.B");
        cell_DOB.setPrefWidth(100);
        cell_DOB.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        
        TableColumn<Customer, String> cell_PhoneNumber = new TableColumn<>("Phone Number");
        cell_PhoneNumber.setPrefWidth(100);
        cell_PhoneNumber.setCellValueFactory(new PropertyValueFactory<>("PhoneNumber"));
        
        TableColumn<Customer, String> cell_Address = new TableColumn<>("Address");
        cell_Address.setPrefWidth(150);
        cell_Address.setCellValueFactory(new PropertyValueFactory<>("Address"));
        
        TableColumn<Customer, String> cell_LicenceNumber = new TableColumn<>("Licence Number");
        cell_LicenceNumber.setPrefWidth(100);
        cell_LicenceNumber.setCellValueFactory(new PropertyValueFactory<>("LicenceNumber"));

        ObservableList<Customer> CustomerList;
        CustomerList = MainClass.getInstance().mySystemManager.getAllCustomersList();
        
        CustomerTable.setItems(CustomerList);
        CustomerTable.getColumns().addAll(cell_ID, cell_FirstName, cell_LastName, cell_DOB, cell_PhoneNumber, cell_Address, cell_LicenceNumber);
    }

    @FXML
    void Cancel(ActionEvent event) {
        MainClass.getInstance().mySystemManager.setIsUpdate(false);
        MainClass.getInstance().myStageManager.GoToWindow("ExistingHires.fxml");
    }

    @FXML
    void LogOut(ActionEvent event) {
        MainClass.getInstance().myStageManager.GoToLogin();
    }

    @FXML
    void Save(ActionEvent event) {
        MainClass.getInstance().mySystemManager.UpdateHire(MainClass.getInstance().mySystemManager.selectedHire.ID.intValue(), Date.from(Pick_Up_Date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(Return_Date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()), CustomerTable.getSelectionModel().getSelectedItem().ID.getValue(), MainClass.getInstance().mySystemManager.GetLoggedInUserID(), CarTable.getSelectionModel().getSelectedItem().LicenceNumber.getValue().toString());
        MainClass.getInstance().mySystemManager.setIsUpdate(false);
        MainClass.getInstance().myStageManager.GoToWindow("ExistingHires.fxml");
    }

}
