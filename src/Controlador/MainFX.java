/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

public class MainFX extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/Vista/paginaPrincipal.fxml"));
        primaryStage.setTitle("Game Heaven");
        primaryStage.setScene(new Scene(root, 1366, 768));
        primaryStage.show();
        
        primaryStage.setResizable(false);
        
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/Imagenes/logo.png")));
        
        
    }


    public static void main(String[] args) {
        launch(args);
    }
}
