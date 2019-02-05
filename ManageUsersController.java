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
public class ManageUsersController implements Initializable{
    @FXML private Label LoggedInUser;
    
    @FXML
    private TableView<User> table;
    
    @FXML
    private void LogOut(ActionEvent event) 
    {
        MainClass.getInstance().myStageManager.GoToLogin();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        LoggedInUser.setText("Logged in as: "+MainClass.getInstance().mySystemManager.GetLoggedInUser());
        
        TableColumn<User, String> cell_ID = new TableColumn<>("User ID");
        cell_ID.setPrefWidth(32);
        cell_ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        
        TableColumn<User, String> cell_UserName = new TableColumn<>("User Name");
        cell_UserName.setPrefWidth(100);
        cell_UserName.setCellValueFactory(new PropertyValueFactory<>("UserName"));
        
        TableColumn<User, String> cell_Permission = new TableColumn<>("Permission Level");
        cell_Permission.setPrefWidth(100);
        cell_Permission.setCellValueFactory(new PropertyValueFactory<>("PermissionLevel"));
        
        ObservableList<User> UserList;
        UserList = MainClass.getInstance().mySystemManager.getAllUsersList();
        
        table.setItems(UserList);
        
        table.getColumns().addAll(cell_ID, cell_UserName, cell_Permission);
    }
    
    @FXML
    private void GoBack(ActionEvent event) {
        MainClass.getInstance().myStageManager.GoToWindow("Home.fxml");
    }
    @FXML
    private void AddNewUser(ActionEvent event) {
        MainClass.getInstance().myStageManager.GoToWindow("AddNewUser.fxml");
    }
    
    @FXML
    void DeleteUser(ActionEvent event) {
        
        int selectedUserID = table.getSelectionModel().getSelectedItem().ID.getValue();
        MainClass.getInstance().mySystemManager.DeleteUser(selectedUserID);
        // Update list
        ObservableList<User> UserList;
        UserList = MainClass.getInstance().mySystemManager.getAllUsersList();
        table.setItems(UserList);

    }
    
    @FXML
    void EditUser(ActionEvent event) {
        MainClass.getInstance().mySystemManager.selectedUser = table.getSelectionModel().getSelectedItem();
        MainClass.getInstance().mySystemManager.setIsUpdate(true);
        MainClass.getInstance().myStageManager.GoToWindow("AddNewUser.fxml");
    }
}
