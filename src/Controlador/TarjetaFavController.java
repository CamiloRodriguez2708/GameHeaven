/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controlador;

import Modelo.nodoVideojuego;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author USUARIO
 */
public class TarjetaFavController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML private ImageView portada, Fav, noFav;
    @FXML private Text precio, nombre;
    
    private nodoVideojuego juego;
    
    boolean favoritos = false;
    boolean listaFavoritosVacia = false;
    
    private PantallaFavoritosController controlador;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        noFav.setVisible(true);
        Fav.setVisible(false);
        Rectangle clip = new Rectangle();
         
         
    clip.widthProperty().bind(portada.fitWidthProperty());
    clip.heightProperty().bind(portada.fitHeightProperty());

    
    clip.setArcWidth(25);
    clip.setArcHeight(25);

    portada.setClip(clip);
    }
    public void anadirDatos(nodoVideojuego juego){
        try {
            this.juego = juego;
            InputStream is = new FileInputStream(System.getProperty("user.dir")+ "/src/datos/imagenes/" + juego.portada);
            
            if (is != null) {
                portada.setImage(new Image(is));
            }
        } catch (Exception e) {
            System.out.println("Error cargando imagen");
            System.out.println(System.getProperty("user.dir")+ "/src/datos/imagenes/" + juego.portada);
        }
        
        precio.setText("$" + (int) juego.precioDigital);
        nombre.setText(juego.nombre);
        
        buscarFavoritoID();
    }
    
    public void getControlador(PantallaFavoritosController aux){
        this.controlador = aux;
    }
    
    @FXML
    public void entrar(MouseEvent event) throws Exception{
        if(Sesion.usuarioActual != null){
        Sistema.seleccionado = this.juego;
        System.out.println("JUEGO SELECCIONADO: "+ Sistema.seleccionado.nombre);
        Parent root = FXMLLoader.load(getClass().getResource("/Vista/PantallaCompra.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = stage.getScene();
        scene.setRoot(root);
        }
        else{
            controlador.abrirMensaje("POR FAVOR INICIE SESIÓN PRIMERO", "cerrar");
        }
    }
    
    @FXML
    private void AgregarFavoritos(MouseEvent event){
        try{
        if(favoritos == false){
            if(listaFavoritosVacia){
                Sesion.usuarioActual.listaDeseados.remove(0);
                listaFavoritosVacia = false;
            }
            Sesion.usuarioActual.listaDeseados.add(String.valueOf(juego.id));
            favoritos= true;
            Fav.setVisible(true);
            noFav.setVisible(false);
            Sesion.lista.guardarArchivo();
        }
        else{
            Sesion.usuarioActual.listaDeseados.remove(buscarFavoritoID());
            favoritos = false;
            Fav.setVisible(false);
            noFav.setVisible(true);
            if(Sesion.usuarioActual.listaDeseados.isEmpty()){
                Sesion.usuarioActual.listaDeseados.add("#");
                listaFavoritosVacia = true;

            }
            Sesion.lista.guardarArchivo();
        }  
    }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private int buscarFavoritoID(){
        for(int i=0; i< Sesion.usuarioActual.listaDeseados.size(); i++){
            if(!Sesion.usuarioActual.listaDeseados.get(i).equals("#")){
            if(juego.id == Integer.parseInt(Sesion.usuarioActual.listaDeseados.get(i))){
                favoritos = true;
                Fav.setVisible(true);
                noFav.setVisible(false);
                return i;
            }
            }
            else{
                favoritos = false;
                listaFavoritosVacia = true;
                noFav.setVisible(true);
                Fav.setVisible(false);
            }
            
        }
        return -1;
    }
}
