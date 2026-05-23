/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controlador;

import Modelo.nodoVideojuego;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import java.io.FileInputStream;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Camilo Rodriguez
 */
public class TarjetaHistorialController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML private ImageView Miniatura;
    
    @FXML private Label nombre;
    
    @FXML private Text Fecha;
    
    private nodoVideojuego juego;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
        Rectangle clip = new Rectangle();
         
         
    clip.widthProperty().bind(Miniatura.fitWidthProperty());
    clip.heightProperty().bind(Miniatura.fitHeightProperty());
    Miniatura.setPreserveRatio(false); 

    
    clip.setArcWidth(30);
    clip.setArcHeight(30);

    Miniatura.setClip(clip);
     
    
    }
    
    public void anadirDatos(nodoVideojuego juego){
        this.juego =juego;
        try {
       
            InputStream is = new FileInputStream(System.getProperty("user.dir")+ "/src/datos/imagenes/"+juego.portada);
            if (is != null) {
                Miniatura.setImage(new Image(is));
            }
        } catch (Exception e) {
            System.out.println("Error cargando imagen");
        }
        nombre.setText(juego.nombre +" ("+juego.plataforma.get(0)+") "+"("+juego.edicion+")");
        Fecha.setText(juego.fechaLanzamiento);
    }
}
    
    


