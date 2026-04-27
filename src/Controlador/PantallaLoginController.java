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

public class PantallaLoginController implements Initializable {

    @FXML
    private ComboBox<String> ComboBoxU;

    @FXML
    private TextField TextFieldU;

    @FXML
    private PasswordField PasswordFieldP;

    private final listaUsuarios lista = new listaUsuarios();

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
            mostrarAlerta("Completa todos los campos.");
            return;
        }

        nodoUsuario usuario = lista.iniciarSesion(correo, clave);
        if (usuario == null) {
            mostrarAlerta("Correo o contraseña incorrectos.");
            return;
        }

        if ("Usuario".equals(tipoSeleccionado) && usuario.tipo != 0) {
            mostrarAlerta("Ese usuario no corresponde al tipo seleccionado.");
            return;
        }
        if ("Administrador".equals(tipoSeleccionado) && usuario.tipo != 1) {
            mostrarAlerta("Ese usuario no corresponde al tipo seleccionado.");
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
            FadeTransition fadeOut = new FadeTransition(Duration.millis(200), scene.getRoot());
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setOnFinished(e -> {
                scene.setRoot(root);
                FadeTransition fadeIn = new FadeTransition(Duration.millis(200), root);
                fadeIn.setFromValue(0);
                fadeIn.setToValue(1);
                fadeIn.play();
            });
            fadeOut.play();
        } catch (Exception ex) {
            mostrarAlerta("No se pudo abrir la pantalla solicitada.");
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
