/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controlador;

import Modelo.nodoVideojuego;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Camilo Rodriguez
 */
public class PantallaGestionController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML FlowPane Gestion;
    
    @FXML TextField buscar;
    
    @FXML
    private Text txtUsuario, txtAccion;
    
    @FXML
    private ScrollPane ScrollG;
    
    @FXML private Pane overlay;
    @FXML private AnchorPane popupDetalles;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        actualizarUsuario();
        ScrollG.getStylesheets().add(getClass().getResource("/Styles/ScrollPaneG.css").toExternalForm());
        Platform.runLater(() -> {
          mostrarTodos();       });
        
        buscar.textProperty().addListener((obs, oldVal, newVal) -> {
            buscar.setText(String.valueOf(newVal));
            filtrarTodo(newVal);
        });
        
        overlay.setOnMouseClicked(e -> {
        overlay.setVisible(false);
        Sistema.listaJuegos.guardarArchivo();
        mostrarTodos();   
                                       });
    }
    
    public void mostrarTodos() {
         Gestion.getChildren().clear();
         nodoVideojuego temp = Sistema.listaJuegos.inicio;

    while (temp != null) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/TarjetaAdmin.fxml"));
            Parent tarjeta = loader.load();

            TarjetaAdminController controller = loader.getController();
            controller.anadirDatos(temp);
            
            controller.setControl(this);

            Gestion.getChildren().add(tarjeta);
            FlowPane.setMargin(tarjeta, new javafx.geometry.Insets(10));

        } catch (Exception e) {
            e.printStackTrace();
        }

        temp = temp.sig;
    }

    }
    
    public void filtrarTodo(String buscar) {
        Gestion.getChildren().clear(); 
        nodoVideojuego temp = Sistema.listaJuegos.inicio;

        while (temp != null) {

            boolean encontrado = temp.nombre.toLowerCase().contains(buscar.toLowerCase());
            
            if (encontrado) {
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/TarjetaAdmin.fxml"));
                    Parent tarjeta = loader.load();
                    TarjetaAdminController controller = loader.getController();
                    controller.anadirDatos(temp);
                    controller.setControl(this);
                    controller.setBuscar(buscar);
                    Gestion.getChildren().add(tarjeta);
                    FlowPane.setMargin(tarjeta, new javafx.geometry.Insets(10));
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
            else if(buscar.equals("") || buscar == null){
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/TarjetaAdmin.fxml"));
                    Parent tarjeta = loader.load();
                    TarjetaAdminController controller = loader.getController();
                    controller.anadirDatos(temp);
                    controller.setControl(this);
                    controller.setBuscar(buscar);
                    Gestion.getChildren().add(tarjeta);
                    FlowPane.setMargin(tarjeta, new javafx.geometry.Insets(10));
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                
            }

            temp = temp.sig; 
        }
    }
    
    @FXML
    public void Login(MouseEvent event) throws Exception {
        if (Sesion.usuarioActual != null) {
            Sesion.usuarioActual = null;
            actualizarUsuario();
            
            Parent root = FXMLLoader.load(getClass().getResource("/Vista/paginaPrincipal.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = stage.getScene();
            scene.setRoot(root);
            return;
        }
        Parent root = FXMLLoader.load(getClass().getResource("/Vista/PantallaLogin.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = stage.getScene();
        scene.setRoot(root);}
    @FXML
    public void principal(MouseEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Vista/paginaPrincipal.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = stage.getScene();
        scene.setRoot(root);
    }
    @FXML
    public void agregar(MouseEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Vista/PantallaAgregarElemento.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = stage.getScene();
        scene.setRoot(root);
    }
    
    @FXML
    private void actualizarUsuario() {
        if (Sesion.usuarioActual == null) {
            txtUsuario.setText("Invitado");
            txtAccion.setText("Ingresar");
        } else {
            txtUsuario.setText(Sesion.usuarioActual.nombreU);
            txtAccion.setText("Cerrar sesión");
        }
    }
    
    public void abrirDetalles(nodoVideojuego juego) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/SubDetalles.fxml"));
        Parent root = loader.load();

        SubDetallesController controller = loader.getController();
        controller.cargarDatos(juego);
        

        popupDetalles.getChildren().setAll(root);
        overlay.setVisible(true);

    } catch (Exception e) {
        e.printStackTrace();
    }
    
    
}
    public void abrirModificar(nodoVideojuego juego) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/SubModificar.fxml"));
        Parent root = loader.load();

        SubModificarController controller = loader.getController();
        controller.cargarDatos(juego);
        

        popupDetalles.getChildren().setAll(root);
        overlay.setVisible(true);

    } catch (Exception e) {
        e.printStackTrace();
    }
    }
}
