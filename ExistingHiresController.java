
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Aaron Edge (113612)
 */
public class ExistingHiresController implements Initializable{
    
    @FXML private Label LoggedInUser;
    
    @FXML
    private TableView<Hire> table;
    
    @FXML
    private void LogOut(ActionEvent event) 
    {
        MainClass.getInstance().myStageManager.GoToLogin();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LoggedInUser.setText("Logged in as: "+MainClass.getInstance().mySystemManager.GetLoggedInUser());
        
        // Setup hires table
        TableColumn<Hire, String> cell_ID = new TableColumn<>("Hire ID");
        cell_ID.setPrefWidth(32);
        cell_ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        
        TableColumn<Hire, String> cell_Start = new TableColumn<>("Start Date");
        cell_Start.setPrefWidth(75);
        cell_Start.setCellValueFactory(new PropertyValueFactory<>("StartDate"));
        
        TableColumn<Hire, String> cell_End = new TableColumn<>("End Date");
        cell_End.setPrefWidth(75);
        cell_End.setCellValueFactory(new PropertyValueFactory<>("EndDate"));
        
        TableColumn<Hire, String> cell_Customer = new TableColumn<>("Customer ID");
        cell_Customer.setPrefWidth(32);
        cell_Customer.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
        
        TableColumn<Hire, String> cell_User = new TableColumn<>("User ID");
        cell_User.setPrefWidth(32);
        cell_User.setCellValueFactory(new PropertyValueFactory<>("UserID"));
        
        TableColumn<Hire, String> cell_Licence = new TableColumn<>("Licence Number");
        cell_Licence.setPrefWidth(32);
        cell_Licence.setCellValueFactory(new PropertyValueFactory<>("LicenceNumber"));
        
        ObservableList<Hire> HiresList;
        HiresList = MainClass.getInstance().mySystemManager.getAllHiresList();
        
        table.setItems(HiresList);
        table.getColumns().addAll(cell_ID, cell_Start, cell_End, cell_Customer, cell_User, cell_Licence);
        
        
    }
    
    @FXML
    private void GoBack(ActionEvent event) {
        MainClass.getInstance().myStageManager.GoToWindow("Home.fxml");
    }
    
    @FXML
    void DeleteHire(ActionEvent event) {
        
        int selectedHireID = table.getSelectionModel().getSelectedItem().ID.getValue();
        MainClass.getInstance().mySystemManager.DeleteHire(selectedHireID);
        // Update list
        ObservableList<Hire> HireList;
        HireList = MainClass.getInstance().mySystemManager.getAllHiresList();
        table.setItems(HireList);

    }
    
    @FXML
    void EditHire(ActionEvent event) {
        MainClass.getInstance().mySystemManager.selectedHire = table.getSelectionModel().getSelectedItem();
        MainClass.getInstance().mySystemManager.setIsUpdate(true);
        MainClass.getInstance().myStageManager.GoToWindow("EditHire.fxml");
    }
    
}
