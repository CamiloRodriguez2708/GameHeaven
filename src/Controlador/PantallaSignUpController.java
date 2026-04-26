/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author USUARIO
 */
public class PantallaSignUpController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void Principal(MouseEvent event) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("/Vista/paginaPrincipal.fxml"));

    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

    Scene scene = stage.getScene();

    // Fade out
    FadeTransition fadeOut = new FadeTransition(Duration.millis(250), scene.getRoot());
    fadeOut.setFromValue(1);
    fadeOut.setToValue(0);

    fadeOut.setOnFinished(e -> {

        scene.setRoot(root); 

        FadeTransition fadeIn = new FadeTransition(Duration.millis(250), root);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    });
}}
