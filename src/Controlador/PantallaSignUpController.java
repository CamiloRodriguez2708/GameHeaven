package Controlador;

import Modelo.nodoUsuario;
import java.net.URL;
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
import javafx.stage.Stage;
import javafx.util.Duration;

public class PantallaSignUpController implements Initializable {

    @FXML private TextField txtNombre;
    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtContrasena;
    @FXML private PasswordField txtConfirmarContrasena;
    @FXML private TextField txtId;
    @FXML private ComboBox<String> cbTipo;

    private final listaUsuarios lista = new listaUsuarios();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbTipo.getItems().setAll("Usuario", "Administrador");
        cbTipo.getSelectionModel().selectFirst();
    }

    @FXML
    public void registrar(ActionEvent event) {
        String nombre = valor(txtNombre);
        String correo = valor(txtCorreo);
        String clave = valor(txtContrasena);
        String confirmar = valor(txtConfirmarContrasena);
        String idTexto = valor(txtId);
        String tipoTexto = cbTipo.getValue();

        if (nombre.isEmpty() || correo.isEmpty() || clave.isEmpty() || confirmar.isEmpty() || idTexto.isEmpty() || tipoTexto == null) {
            alerta("Completa todos los campos básicos.");
            return;
        }
        if (!clave.equals(confirmar)) {
            alerta("Las contraseñas no coinciden.");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idTexto);
        } catch (NumberFormatException e) {
            alerta("El ID debe ser numérico.");
            return;
        }

        if (lista.buscarCorr(correo) != null) {
            alerta("Ese correo ya está registrado.");
            return;
        }

        int tipo = "Administrador".equals(tipoTexto) ? 1 : 0;
        Date hoy = new Date();
        lista.agregarInicio(
                nombre,
                correo,
                clave,
                nombre,
                "CC",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                hoy,
                0,
                id,
                tipo
        );

        alerta("Usuario registrado correctamente.");
        cambiarVista(event, "/Vista/PantallaLogin.fxml");
    }

    @FXML
    public void Principal(MouseEvent event) throws Exception {
        cambiarVista(event, "/Vista/paginaPrincipal.fxml");
    }

    private String valor(TextField campo) {
        return campo.getText() == null ? "" : campo.getText().trim();
    }

    private String valor(PasswordField campo) {
        return campo.getText() == null ? "" : campo.getText().trim();
    }

    private void alerta(String texto) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(texto);
        alert.showAndWait();
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
            alerta("No se pudo abrir la pantalla solicitada.");
        }
    }
}
