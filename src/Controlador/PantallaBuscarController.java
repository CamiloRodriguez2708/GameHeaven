package Controlador;

import Modelo.nodoVideojuego;
import Controlador.TarjetaController;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;


public class PantallaBuscarController implements Initializable {

    @FXML
    private ComboBox<String> ComboBoxT;

    @FXML
    private Text txtUsuario, txtAccion, carritoTxt, mensajeCarritoTxt;
    
    @FXML
    private ScrollPane ScrollB, ScrollR;
    
    @FXML
    private TextField buscar;
    
    @FXML
    private HBox buscarPant, relPant;
    
    @FXML
    private AnchorPane popupCarrito;
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        buscar.setText(Sesion.buscar);
        carritoTxt.setText(String.valueOf(Sesion.carrito.cantidadJuegos()));
        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list, "Catalogo", "Historial", "Favoritos");
        ComboBoxT.getItems().setAll(list);
        actualizarUsuario();
        ScrollB.getStylesheets().add(getClass().getResource("/Styles/ScrollPaneB.css").toExternalForm());
        ScrollR.getStylesheets().add(getClass().getResource("/Styles/ScrollPaneR.css").toExternalForm());
        
        MostrarRelacionados(MostrarBusqueda(buscar.getText()));
        
        buscar.textProperty().addListener((obs, oldVal, newVal) ->{
            if(newVal != ""){
            MostrarRelacionados(MostrarBusqueda(newVal));
            }
            else{
                Regresar();
            }
        });
        Platform.runLater(()->{
            buscar.requestFocus();
            buscar.positionCaret(buscar.getText().length());
            Stage stage = (Stage) buscar.getScene().getWindow();
            stage.setOnCloseRequest(event -> {

            Sesion.carrito.vaciar();});
        });
        
        ComboBoxT.getSelectionModel().selectedIndexProperty().addListener((obj, oldVal, newVal)->{
            if(newVal.intValue() == 2){
                try{
                Parent root = FXMLLoader.load(getClass().getResource("/Vista/PantallaFavoritos.fxml"));
                Stage stage = (Stage) buscar.getScene().getWindow();
                Scene scene = stage.getScene();
                scene.setRoot(root);
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
            if(newVal.intValue() == 1){
                try{
                Parent root = FXMLLoader.load(getClass().getResource("/Vista/PantallaHistorial.fxml"));
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
                Stage stage = (Stage) buscar.getScene().getWindow();
                Scene scene = stage.getScene();
                scene.setRoot(root);
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
        });
           
           
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
    
    

    
    private List<nodoVideojuego> MostrarBusqueda(String nombre) {
        buscarPant.getChildren().clear(); 
        nodoVideojuego temp = Sistema.listaJuegos.inicio;
        List<nodoVideojuego> lista = new ArrayList<>();

        while (temp != null) {

            boolean nombreB = temp.nombre.toLowerCase().contains(nombre.toLowerCase());

           

            if (nombreB) {
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/Tarjeta.fxml"));
                    Parent tarjeta = loader.load();
                    TarjetaController controller = loader.getController();
                    controller.anadirDatos(temp);
                    buscarPant.getChildren().add(tarjeta);
                    HBox.setMargin(tarjeta, new javafx.geometry.Insets(10));
                    lista.add(temp);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
            else {
                
            }

            temp = temp.sig; 
        }
        return lista;
    }
    
    private void MostrarRelacionados (List <nodoVideojuego> lista) {
        relPant.getChildren().clear(); 
        nodoVideojuego temp = Sistema.listaJuegos.inicio;

        while (temp != null) {
            boolean relacionado = false;
            boolean nombre = false;
            
            for (int i = 0; i < lista.size(); i++) {
            if(temp.categoria.contains(lista.get(i).categoria)){
                relacionado = true;
            }
            }
            
            for (int i = 0; i < lista.size(); i++) {
            if(temp.nombre.equals(lista.get(i).nombre)){
                nombre = true;
            }
            }
             

            if (relacionado && !nombre) {
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/Tarjeta.fxml"));
                    Parent tarjeta = loader.load();
                    TarjetaController controller = loader.getController();
                    controller.anadirDatos(temp);
                    relPant.getChildren().add(tarjeta);
                    HBox.setMargin(tarjeta, new javafx.geometry.Insets(10));
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
            else {
                
            }

            temp = temp.sig; 
        }
    }

    
    @FXML
    public void Regresar() {
        try{
        Parent root = FXMLLoader.load(getClass().getResource("/Vista/PaginaPrincipal.fxml"));
        Stage stage = (Stage) buscar.getScene().getWindow();
        Scene scene = stage.getScene();
        scene.setRoot(root);
        }
        catch (IOException e){
            e.printStackTrace();
        }
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
    
    
   
}
