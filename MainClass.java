/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 *
 * @author aaronedge
 */
public class MainClass extends Application {
    
    public StageManager myStageManager;
    public SystemManager mySystemManager;
   
    private static MainClass instance;

    public MainClass() {
        instance = this;
    }

    public static MainClass getInstance() {
        return instance;
    }
    
    @Override
    public void start(Stage primaryStage) {
        myStageManager = new StageManager();
        myStageManager.InitManager(primaryStage);
        mySystemManager = new SystemManager();
        mySystemManager.doConnect();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
