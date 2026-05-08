package Controlador;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
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
import javafx.stage.Stage;
import javafx.util.Duration;
import Modelo.nodoUsuario;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class PantallaLoginController implements Initializable {

    @FXML
    private ComboBox<String> ComboBoxU;

    @FXML
    private TextField TextFieldU;

    @FXML
    private PasswordField PasswordFieldP;

    @FXML private Pane overlay;
    @FXML private AnchorPane popupMensaje;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list, "Usuario", "Administrador");
        ComboBoxU.getItems().setAll(list);
        ComboBoxU.getSelectionModel().selectFirst();
    }

    @FXML
    public void iniciarSesion(ActionEvent event) {
        String correo = TextFieldU.getText() == null ? "" : TextFieldU.getText().trim();
        String clave = PasswordFieldP.getText() == null ? "" : PasswordFieldP.getText().trim();
        String tipoSeleccionado = ComboBoxU.getValue();

        if (correo.isEmpty() || clave.isEmpty() || tipoSeleccionado == null) {
            abrirMensaje("Completa todos los campos.", "cerrar");
            return;
        }

        nodoUsuario usuario = Sesion.lista.iniciarSesion(correo, clave);
        if (usuario == null) {
            abrirMensaje("Correo o contraseña incorrectos.", "cerrar");
            return;
        }

        if ("Usuario".equals(tipoSeleccionado) && usuario.tipo != 0) {
            abrirMensaje("Ese usuario no corresponde al tipo seleccionado.", "cerrar");
            return;
        }
        if ("Administrador".equals(tipoSeleccionado) && usuario.tipo != 1) {
            abrirMensaje("Ese usuario no corresponde al tipo seleccionado.", "cerrar");
            return;
        }

        Sesion.usuarioActual = usuario;
        cambiarVista(event, "/Vista/paginaPrincipal.fxml");
    }

    @FXML
    public void Principal(MouseEvent event) throws Exception {
        cambiarVista(event, "/Vista/paginaPrincipal.fxml");
    }

    @FXML
    public void signUp(MouseEvent event) throws Exception {
        cambiarVista(event, "/Vista/PantallaSignUp.fxml");
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
