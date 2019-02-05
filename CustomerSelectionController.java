
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Aaron Edge (113612)
 */
public class CustomerSelectionController implements Initializable {
    
    @FXML private Label LoggedInUser;
    @FXML private JFXComboBox searchType;
    // Customer Table
    @FXML private TableView<Customer> table;
    
    @FXML
    private void LogOut(ActionEvent event) 
    {
        MainClass.getInstance().myStageManager.GoToLogin();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LoggedInUser.setText("Logged in as: "+MainClass.getInstance().mySystemManager.GetLoggedInUser());
        searchType.getItems().addAll(MainClass.getInstance().mySystemManager.GetColNames("CUSTOMERS"));
        
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
        
        table.setItems(CustomerList);
        table.getColumns().addAll(cell_ID, cell_FirstName, cell_LastName, cell_DOB, cell_PhoneNumber, cell_Address, cell_LicenceNumber);
        
    }
    
    public void GoNext(ActionEvent event)
    {
        if(table.getSelectionModel().isEmpty()) {
            AlertDialog diag = new AlertDialog(MainClass.getInstance().myStageManager.GetStage(),"Select a customer", 0);
            diag.show();
        } else {
            MainClass.getInstance().mySystemManager.selectedCustomer = table.getSelectionModel().getSelectedItem();
            MainClass.getInstance().myStageManager.GoToWindow("DateSelection.fxml");
        }
    }
    
    public void GoBack(ActionEvent event)
    {
        //ToDo
        MainClass.getInstance().myStageManager.GoToWindow("Home.fxml");
    }
    
    public void AddNewCustomer(ActionEvent event)
    {
        MainClass.getInstance().myStageManager.GoToWindow("AddNewCustomer.fxml");
    }
}
