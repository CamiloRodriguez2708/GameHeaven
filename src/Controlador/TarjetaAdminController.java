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
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.shape.Rectangle;
import Controlador.PantallaGestionController;
import java.io.FileInputStream;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.File;

/**
 * FXML Controller class
 *
 * @author USUARIO
 */
public class TarjetaAdminController implements Initializable {

    /**
     * Initializes the controller class.
     */
    PantallaGestionController control;
    
    String buscar;
    
    @FXML private ImageView Miniatura;
    
    @FXML private Label nombre;
    
    @FXML private ComboBox<String> plataforma;
    
    @FXML private TextField stock, precio;
    
    @FXML private Button extra;
    
    @FXML private ContextMenu menu;
    
    @FXML private MenuItem itemEliminar, itemEditar, itemDetalles;
    
    private nodoVideojuego juego;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        extra.setOnMouseClicked(e -> {
        if (e.getButton() == MouseButton.PRIMARY) {

            if (!menu.isShowing()) {
                menu.show(extra, Side.BOTTOM, 0,0);
            } else {
                menu.hide();
            }

            e.consume(); 
        }
    });
        Rectangle clip = new Rectangle();
         
         
    clip.widthProperty().bind(Miniatura.fitWidthProperty());
    clip.heightProperty().bind(Miniatura.fitHeightProperty());
    Miniatura.setPreserveRatio(false); 

    
    clip.setArcWidth(30);
    clip.setArcHeight(30);

    Miniatura.setClip(clip);
    nombre.setTextOverrun(OverrunStyle.ELLIPSIS);
    nombre.setWrapText(false); 
    nombre.setMaxWidth(200);   
    
    plataforma.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
    if (newVal.intValue() != -1 && juego != null) {
        stock.setText(juego.stockDigital.get(newVal.intValue()).toString());
    }
    });
    
    itemEliminar.setOnAction(e -> {
    eliminar();
    });
    
    itemDetalles.setOnAction(e -> {
    control.abrirDetalles(juego);
    });
    menu.setOnShowing(e -> {
    String css = getClass().getResource("/Styles/ContextMenu.css").toExternalForm();
    
    if (!menu.getScene().getStylesheets().contains(css)) {
        menu.getScene().getStylesheets().add(css);
    }
});
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
        
        precio.setText("$" + (int) juego.precioDigital);
        nombre.setText(juego.nombre);
        plataforma.getItems().addAll(juego.plataforma);
        plataforma.getSelectionModel().selectFirst();
        stock.setText(juego.stockDigital.get(plataforma.getSelectionModel().getSelectedIndex()).toString());
    }
    
    public void setControl(PantallaGestionController control){
        this.control = control;
    }
    
    public void setBuscar(String buscar){
        this.buscar = buscar;
    }
    
    public void eliminar(){
        eliminarCarpeta();
        Sistema.listaJuegos.eliminar(juego.id);
        if(buscar != null){
            control.filtrarTodo(buscar);
        }
        else{
            control.mostrarTodos();
        }
        Sistema.listaJuegos.guardarArchivo();
    }
    
    public void eliminarCarpeta(){
        File portada = new File(System.getProperty("user.dir")+ "/src/datos/imagenes/"+juego.portada);
        File cap1 = new File(System.getProperty("user.dir")+ "/src/datos/imagenes/"+juego.capturas.get(0));
        File cap2 = new File(System.getProperty("user.dir")+ "/src/datos/imagenes/"+juego.capturas.get(1));
        File cap3 = new File(System.getProperty("user.dir")+ "/src/datos/imagenes/"+juego.capturas.get(2));
        File carpeta = new File(System.getProperty("user.dir")+ "/src/datos/imagenes/" + juego.nombre);
        
        Miniatura.setImage(null);
        portada.delete();
        cap1.delete();
        cap2.delete();
        cap3.delete();
        carpeta.delete();
    
}   
    
    
}

