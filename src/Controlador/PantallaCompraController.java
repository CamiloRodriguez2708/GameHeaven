/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.nodoVideojuego;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Camilo Rodriguez
 */
public class PantallaCompraController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private nodoVideojuego juego = Sistema.seleccionado;
    boolean favoritos = false;
    @FXML ComboBox<String> plataformaCB, ComboBoxT;
    @FXML ImageView ImagePrincipal,Image1,Image2,Image3;
    @FXML Text nombreTxt, descripcionTxt, precioDTxt, precioFTxt, stockDTxt, stockFTxt, carritoTxt, txtUsuario, txtAccion;
    @FXML HBox etiquetasHbox;
    @FXML TextField buscar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        CargarJuego();
        carritoTxt.setText(String.valueOf(Sesion.carrito.cantidadJuegos()));
        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list, "Catalogo", "Historial", "Favoritos");
        ComboBoxT.getItems().setAll(list);
        actualizarUsuario();
        Rectangle clip0 = new Rectangle();
        Rectangle clip1 = new Rectangle();
        Rectangle clip2 = new Rectangle();
        Rectangle clip3 = new Rectangle();
        
        clip0.widthProperty().bind(ImagePrincipal.fitWidthProperty());
        clip0.heightProperty().bind(ImagePrincipal.fitHeightProperty());
        ImagePrincipal.setPreserveRatio(false); 
        
        clip1.widthProperty().bind(Image1.fitWidthProperty());
        clip1.heightProperty().bind(Image1.fitHeightProperty());
        Image1.setPreserveRatio(false); 
        
        clip2.widthProperty().bind(Image2.fitWidthProperty());
        clip2.heightProperty().bind(Image2.fitHeightProperty());
        Image2.setPreserveRatio(false); 
        
        clip3.widthProperty().bind(Image3.fitWidthProperty());
        clip3.heightProperty().bind(Image3.fitHeightProperty());
        Image3.setPreserveRatio(false); 

    
        clip0.setArcWidth(30);
        clip0.setArcHeight(30);
        
        clip1.setArcWidth(30);
        clip1.setArcHeight(30);
        
        clip2.setArcWidth(30);
        clip2.setArcHeight(30);
        
        clip3.setArcWidth(30);
        clip3.setArcHeight(30);
        
        ImagePrincipal.setClip(clip0);
        Image1.setClip(clip1);
        Image2.setClip(clip2);
        Image3.setClip(clip3);
        buscarFavoritoID();
        
        buscar.textProperty().addListener((obj, oldVal, newVal) ->{
            if(newVal != ""){
                Sesion.buscar = newVal;
                Buscar();
            }
            else{
                
            }
        });
        
        plataformaCB.getSelectionModel().selectedIndexProperty().addListener((obj,oldVal,newVal)->{
            stockFTxt.setText("Stock: " +juego.stockFisico.get(plataformaCB.getSelectionModel().getSelectedIndex()));
            stockDTxt.setText("Stock: " +juego.stockDigital.get(plataformaCB.getSelectionModel().getSelectedIndex()));
        });
        
        Image1.setOnMouseClicked(e -> {
            CambiarImagen(Image1);
});
        Image2.setOnMouseClicked(e -> {
            CambiarImagen(Image2);
});
        Image3.setOnMouseClicked(e -> {
            CambiarImagen(Image3);
});
        Platform.runLater(()->{
           Stage stage = (Stage) ImagePrincipal.getScene().getWindow();
           stage.setOnCloseRequest(event -> {

            Sesion.carrito.vaciar();
           });
       });
   
    } 
    private void AgregarFavoritos(){
        if(favoritos == false){
            Sesion.usuarioActual.listaDeseados.add(String.valueOf(juego.id));
        }
        else{
            Sesion.usuarioActual.listaDeseados.remove(buscarFavoritoID());
            
        }
    }
    
    private int buscarFavoritoID(){
        for(int i=0; i< Sesion.usuarioActual.listaDeseados.size(); i++){
            if(!Sesion.usuarioActual.listaDeseados.get(i).equals("#")){
            if(juego.id == Integer.parseInt(Sesion.usuarioActual.listaDeseados.get(i))){
                favoritos = true;
                return i;
            }
            }
            else{
                favoritos = false;
            }
        }
        return -1;
    }
    @FXML
    private void AgregarFisico(MouseEvent event){
        try{
            if(juego.stockFisico.get(plataformaCB.getSelectionModel().getSelectedIndex()) != 0){
        juego.stockFisico.set(plataformaCB.getSelectionModel().getSelectedIndex(),juego.stockFisico.get(plataformaCB.getSelectionModel().getSelectedIndex()) - 1);
        nodoVideojuego insertar = new nodoVideojuego( juego.id, juego.nombre, new ArrayList<String>(juego.plataforma), "Fisico", juego.categoria,
                                                      juego.etiquetas, juego.descripcion, juego.peso, juego.requisitosMin,
                                                      juego.requisitosRec, juego.idioma, juego.fechaLanzamiento, juego.portada, 
                                                      juego.capturas, juego.precioDigital, juego.precioFisico, juego.stockDigital, 
                                                      juego.stockFisico, juego.calificaciones);
        insertar.plataforma.clear();
        insertar.plataforma.add(plataformaCB.getSelectionModel().getSelectedItem());
        Sesion.carrito.agregarJuego(insertar);
        carritoTxt.setText(String.valueOf(Sesion.carrito.cantidadJuegos()));
        stockFTxt.setText("Stock: " +juego.stockFisico.get(plataformaCB.getSelectionModel().getSelectedIndex()));
        Sistema.listaJuegos.guardarArchivo();
            }
            else{
                
            }
        }
        
        catch(Exception e){
            e.printStackTrace();
        }
        
    }
    @FXML
    private void AgregarDigital(MouseEvent event){
        try{
            if(juego.stockDigital.get(plataformaCB.getSelectionModel().getSelectedIndex()) != 0){
        juego.stockDigital.set(plataformaCB.getSelectionModel().getSelectedIndex(),juego.stockDigital.get(plataformaCB.getSelectionModel().getSelectedIndex()) - 1);
        nodoVideojuego insertar = new nodoVideojuego( juego.id, juego.nombre, new ArrayList<String>(juego.plataforma), "Digital", juego.categoria,
                                                      juego.etiquetas, juego.descripcion, juego.peso, juego.requisitosMin,
                                                      juego.requisitosRec, juego.idioma, juego.fechaLanzamiento, juego.portada, 
                                                      juego.capturas, juego.precioDigital, juego.precioFisico, juego.stockDigital, 
                                                      juego.stockFisico, juego.calificaciones);
        insertar.plataforma.clear();
        insertar.plataforma.add(plataformaCB.getSelectionModel().getSelectedItem());
        Sesion.carrito.agregarJuego(insertar);
        carritoTxt.setText(String.valueOf(Sesion.carrito.cantidadJuegos()));
        stockDTxt.setText("Stock: " +juego.stockDigital.get(plataformaCB.getSelectionModel().getSelectedIndex()));
        Sistema.listaJuegos.guardarArchivo();    
            }
            else{
                
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void CargarJuego(){
        ImagePrincipal.setImage(new Image(new File(System.getProperty("user.dir")+ "/src/datos/imagenes/"+ juego.portada).toURI().toString()));
        Image1.setImage(new Image(new File(System.getProperty("user.dir")+ "/src/datos/imagenes/"+ juego.capturas.get(0)).toURI().toString()));
        Image2.setImage(new Image(new File(System.getProperty("user.dir")+ "/src/datos/imagenes/"+ juego.capturas.get(1)).toURI().toString()));
        Image3.setImage(new Image(new File(System.getProperty("user.dir")+ "/src/datos/imagenes/"+ juego.capturas.get(2)).toURI().toString()));
        
        nombreTxt.setText(juego.nombre);
        descripcionTxt.setText(juego.descripcion + "\n"+ "Requisitos minimos: " + juego.requisitosMin+ "\n"+"Requisitos minimos: " + juego.requisitosRec);
        plataformaCB.getItems().setAll(juego.plataforma);
        plataformaCB.getSelectionModel().select(0);
        precioDTxt.setText("Precio Digital: $" + (int) juego.precioDigital);
        stockDTxt.setText("Stock: " + juego.stockDigital.get(0));
        precioFTxt.setText("Precio Físico: $" + (int) juego.precioFisico);
        stockFTxt.setText("Stock: " + juego.stockFisico.get(0));
        
        for (String etiqueta : juego.etiquetas) {

    Button btnTag = new Button(etiqueta);

    btnTag.setStyle("""
        -fx-background-color: #EBDFCC;
        -fx-background-radius: 20;
        -fx-text-fill: black;
        -fx-font-weight: bold;
        """);

    etiquetasHbox.getChildren().add(btnTag);
}
    }
    
    @FXML
    public void volver(MouseEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Vista/paginaPrincipal.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = stage.getScene();
        scene.setRoot(root);
    }
    @FXML
    public void Login(MouseEvent event) throws Exception {
        if (Sesion.usuarioActual != null) {
            Parent root = FXMLLoader.load(getClass().getResource("/Vista/ConfiguracionUsuario.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = stage.getScene();
            scene.setRoot(root);
            return;
        }
        Parent root = FXMLLoader.load(getClass().getResource("/Vista/PantallaLogin.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = stage.getScene();
        scene.setRoot(root);
    }
    private void actualizarUsuario() {
        if (Sesion.usuarioActual == null) {
            txtUsuario.setText("Invitado");
            txtAccion.setText("Ingresar");
        } else {
            txtUsuario.setText(Sesion.usuarioActual.nombreU);
            txtAccion.setText("Configuración");
        }
    }
    public void Buscar() {
        try{
        Parent root = FXMLLoader.load(getClass().getResource("/Vista/PantallaBuscar.fxml"));
        Stage stage = (Stage) buscar.getScene().getWindow();
        Scene scene = stage.getScene();
        scene.setRoot(root);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    
    private void CambiarImagen(ImageView miniatura){

    if(miniatura.getImage() == null || ImagePrincipal.getImage() == null){
        return;
    }

    Image temp = miniatura.getImage();

    miniatura.setImage(ImagePrincipal.getImage());

    ImagePrincipal.setImage(temp);
}
}
