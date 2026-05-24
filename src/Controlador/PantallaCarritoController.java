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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PantallaCarritoController implements Initializable {

    @FXML private Text txtUsuario, txtAccion, carritoTxt, totalTxt, ivaTxt, mensajeTxt;
    @FXML private ComboBox<String> ComboBoxT;
    @FXML private TextField buscar;
    @FXML private ScrollPane Scroll;
    @FXML private AnchorPane compraPopup;
    @FXML private VBox listaCarrito, facturaItems;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list, "Catalogo", "Historial", "Favoritos");
        ComboBoxT.getItems().setAll(list);

        actualizarUsuario();
        cargarCarrito();
        Scroll.getStylesheets().add(getClass().getResource("/Styles/ScrollPane.css").toExternalForm());

        buscar.textProperty().addListener((obj, oldVal, newVal) -> {
            if (newVal != null && !newVal.isEmpty()) {
                Sesion.buscar = newVal;
                Buscar();
            }
        });

        Platform.runLater(() -> {
            Stage stage = (Stage) buscar.getScene().getWindow();
            stage.setOnCloseRequest(event -> Sesion.carrito.vaciar());
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
        Sesion.paginaAnterior = Sesion.paginaActual;
        Sesion.paginaActual = "/Vista/PantallaCarrito.fxml";
    }

    private void cargarCarrito() {
        listaCarrito.getChildren().clear();
        facturaItems.getChildren().clear();
        carritoTxt.setText(String.valueOf(Sesion.carrito.cantidadJuegos()));
        actualizarFactura();

        if (Sesion.carrito.cantidadJuegos() == 0) {
            mensajeTxt.setText("Tu carrito esta vacio");
            return;
        }

        mensajeTxt.setText("");
        for (nodoVideojuego juego : Sesion.carrito.obtenerJuegos()) {
            listaCarrito.getChildren().add(crearFilaCarrito(juego));
        }
    }

    private HBox crearFilaCarrito(nodoVideojuego juego) {
        HBox fila = new HBox(20);
        fila.setPrefWidth(760);
        fila.setMinHeight(80);
        fila.setAlignment(Pos.CENTER_LEFT);

        HBox datosFila = new HBox(16);
        datosFila.setPadding(new Insets(8));
        datosFila.setPrefWidth(635);
        datosFila.setMinHeight(80);
        datosFila.setAlignment(Pos.CENTER_LEFT);
        datosFila.setStyle("-fx-background-color: #021B2B; -fx-background-radius: 24;");

        ImageView portada = new ImageView();
        portada.setFitWidth(122);
        portada.setFitHeight(64);
        portada.setPreserveRatio(false);
        Rectangle clip = new Rectangle(122, 64);
        clip.setArcWidth(18);
        clip.setArcHeight(18);
        portada.setClip(clip);

        try {
            portada.setImage(new Image(new File(System.getProperty("user.dir") + "/src/datos/imagenes/" + juego.portada).toURI().toString()));
        } catch (Exception e) {
            portada.setImage(null);
        }

        Text nombre = new Text(nombreCarrito(juego).toUpperCase());
        nombre.setFill(Color.web("#EBDFCC"));
        nombre.setFont(Font.font("Nirmala UI", 22));
        nombre.setWrappingWidth(470);

        Button quitar = new Button("X");
        quitar.setPrefWidth(78);
        quitar.setPrefHeight(78);
        quitar.setMinWidth(78);
        quitar.setMinHeight(78);
        quitar.setStyle("-fx-background-color: #FF0841; -fx-background-radius: 24; -fx-text-fill: #EBDFCC; -fx-font-family: 'Nirmala UI'; -fx-font-size: 42;");
        quitar.setOnAction(event -> {
            Sesion.carrito.eliminarJuego(juego);
            cargarCarrito();
        });

        datosFila.getChildren().addAll(portada, nombre);
        fila.getChildren().addAll(datosFila, quitar);
        return fila;
    }

    private Text crearLineaFactura(nodoVideojuego juego) {
        float precio = juego.edicion.equals("Digital") ? juego.precioDigital : juego.precioFisico;
        Text linea = new Text(nombreFactura(juego) + ": $" + (int) precio);
        linea.setFill(Color.web("#EBDFCC"));
        linea.setFont(Font.font("Nirmala UI", 24));
        linea.setWrappingWidth(390);
        return linea;
    }

    private void actualizarFactura() {
        float subtotal = Sesion.carrito.calcularTotal();
        int iva = Math.round(subtotal * 0.05f);
        int total = Math.round(subtotal + iva);

        for (nodoVideojuego juego : Sesion.carrito.obtenerJuegos()) {
            facturaItems.getChildren().add(crearLineaFactura(juego));
        }

        ivaTxt.setText("Iva: $" + iva);
        totalTxt.setText("Total: $" + total);
    }

    private String nombreCarrito(nodoVideojuego juego) {
        return juego.nombre + " (" + plataforma(juego) + ") (" + juego.edicion + ")";
    }

    private String nombreFactura(nodoVideojuego juego) {
        return juego.nombre + " (" + plataforma(juego).toUpperCase() + ") (" + juego.edicion + ")";
    }

    private String plataforma(nodoVideojuego juego) {
        return juego.plataforma.isEmpty() ? "Sin plataforma" : juego.plataforma.get(0);
    }

    @FXML
    public void comprar(MouseEvent event) {
        if (Sesion.carrito.cantidadJuegos() == 0) {
            mensajeTxt.setText("Agrega juegos antes de finalizar la compra");
            return;
        }

        Sesion.carrito.confirmarCompra();
        compraPopup.setVisible(true);
    }

    @FXML
    public void cerrarCompraPopup(MouseEvent event) {
        compraPopup.setVisible(false);
        cargarCarrito();
        mensajeTxt.setText("Compra finalizada correctamente");
    }

    @FXML
    public void vaciar(MouseEvent event) {
        Sesion.carrito.vaciar();
        cargarCarrito();
        mensajeTxt.setText("Carrito vaciado");
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
            txtAccion.setText("Configuracion");
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
