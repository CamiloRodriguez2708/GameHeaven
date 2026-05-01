package Controlador;

import Modelo.nodoVideojuego;
import Controlador.TarjetaController;
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
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class PaginaPrincipalController implements Initializable {
    
    String actFiltro = "";

    @FXML
    private ComboBox<String> ComboBoxT;

    @FXML
    private Slider SliderP;

    @FXML
    private Text txtUsuario, FiltroPrecio, txtAccion;
    
    @FXML
    private FlowPane panelCatalogo;
    
    @FXML
    private ScrollPane Scroll;
    
    @FXML
    private BorderPane Pantalla;
    
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
    
    private ListaDobleVideojuegos listaJuegos = new ListaDobleVideojuegos();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list, "Tienda", "Historial", "Favoritos");
        ComboBoxT.getItems().setAll(list);
        actualizarUsuario();
        Scroll.getStylesheets().add(getClass().getResource("/Styles/ScrollPane.css").toExternalForm());
        SliderP.valueProperty().addListener((obs, oldVal, newVal) -> {
            FiltroPrecio.setText(String.valueOf(newVal.intValue()));
            filtrarTodo(actFiltro, newVal.doubleValue());
        });
        
        Platform.runLater(() -> {
          mostrarTodos();
          activarBoton(btnMostrarTodo); 
       });
    }

    private void actualizarUsuario() {
        if (Sesion.usuarioActual == null) {
            txtUsuario.setText("Invitado");
            txtAccion.setText("Ingresar");
        } else {
            txtUsuario.setText(Sesion.usuarioActual.nombreU);
            txtAccion.setText("Cerrar sesión");
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

    activo.setStyle("-fx-background-color: #ff0841; -fx-text-fill: white;");
}

    
    private void filtrarTodo(String categoriaBusqueda, double precioMax) {
        panelCatalogo.getChildren().clear(); 
        nodoVideojuego temp = listaJuegos.inicio;

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
                    panelCatalogo.getChildren().add(tarjeta);
                    FlowPane.setMargin(tarjeta, new javafx.geometry.Insets(10));
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
                    panelCatalogo.getChildren().add(tarjeta);
                    FlowPane.setMargin(tarjeta, new javafx.geometry.Insets(10));
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
    nodoVideojuego temp = listaJuegos.inicio;

    while (temp != null) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/Tarjeta.fxml"));
            Parent tarjeta = loader.load();

            TarjetaController controller = loader.getController();
            controller.anadirDatos(temp);

            panelCatalogo.getChildren().add(tarjeta);
            FlowPane.setMargin(tarjeta, new javafx.geometry.Insets(10));

        } catch (Exception e) {
            e.printStackTrace();
        }

        temp = temp.sig;
    }

    }
    
    

    @FXML
    public void Login(MouseEvent event) throws Exception {
        if (Sesion.usuarioActual != null) {
            Sesion.usuarioActual = null;
            actualizarUsuario();
            return;
        }

        Parent root = FXMLLoader.load(getClass().getResource("/Vista/PantallaLogin.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = stage.getScene();

        FadeTransition fadeOut = new FadeTransition(Duration.millis(250), scene.getRoot());
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(e -> {
            scene.setRoot(root);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(250), root);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();
        });
        fadeOut.play();
    }
}