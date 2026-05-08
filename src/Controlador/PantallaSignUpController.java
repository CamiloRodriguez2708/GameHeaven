package Controlador;

import Modelo.nodoUsuario;
import Modelo.nodoVideojuego;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PantallaSignUpController implements Initializable {

    @FXML private TextField txtNombre;
    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtContrasena;
    @FXML private PasswordField txtConfirmarContrasena;
    @FXML private Pane overlay;
    @FXML private AnchorPane popupMensaje;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    @FXML
    public void registrar(ActionEvent event) {
        String nombre = valor(txtNombre);
        String correo = valor(txtCorreo);
        String clave = valor(txtContrasena);
        String confirmar = valor(txtConfirmarContrasena);
        
        

        if (nombre.isEmpty() || correo.isEmpty() || clave.isEmpty() || confirmar.isEmpty()) {
            abrirMensaje("Completa todos los campos básicos.", "cerrar");
            return;
        }
        if (!clave.equals(confirmar)) {
            abrirMensaje("Las contraseñas no coinciden.", "cerrar");
            return;
        }

        if (Sesion.lista.buscarCorr(correo) != null) {
            abrirMensaje("Ese correo ya está registrado.", "cerrar");
            return;
        }

        int tipo = 0;
        ArrayList<String> ListaDeseados = new ArrayList();
        ArrayList<String> historial = new ArrayList();
        ListaDeseados.add("#");
        historial.add("#");
        Date hoy = new Date();
        int id = Sesion.lista.calcularID();
        Sesion.lista.agregarInicio(
                nombre,
                correo,
                clave,
                nombre,
                "CC",
                "#",
                "#",
                "#",
                "#",
                "#",
                "#",
                "#",
                "#",
                "#",
                hoy,
                0,
                id,
                tipo,
                ListaDeseados,
                historial
        );
        Sesion.lista.guardarArchivo();
        abrirMensaje("Usuario registrado correctamente.", "/Vista/PantallaLogin.fxml");
    }

    @FXML
    public void Principal(MouseEvent event) throws Exception {
        cambiarVista(event, "/Vista/paginaPrincipal.fxml");
    }
    
    @FXML
    public void login(MouseEvent event) throws Exception{
        cambiarVista(event, "/Vista/PantallaLogin.fxml");
    }

    private String valor(TextField campo) {
        return campo.getText() == null ? "" : campo.getText().trim();
    }

    private String valor(PasswordField campo) {
        return campo.getText() == null ? "" : campo.getText().trim();
    }

   

    private void cambiarVista(Object evento, String ruta) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(ruta));
            Stage stage;
            if (evento instanceof ActionEvent actionEvent) {
                stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            } else {
                stage = (Stage) ((Node) ((MouseEvent) evento).getSource()).getScene().getWindow();
            }
            Scene scene = stage.getScene();
            
                scene.setRoot(root);
               
           
        } catch (Exception ex) {
            abrirMensaje("No se pudo abrir la pantalla solicitada.", "cerrar");
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
