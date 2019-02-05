import com.jfoenix.controls.JFXComboBox;
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
 * FXML Controller class
 *
 * @author Aaron Edge (113612)
 */
public class CarSelectionController implements Initializable {

    @FXML
    private JFXComboBox carMakeCombo;
    @FXML
    private JFXComboBox carModelCombo;
    
    
    @FXML
    private TableView<Car> table;
    
    @FXML private Label LoggedInUser;
    @FXML

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LoggedInUser.setText("Logged in as: "+MainClass.getInstance().mySystemManager.GetLoggedInUser());
        // Populate Make choicebox
        carMakeCombo.getItems().clear();
        String[] makeList = MainClass.getInstance().mySystemManager.GetAllMakes();
        for(int i = 0; i < makeList.length; i++) {
            carMakeCombo.getItems().addAll(makeList[i]);
        }
        
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
        
        table.setItems(CarList);
        
        table.getColumns().addAll(cell_Licence, cell_Make, cell_Model, cell_HasGPS, cell_Rate, cell_BodyType);
    }    

    @FXML
    private void LogOut(ActionEvent event) {
        MainClass.getInstance().myStageManager.GoToLogin();
    }

    @FXML
    private void GoBack(ActionEvent event) {
        MainClass.getInstance().myStageManager.GoToWindow("DateSelection.fxml");
    }

    @FXML
    private void GoNext(ActionEvent event) {
        
        if(table.getSelectionModel().isEmpty()) {
            AlertDialog diag = new AlertDialog(MainClass.getInstance().myStageManager.GetStage(),"Select a car", 0);
            diag.show();
        } else {
            MainClass.getInstance().mySystemManager.selectedCar = table.getSelectionModel().getSelectedItem();
            MainClass.getInstance().myStageManager.GoToWindow("RentalAgreement.fxml");
        }
    }
    
    @FXML
    private void SaveCarToDB(ActionEvent event) {
        
    }
    @FXML public void EnableModelChoice() {
        carModelCombo.setDisable(false);
        carModelCombo.getItems().clear();
        String[] makeList = MainClass.getInstance().mySystemManager.GetAllModels(carMakeCombo.getSelectionModel().getSelectedItem().toString());
        for(int i = 0; i < makeList.length; i++) {
            carModelCombo.getItems().addAll(makeList[i]);
        }
        
    }
    
}
