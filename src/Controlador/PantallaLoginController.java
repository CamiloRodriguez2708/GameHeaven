/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controlador;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import Controlador.Sesion;
import Controlador.listaUsuarios;
import javafx.animation.FadeTransition;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author USUARIO
 */
public class PantallaLoginController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private ComboBox<String> ComboBoxU;
    
    @FXML
    private TextField TextFieldU;
    
    @FXML
    private PasswordField PasswordFieldP;
    
    
    private AnchorPane PanelI, PanelC;
    
    listaUsuarios lista = new listaUsuarios();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list, new String[]{"Usuario","Administrador"});
        
        ComboBoxU.getItems().addAll(list);
        

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

    fadeOut.play();
}
     
    public void signUp(MouseEvent event) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("/Vista/PantallaSignUp.fxml"));

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

    fadeOut.play();
}

     public void sesion(){
         Sesion.usuarioActual = lista.iniciarSesion(TextFieldU.getText(), PasswordFieldP.getText());
     }
     
}
