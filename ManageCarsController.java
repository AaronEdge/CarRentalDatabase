
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
 * @author Aaron Edge (112312)
 */
public class ManageCarsController implements Initializable{
    @FXML private Label LoggedInUser;
    
    @FXML
    private TableView<Car> table;
    
    @FXML
    private void LogOut(ActionEvent event) 
    {
        MainClass.getInstance().myStageManager.GoToLogin();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        LoggedInUser.setText("Logged in as: "+MainClass.getInstance().mySystemManager.GetLoggedInUser());
        
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
        
        TableColumn<Car, String> cell_Odometer = new TableColumn<>("Odometer Reading");
        cell_Odometer.setPrefWidth(150);
        cell_Odometer.setCellValueFactory(new PropertyValueFactory<>("OdometerReading"));
        
        ObservableList<Car> CarList;
        CarList = MainClass.getInstance().mySystemManager.getAllCarsList();
        
        table.setItems(CarList);
        
        table.getColumns().addAll(cell_Licence, cell_Make, cell_Model, cell_HasGPS, cell_Rate, cell_BodyType, cell_Odometer);
    }
    
    @FXML
    private void GoBack(ActionEvent event) {
        MainClass.getInstance().myStageManager.GoToWindow("Home.fxml");
    }
    
    @FXML
    private void AddNewCar(ActionEvent event) {
        MainClass.getInstance().myStageManager.GoToWindow("AddNewCar.fxml");
    }
    
    @FXML
    void DeleteCar(ActionEvent event) {
        
        String selectedCarLicence = table.getSelectionModel().getSelectedItem().LicenceNumber.getValue();
        MainClass.getInstance().mySystemManager.DeleteCar(selectedCarLicence);
        // Update list
        ObservableList<Car> CarList;
        CarList = MainClass.getInstance().mySystemManager.getAllCarsList();
        table.setItems(CarList);

    }
    
    @FXML
    void EditCar(ActionEvent event) {
        MainClass.getInstance().mySystemManager.selectedCar = table.getSelectionModel().getSelectedItem();
        MainClass.getInstance().mySystemManager.setIsUpdate(true);
        MainClass.getInstance().myStageManager.GoToWindow("AddNewCar.fxml");
    }
}
