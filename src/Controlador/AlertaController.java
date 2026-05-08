/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import Controlador.PantallaSignUpController;
import java.io.IOException;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author USUARIO
 */
public class AlertaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private Text mensaje;
    PantallaSignUpController control;
    String accion;
    @FXML
    private Pane Overlay;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void cargarDatos(String mensaje, String accion){
        
        this.mensaje.setText(mensaje);
        this.accion = accion;
    }
    
    public void setOverlay(Pane Overlay){
        this.Overlay = Overlay;
    }
    
    public void cerrar(Event event){
        if(accion == "cerrar"){
            Overlay.setVisible(false);
        }
        
        else{
            try{
        Parent root = FXMLLoader.load(getClass().getResource(accion));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = stage.getScene();
        scene.setRoot(root);
            }
            catch(IOException e){
                e.printStackTrace();
    }
        }
        
    }
}
