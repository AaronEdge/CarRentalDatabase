/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Aaron Edge
 */
public class MainController implements Initializable {

    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML private Label LoggedInUser;

    
    @FXML
    private void LogOut(ActionEvent event) 
    {
        MainClass.getInstance().myStageManager.GoToLogin();
    }
    
    private void GoDate(ActionEvent event)
    {
        MainClass.getInstance().myStageManager.GoToWindow("DateSelection.fxml");
    }
    
    
    private void GoCar(ActionEvent event)
    {
        MainClass.getInstance().myStageManager.GoToWindow("CarSelection.fxml");
    }

    private void GoHome(ActionEvent event)
    {
        MainClass.getInstance().myStageManager.GoToWindow("Home.fxml");
    }
}
