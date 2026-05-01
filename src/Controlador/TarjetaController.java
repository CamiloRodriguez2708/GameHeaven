/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controlador;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import Modelo.nodoVideojuego;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author USUARIO
 */
public class TarjetaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML private ImageView portada;
    @FXML private Text precio, nombre;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
         Rectangle clip = new Rectangle();
         
         
    clip.widthProperty().bind(portada.fitWidthProperty());
    clip.heightProperty().bind(portada.fitHeightProperty());

    
    clip.setArcWidth(25);
    clip.setArcHeight(25);

    portada.setClip(clip);
    
    
    }
    public void anadirDatos(nodoVideojuego juego){
        try {
       
            InputStream is = TarjetaController.class.getResourceAsStream("/Datos/Imagenes/" + juego.portada);
            if (is != null) {
                portada.setImage(new Image(is));
            }
        } catch (Exception e) {
            System.out.println("Error cargando imagen");
        }
        
        precio.setText("$" + (int) juego.precioDigital);
        nombre.setText(juego.nombre);
    }
}
