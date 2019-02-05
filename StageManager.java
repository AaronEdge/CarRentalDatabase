
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Aaron Edge
 */
public class StageManager {
    
    private String previousPage = "Login.fxml";
    private Stage stage;
    
    public void InitManager(Stage primaryStage)
    {
        primaryStage.initStyle(StageStyle.UNIFIED);
        try {
            //Setup Window
            stage = primaryStage;
            primaryStage.getIcons().add(new Image(MainClass.class.getResourceAsStream("Assets/HireFromUs_Black_Icon.png")));
            primaryStage.setTitle("Hire_From_Us");
            
            GoToLogin();
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void GoBack(){
        try {
            replaceSceneContent(previousPage);
        } catch (Exception ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void GoToWindow(String newWindowName){
        try {
            //Error here
            previousPage = stage.getScene().getRoot().toString();
            replaceSceneContent(newWindowName);
        } catch (Exception ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void GoToLogin() {
        try {
            replaceSceneContent("Login.fxml");
        } catch (Exception ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private Parent replaceSceneContent(String fxml) throws Exception {
        Parent page = (Parent) FXMLLoader.load(MainClass.class.getResource(fxml), null, new JavaFXBuilderFactory());
        Scene scene = stage.getScene();
        if (scene == null) {
            scene = new Scene(page, 800, 600);
            stage.setScene(scene);
        } else {
            stage.getScene().setRoot(page);
        }
        stage.sizeToScene();
        return page;
    }
    
    public Stage GetStage() {
        return stage;
    }
}
