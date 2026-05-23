package Controlador;

import Modelo.nodoVideojuego;
import java.io.IOException;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import Controlador.ColaHistorial;


public class PantallaHistorialController implements Initializable {

    @FXML
    private ComboBox<String> ComboBoxT;

    @FXML
    private Text txtUsuario, txtAccion, carritoTxt, mensajeCarritoTxt, historial;
    
    @FXML
    private AnchorPane BuscarP;
    
    @FXML
    private Pane PaneCompra;
    
    @FXML
    private FlowPane panelCatalogo;
    
    @FXML
    private ScrollPane Scroll;
    
    
    @FXML
    private TextField buscar;
    

    
    @FXML private Pane overlay;
    @FXML private AnchorPane popupMensaje;
    @FXML private AnchorPane popupCarrito;
    
    ColaHistorial cola = new ColaHistorial();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list, "Catalogo", "Historial", "Favoritos");
        ComboBoxT.getItems().setAll(list);
        actualizarUsuario();
        carritoTxt.setText(String.valueOf(Sesion.carrito.cantidadJuegos()));
        Scroll.getStylesheets().add(getClass().getResource("/Styles/ScrollPane.css").toExternalForm());
        if(Sesion.usuarioActual != null){
        Mostrar();
        }
        else{
            BuscarP.setVisible(true);
            PaneCompra.setVisible(true);
            ComboBoxT.setVisible(true);
        }
        
        Platform.runLater(() -> {
          mostrarTodos();
          Stage stage = (Stage) buscar.getScene().getWindow();
           stage.setOnCloseRequest(event -> {

            Sesion.carrito.vaciar();
           });
       });
        
        buscar.textProperty().addListener((obj, oldVal, newVal) ->{
            if(newVal != ""){
                if(Sesion.usuarioActual != null){
                Sesion.buscar = newVal;
                Buscar();
                }
                else{
                    Sesion.buscar = "";
                    buscar.setText("");
                    abrirMensaje("POR FAVOR INICIE SESIÓN PRIMERO", "cerrar");
                }
            }
            else{
                
            }
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
                Parent root = FXMLLoader.load(getClass().getResource("/Vista/PantallaFavoritos.fxml"));
                Stage stage = (Stage) buscar.getScene().getWindow();
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
        if(!Sesion.usuarioActual.historial.get(0).equals("#")){
        for(int i =0 ; i<Sesion.usuarioActual.historial.size(); i++){
            cola.encolar(Sesion.usuarioActual.historial.get(i));
        }
    }
        else{
            historial.setText("No hay ningun juego en tu historial");
        }
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
    
    
    
    private void mostrarTodos() {
         panelCatalogo.getChildren().clear();
         nodoVideojuego temp = cola.getFrente();
        
        if(!Sesion.usuarioActual.historial.equals("#")){
        while(temp != null){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/TarjetaHistorial.fxml"));
            Parent tarjeta = loader.load();

            TarjetaHistorialController controller = loader.getController();
            controller.anadirDatos(temp);
            

            panelCatalogo.getChildren().add(tarjeta);
            FlowPane.setMargin(tarjeta, new javafx.geometry.Insets(15));

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        

        temp = temp.sig;
        }
    }
        
        
    }
    
    @FXML
    public void Mostrar(){
        if(Sesion.usuarioActual.tipo == 1){
            BuscarP.setVisible(false);
            PaneCompra.setVisible(false);
            ComboBoxT.setVisible(false);
            
        }
        else if(Sesion.usuarioActual.tipo == 0){
            BuscarP.setVisible(true);
            PaneCompra.setVisible(true);
            ComboBoxT.setVisible(true);
            
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
    
    
    
    public void Buscar() {
        try{
        Parent root = FXMLLoader.load(getClass().getResource("/Vista/PantallaBuscar.fxml"));
        Stage stage = (Stage) BuscarP.getScene().getWindow();
        Scene scene = stage.getScene();
        scene.setRoot(root);
        }
        catch (IOException e){
            e.printStackTrace();
        }
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
    
    
   
   
}
