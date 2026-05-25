package Controlador;

import Modelo.nodoVideojuego;
import Controlador.TarjetaController;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;


public class PaginaPrincipalController implements Initializable {
    
    String actFiltro = "";

    @FXML
    private ComboBox<String> ComboBoxT;

    @FXML
    private Slider SliderP;

    @FXML
    private Text txtUsuario, FiltroPrecio, txtAccion, carritoTxt, mensajeCarritoTxt;
    
    @FXML
    private AnchorPane BuscarP;
    
    @FXML
    private Pane PaneCompra, PanelMBotones;
    
    @FXML
    private FlowPane panelCatalogo, panelBotones;
    
    @FXML
    private ScrollPane Scroll, ScrollP;
    
    @FXML
    private BorderPane Pantalla;
    
    @FXML
    private TextField buscar;
    
    @FXML
    private Button Gestionar;
    
    @FXML private Pane overlay;
    @FXML private AnchorPane popupMensaje;
    @FXML private AnchorPane popupCarrito;
    
   
    
      @FXML
      private void accionClick() { 
           activarBoton(btnAccion);
           filtrarTodo("accion", SliderP.getValue()); 
           actFiltro = "accion";
      }
      
      @FXML
       private void estrategiaClick() { 
         activarBoton(btnEstrategia);
         filtrarTodo("estrategia", SliderP.getValue()); 
         actFiltro = "estrategia";
      }

      @FXML
      private void casualClick() { 
         activarBoton(btnCasual);
         filtrarTodo("casual", SliderP.getValue()); 
         actFiltro = "casual";
       }

      @FXML
       private void rpgClick() { 
         activarBoton(btnRPG);
         filtrarTodo("rpg", SliderP.getValue());
         actFiltro = "rpg";
       }

      @FXML
       private void disparosClick() { 
         activarBoton(btnDisparos);
         filtrarTodo("disparos", SliderP.getValue()); 
         actFiltro = "disparos";
       }

      @FXML
       private void ritmoClick() { 
          activarBoton(btnRitmo);
          filtrarTodo("ritmo", SliderP.getValue()); 
          actFiltro = "ritmo";
        }

      @FXML
       private void mostrarTodoClick() { 
          activarBoton(btnMostrarTodo);
           actFiltro = "";
           mostrarTodos(); 
         }
    
     @FXML private javafx.scene.control.Button btnMostrarTodo;
     @FXML private javafx.scene.control.Button btnAccion;
     @FXML private javafx.scene.control.Button btnDisparos;
     @FXML private javafx.scene.control.Button btnRPG;
     @FXML private javafx.scene.control.Button btnRitmo;
     @FXML private javafx.scene.control.Button btnEstrategia;
     @FXML private javafx.scene.control.Button btnCasual;
     @FXML private javafx.scene.control.Button btnMas;
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list, "Catalogo", "Historial", "Favoritos");
        ComboBoxT.getItems().setAll(list);
        
        actualizarUsuario();
        carritoTxt.setText(String.valueOf(Sesion.carrito.cantidadJuegos()));
        Scroll.getStylesheets().add(getClass().getResource("/Styles/ScrollPane.css").toExternalForm());
        ScrollP.getStylesheets().add(getClass().getResource("/Styles/ScrollPaneP.css").toExternalForm());
        if(Sesion.usuarioActual != null){
        Mostrar();
        }
        else{
            BuscarP.setVisible(true);
            PaneCompra.setVisible(true);
            ComboBoxT.setVisible(true);
            Gestionar.setVisible(false);
        }
        
        SliderP.valueProperty().addListener((obs, oldVal, newVal) -> {
            FiltroPrecio.setText(String.valueOf(newVal.intValue()));
            filtrarTodo(actFiltro, newVal.doubleValue());
        });
        
        Platform.runLater(() -> {
          mostrarTodos();
          activarBoton(btnMostrarTodo); 
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
                Parent root = FXMLLoader.load(getClass().getResource("/Vista/PantallaHistorial.fxml"));
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
        Sesion.paginaAnterior = Sesion.paginaActual;
        Sesion.paginaActual = "/Vista/PaginaPrincipal.fxml";
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
    
    private void activarBoton(javafx.scene.control.Button activo) {

    btnMostrarTodo.setStyle("-fx-background-color: #EBDFCC;");
    btnAccion.setStyle("-fx-background-color: #EBDFCC;");
    btnDisparos.setStyle("-fx-background-color: #EBDFCC;");
    btnRPG.setStyle("-fx-background-color: #EBDFCC;");
    btnRitmo.setStyle("-fx-background-color: #EBDFCC;");
    btnEstrategia.setStyle("-fx-background-color: #EBDFCC;");
    btnCasual.setStyle("-fx-background-color: #EBDFCC;");
    btnMas.setStyle("-fx-background-color: #EBDFCC;");

    activo.setStyle("-fx-background-color: #ff0841; -fx-text-fill: white;");
}

    
    private void filtrarTodo(String categoriaBusqueda, double precioMax) {
        panelCatalogo.getChildren().clear(); 
        nodoVideojuego temp = Sistema.listaJuegos.inicio;

        while (temp != null) {

            boolean cumpleCat = categoriaBusqueda.equalsIgnoreCase("Todos") || 
                    temp.categoria.toLowerCase().contains(categoriaBusqueda.toLowerCase());

            boolean cumplePrecio = temp.precioDigital <= precioMax;

            if (cumpleCat && cumplePrecio) {
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/Tarjeta.fxml"));
                    Parent tarjeta = loader.load();
                    TarjetaController controller = loader.getController();
                    controller.anadirDatos(temp);
                    controller.getControlador(this);
                    panelCatalogo.getChildren().add(tarjeta);
                    FlowPane.setMargin(tarjeta, new javafx.geometry.Insets(15));
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
            else if(categoriaBusqueda.equals("") && cumplePrecio){
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/Tarjeta.fxml"));
                    Parent tarjeta = loader.load();
                    TarjetaController controller = loader.getController();
                    controller.anadirDatos(temp);
                    controller.getControlador(this);
                    panelCatalogo.getChildren().add(tarjeta);
                    FlowPane.setMargin(tarjeta, new javafx.geometry.Insets(15));
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                
            }

            temp = temp.sig; 
        }
    }

    
    private void mostrarTodos() {
         panelCatalogo.getChildren().clear();
         nodoVideojuego temp = Sistema.listaJuegos.inicio;

    while (temp != null) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/Tarjeta.fxml"));
            Parent tarjeta = loader.load();

            TarjetaController controller = loader.getController();
            controller.anadirDatos(temp);
            controller.getControlador(this);

            panelCatalogo.getChildren().add(tarjeta);
            FlowPane.setMargin(tarjeta, new javafx.geometry.Insets(15));

        } catch (Exception e) {
            e.printStackTrace();
        }

        temp = temp.sig;
    }

    }
    
    @FXML
    public void Mostrar(){
        if(Sesion.usuarioActual.tipo == 1){
            BuscarP.setVisible(false);
            PaneCompra.setVisible(false);
            ComboBoxT.setVisible(false);
            Gestionar.setVisible(true);
        }
        else if(Sesion.usuarioActual.tipo == 0){
            BuscarP.setVisible(true);
            PaneCompra.setVisible(true);
            ComboBoxT.setVisible(true);
            Gestionar.setVisible(false);
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
    public void Admin (MouseEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Vista/PantallaGestion.fxml"));
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
    
    @FXML
    void revisarInicio(MouseEvent event){
        if(Sesion.usuarioActual == null){
        abrirMensaje("POR FAVOR INICIE SESIÓN PRIMERO", "cerrar");
        ComboBoxT.hide();
        }
    }
    
    void crearBotones(){
    panelBotones.getChildren().clear();
    nodoVideojuego aux = Sistema.listaJuegos.inicio;
    ArrayList<String> repetidos = new ArrayList();
    while (aux != null){
    
        if(!aux.categoria.equalsIgnoreCase("accion") &&
           !aux.categoria.equalsIgnoreCase("estrategia") &&
           !aux.categoria.equalsIgnoreCase("casual") &&
           !aux.categoria.equalsIgnoreCase("rpg") &&
           !aux.categoria.equalsIgnoreCase("disparos") &&
           !aux.categoria.equalsIgnoreCase("ritmo") &&
           !repetidos.contains(aux.categoria.toLowerCase())){
           repetidos.add(aux.categoria.toLowerCase());
    Button boton = new Button(aux.categoria);
    String categoria = aux.categoria;
    boton.setOnAction(e -> {
        filtrarTodo(categoria, SliderP.getValue()); 
        actFiltro = categoria;
        PanelMBotones.setVisible(false);
    });
    boton.setStyle("""
    -fx-background-color: #ff0841;
    -fx-text-fill: white;
    -fx-font-family: 'Nirmala UI';
    -fx-font-weight: bold;
    -fx-font-size: 16px; 
     """);
    panelBotones.getChildren().add(boton);
    
}
        aux = aux.sig;
    }
   
}
    @FXML
    private void abrirBotones(){
        PanelMBotones.setVisible(true);
        activarBoton(btnMas);
        panelBotones.setHgap(10);
        panelBotones.setVgap(10);
        panelBotones.setPadding(new Insets(10));
        crearBotones();
    }
    
    @FXML
    private void cerrarBotones(MouseEvent event){
        PanelMBotones.setVisible(false);
    }
}
