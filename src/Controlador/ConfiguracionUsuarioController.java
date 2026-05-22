/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Camilo Rodriguez
 */
public class ConfiguracionUsuarioController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML private AnchorPane informacionPersonal, datosDeCuenta, seguridad, metodoDePago, dirrecion;
    
    @FXML
    private Text txtUsuario, txtAccion, carritoTxt, mensajeCarritoTxt;
    
    @FXML
    private ComboBox<String> ComboBoxT, tipoDoc, genero;
    
    @FXML
    private TextField nombreR, documento, nombreU, correo, telefono, contrasena, banco, metodo, cuenta, codigo, departamento, municipio, dirrecciontxt, buscar;
    
    @FXML
    private DatePicker nacimiento;
    
    @FXML private Pane overlay;
    @FXML private AnchorPane popupMensaje;
    @FXML private AnchorPane popupCarrito;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        informacionPersonal.setVisible(true);
        datosDeCuenta.setVisible(false);
        seguridad.setVisible(false);
        metodoDePago.setVisible(false);
        dirrecion.setVisible(false);
        actualizarUsuario();
        carritoTxt.setText(String.valueOf(Sesion.carrito.cantidadJuegos()));
        
        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list, "Catalogo", "Historial", "Favoritos");
        ComboBoxT.getItems().setAll(list);
        actualizarUsuario();
        
        nombreR.setText(Sesion.usuarioActual.nombreR);
        documento.setText(Sesion.usuarioActual.numD);
        nombreU.setText(Sesion.usuarioActual.nombreU);
        correo.setText(Sesion.usuarioActual.correo);
        telefono.setText(Sesion.usuarioActual.telefono);
        contrasena.setText(Sesion.usuarioActual.contrasena);
        banco.setText(Sesion.usuarioActual.nomBanco);
        metodo.setText(Sesion.usuarioActual.tipoMetodoP);
        cuenta.setText(String.valueOf(Sesion.usuarioActual.numTarjeta));
        codigo.setText(Sesion.usuarioActual.codigoP);
        departamento.setText(Sesion.usuarioActual.departamento);
        municipio.setText(Sesion.usuarioActual.municipio);
        dirrecciontxt.setText(Sesion.usuarioActual.dirrecion);
        
        tipoDoc.setPromptText(Sesion.usuarioActual.tipoD);
        tipoDoc.getItems().add("C.C");
        tipoDoc.getItems().add("T.I");
        
        genero.setPromptText(Sesion.usuarioActual.genero);
        genero.getItems().add("M");
        genero.getItems().add("F");
        
        nacimiento.setPromptText(Sesion.usuarioActual.fechaNacimiento.toString());
        
        Platform.runLater(()->{
           Stage stage = (Stage) overlay.getScene().getWindow();
           stage.setOnCloseRequest(event -> {

            Sesion.carrito.vaciar();
           });
       });
        ComboBoxT.getSelectionModel().selectedIndexProperty().addListener((obj, oldVal, newVal)->{
            if(newVal.intValue() == 2){
                try{
                Parent root = FXMLLoader.load(getClass().getResource("/Vista/PantallaFavoritos.fxml"));
                Stage stage = (Stage) ComboBoxT.getScene().getWindow();
                Scene scene = stage.getScene();
                scene.setRoot(root);
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
            if(newVal.intValue() == 1){
                try{
                Parent root = FXMLLoader.load(getClass().getResource("/Vista/PantallaFavoritos.fxml"));
                Stage stage = (Stage) ComboBoxT.getScene().getWindow();
                Scene scene = stage.getScene();
                scene.setRoot(root);
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
            if(newVal.intValue() == 0){
                try{
                Parent root = FXMLLoader.load(getClass().getResource("/Vista/PaginaPrincipal.fxml"));
                Stage stage = (Stage) ComboBoxT.getScene().getWindow();
                Scene scene = stage.getScene();
                scene.setRoot(root);
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
        });
        buscar.textProperty().addListener((obj, oldVal, newVal) ->{
            if(newVal != ""){
                Sesion.buscar = newVal;
                Buscar();
            }
            else{
                
            }
        });
    }    
    
    public void infoP (MouseEvent event) {
        informacionPersonal.setVisible(true);
        datosDeCuenta.setVisible(false);
        seguridad.setVisible(false);
        metodoDePago.setVisible(false);
        dirrecion.setVisible(false);
    }
    
    public void datosC (MouseEvent event) {
        informacionPersonal.setVisible(false);
        datosDeCuenta.setVisible(true);
        seguridad.setVisible(false);
        metodoDePago.setVisible(false);
        dirrecion.setVisible(false);
    }
    
    public void seguridad (MouseEvent event) {
        informacionPersonal.setVisible(false);
        datosDeCuenta.setVisible(false);
        seguridad.setVisible(true);
        metodoDePago.setVisible(false);
        dirrecion.setVisible(false);
    }
    
    public void metodoP (MouseEvent event) {
        informacionPersonal.setVisible(false);
        datosDeCuenta.setVisible(false);
        seguridad.setVisible(false);
        metodoDePago.setVisible(true);
        dirrecion.setVisible(false);
    }
    
    public void dirrecion (MouseEvent event) {
        informacionPersonal.setVisible(false);
        datosDeCuenta.setVisible(false);
        seguridad.setVisible(false);
        metodoDePago.setVisible(false);
        dirrecion.setVisible(true);
    }
    
    @FXML
    public void principal(MouseEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Vista/paginaPrincipal.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = stage.getScene();
        scene.setRoot(root);
    }
    
    @FXML
    public void abrirCarrito(MouseEvent event) {
        actualizarPopupCarrito();
        popupCarrito.setVisible(!popupCarrito.isVisible());
    }
    
    @FXML
    public void verCarrito(MouseEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Vista/PantallaCarrito.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = stage.getScene();
        scene.setRoot(root);
    }
    
    private void actualizarPopupCarrito() {
        int cantidad = Sesion.carrito.cantidadJuegos();
        carritoTxt.setText(String.valueOf(cantidad));
        if (cantidad == 1) {
            mensajeCarritoTxt.setText("Hay 1 elemento\nen el carrito");
        } else {
            mensajeCarritoTxt.setText("Hay " + cantidad + " elementos\nen el carrito");
        }
    }
    
    @FXML
    
    private void actualizarUsuario() {
        if (Sesion.usuarioActual == null) {
            txtUsuario.setText("Invitado");
            txtAccion.setText("Ingresar");
        } else {
            txtUsuario.setText(Sesion.usuarioActual.nombreU);
            txtAccion.setText("Volver");
        }
    }
    
    public void guardar(){
        
        Sesion.usuarioActual.nombreR = nombreR.getText();
        Sesion.usuarioActual.numD = documento.getText();
        Sesion.usuarioActual.nombreU = nombreU.getText();
        if(Sesion.lista.buscarCorr(correo.getText()) != null && correo.getText().equals(Sesion.lista.buscarCorr(correo.getText()).correo)){
            abrirMensaje("El correo ya se encuentra registrado", "cerrar");
            correo.setText(Sesion.usuarioActual.correo);
        }
        else{
           Sesion.usuarioActual.correo = correo.getText(); 
        }
        
        Sesion.usuarioActual.telefono = telefono.getText();
        Sesion.usuarioActual.contrasena = contrasena.getText();
        Sesion.usuarioActual.nomBanco = banco.getText();
        Sesion.usuarioActual.tipoMetodoP = metodo.getText();
        Sesion.usuarioActual.numTarjeta = Integer.parseInt(cuenta.getText());
        Sesion.usuarioActual.codigoP = codigo.getText();
        Sesion.usuarioActual.departamento = departamento.getText();
        Sesion.usuarioActual.municipio = municipio.getText();
        Sesion.usuarioActual.dirrecion = dirrecciontxt.getText();
        
        if(tipoDoc.getValue() == null){
            
        }
        else{
            Sesion.usuarioActual.tipoD = tipoDoc.getValue();
        }
        
        if(genero.getValue() == null){
            
        }
        else{
        Sesion.usuarioActual.genero = genero.getValue();
        }
        
        if(nacimiento.getValue() == null){
            
        }
        else{
        Sesion.usuarioActual.fechaNacimiento = java.sql.Date.valueOf(nacimiento.getValue());
        }
        
        Sesion.lista.modificar(Sesion.usuarioActual);
        Sesion.lista.guardarArchivo();
    }
    
    public void borrar(Event event) throws IOException{
        Sesion.lista.eliminar(String.valueOf(Sesion.usuarioActual.iD));
        Sesion.usuarioActual = null;
        Sesion.lista.guardarArchivo();
        Parent root = FXMLLoader.load(getClass().getResource("/Vista/paginaPrincipal.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = stage.getScene();
        scene.setRoot(root);
    }
    
    public void cerrar(Event event) throws IOException{
        Sesion.usuarioActual = null;
        Sesion.carrito.vaciar();
        Parent root = FXMLLoader.load(getClass().getResource("/Vista/paginaPrincipal.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = stage.getScene();
        scene.setRoot(root);
        
    }
    
    public void abrirMensaje(String mensaje, String accion) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/Alerta.fxml"));
        Parent root = loader.load();

        AlertaController controller = loader.getController();
        controller.cargarDatos(mensaje, accion);
        controller.setOverlay(overlay);
        

        popupMensaje.getChildren().setAll(root);
        overlay.setVisible(true);

    } catch (Exception e) {
        e.printStackTrace();
    }
    }
    public void Buscar() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Vista/PantallaBuscar.fxml"));
            Stage stage = (Stage) buscar.getScene().getWindow();
            Scene scene = stage.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
