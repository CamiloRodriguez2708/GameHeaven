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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void cargarDatos(String mensaje, PantallaSignUpController control){
        this.control = control;
        this.mensaje.setText(mensaje);
    }
    
    public void cerrar(){
        control.cerrar();
    }
}
