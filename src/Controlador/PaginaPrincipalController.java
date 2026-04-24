/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controlador;

import javafx.scene.input.MouseEvent;
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
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author USUARIO
 */
public class PaginaPrincipalController implements Initializable {

    @FXML
    private ComboBox<String> ComboBoxT;
    
    @FXML
    private Slider SliderP;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list, new String[]{"Tienda","Historial","Favoritos"});
        
        ComboBoxT.getItems().addAll(list);
        
        
    }
    @FXML
    public void Login(MouseEvent event) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("/Vista/PantallaLogin.fxml"));
    
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.setFullScreen(true);
    stage.setFullScreenExitHint("");
    stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
    
    stage.show();
}
    
}
