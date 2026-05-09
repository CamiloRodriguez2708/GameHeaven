/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controlador;

import Modelo.nodoVideojuego;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Camilo Rodriguez
 */
public class PantallaAgregarElementoController implements Initializable {
   
    @FXML
    private ImageView portada, captura1, captura2, captura3;
    
    @FXML
    private Text txtUsuario, txtAccion;
    
    @FXML
    private TextField nombreTF, plataformasTF, precioD, precioF, stockD, stockF;
    
    @FXML
    private ComboBox plataformaC;
    
    @FXML
    private TextArea TextAreaD;
    
    @FXML private Pane overlay;
    @FXML private AnchorPane popupMensaje;
    
    public int id = Sistema.listaJuegos.generarID();
    public String nombre = "";
    public ArrayList<String> plataforma = new ArrayList<>();
          
    public String categoria = "";       
    public ArrayList<String> etiquetas = new ArrayList<>();
    public String descripcion = "";     
    
    //INFO TECNICA
    public String peso = "";            
    public String requisitosMin = "";  
    public String requisitosRec = "";   
    public ArrayList<String> idioma = new ArrayList<>();
    public String fechaLanzamiento = ""; 

    //imagenes
    public String portadaDir = "";         
    public ArrayList<String> capturas = new ArrayList<>();      
            

   
    public float precioDigital = 0;
    public float precioFisico = 0;
    
    public ArrayList<Integer> stockDigital = new ArrayList<>();
    public ArrayList<Integer> stockFisico = new ArrayList<>();
    
 
    public ArrayList<Float> calificaciones = new ArrayList<>();   
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        actualizarUsuario();
        Rectangle clip0 = new Rectangle();
        Rectangle clip1 = new Rectangle();
        Rectangle clip2 = new Rectangle();
        Rectangle clip3 = new Rectangle();
        
        clip0.widthProperty().bind(portada.fitWidthProperty());
        clip0.heightProperty().bind(portada.fitHeightProperty());
        portada.setPreserveRatio(false); 
        
        clip1.widthProperty().bind(captura1.fitWidthProperty());
        clip1.heightProperty().bind(captura1.fitHeightProperty());
        captura1.setPreserveRatio(false); 
        
        clip2.widthProperty().bind(captura2.fitWidthProperty());
        clip2.heightProperty().bind(captura2.fitHeightProperty());
        captura2.setPreserveRatio(false); 
        
        clip3.widthProperty().bind(captura3.fitWidthProperty());
        clip3.heightProperty().bind(captura3.fitHeightProperty());
        captura3.setPreserveRatio(false); 

    
        clip0.setArcWidth(30);
        clip0.setArcHeight(30);
        
        clip1.setArcWidth(30);
        clip1.setArcHeight(30);
        
        clip2.setArcWidth(30);
        clip2.setArcHeight(30);
        
        clip3.setArcWidth(30);
        clip3.setArcHeight(30);
        
        portada.setClip(clip0);
        captura1.setClip(clip1);
        captura2.setClip(clip2);
        captura3.setClip(clip3);
        
        calificaciones.add(0f);
        //PORTADA
       portada.setOnDragOver(event -> {
    if (event.getGestureSource() != portada && event.getDragboard().hasFiles()) {
        event.acceptTransferModes(TransferMode.COPY);
    }
    event.consume();
    
                                                                                });
       portada.setOnDragDropped(event -> {
    Dragboard db = event.getDragboard();
    boolean success = false;

    if (db.hasFiles()) {
        success = true;

        for (File file : db.getFiles()) {
        if (file.getName().endsWith(".jpg")) {
             guardarPortada(file);
            portada.setImage(new Image(new File(System.getProperty("user.dir")+ "/src/datos/imagenes/"+ portadaDir).toURI().toString()));
         }
        }
    }
    event.setDropCompleted(success);
    event.consume();
});
       //CAPTURA 1
       captura1.setOnDragOver(event -> {
    if (event.getGestureSource() != captura1 && event.getDragboard().hasFiles()) {
        event.acceptTransferModes(TransferMode.COPY);
    }
    event.consume();
    
                                                                                });
       captura1.setOnDragDropped(event -> {
    Dragboard db = event.getDragboard();
    boolean success = false;

    if (db.hasFiles()) {
        success = true;

        for (File file : db.getFiles()) {
        if (file.getName().endsWith(".jpg")) {
             
            captura1.setImage(new Image(new File(guardarCaptura(file)).toURI().toString()));
         }
        }
    }
    event.setDropCompleted(success);
    event.consume();
});
       //CAPTURA 2
       captura2.setOnDragOver(event -> {
    if (event.getGestureSource() != captura2 && event.getDragboard().hasFiles()) {
        event.acceptTransferModes(TransferMode.COPY);
    }
    event.consume();
    
                                                                                });
       captura2.setOnDragDropped(event -> {
    Dragboard db = event.getDragboard();
    boolean success = false;

    if (db.hasFiles()) {
        success = true;

        for (File file : db.getFiles()) {
        if (file.getName().endsWith(".jpg")) {
             
            captura2.setImage(new Image(new File(guardarCaptura(file)).toURI().toString()));
         }
        }
    }
    event.setDropCompleted(success);
    event.consume();
});
       //CAPTURA 3
       captura3.setOnDragOver(event -> {
    if (event.getGestureSource() != captura3 && event.getDragboard().hasFiles()) {
        event.acceptTransferModes(TransferMode.COPY);
    }
    event.consume();
    
                                                                                });
       captura3.setOnDragDropped(event -> {
    Dragboard db = event.getDragboard();
    boolean success = false;

    if (db.hasFiles()) {
        success = true;

        for (File file : db.getFiles()) {
        if (file.getName().endsWith(".jpg")) {
             
            captura3.setImage(new Image(new File(guardarCaptura(file)).toURI().toString()));
         }
        }
    }
    event.setDropCompleted(success);
    event.consume();
});
       
       // NOMBRE JUEGO
       nombreTF.focusedProperty().addListener((obs, oldVal, newVal) -> {
           if(!newVal){
               String texto = nombreTF.getText();

               nodoVideojuego v = Sistema.listaJuegos.buscarPorNombre(texto);

               if (v != null) {

               abrirMensaje("El nombre ya está registrado", "cerrar");
               nombreTF.setText("");

            }else {

            nombre = texto;
            }
           }
            
        });
       
       // AGREGAR PLATAFORMA
       plataformasTF.focusedProperty().addListener((obs, oldVal, newVal) -> {
           
             plataforma.clear(); 

        if (!newVal) {
        String[] partes = plataformasTF.getText().split(",");

        for (String p : partes) {
            plataforma.add(p.trim()); 
        }
        plataformaC.getItems().clear();
        
        plataformaC.getItems().addAll(plataforma);
        stockDigital.clear();
        stockFisico.clear();
        for (int i = 0; i < plataforma.size(); i++) {
        stockDigital.add(0);
        stockFisico.add(0);
        }
    }
        });
       
       precioD.textProperty().addListener((obs, oldVal, newVal) -> {
            precioDigital = Float.parseFloat(newVal);
        });
       
       precioF.textProperty().addListener((obs, oldVal, newVal) -> {
            precioFisico = Float.parseFloat(newVal);
        });
       plataformaC.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
        stockD.setText(stockDigital.get(plataformaC.getSelectionModel().getSelectedIndex()).toString());
        stockF.setText(stockFisico.get(plataformaC.getSelectionModel().getSelectedIndex()).toString());
    }
);
       stockD.textProperty().addListener((obs, oldVal, newVal)-> {
           stockDigital.set(plataformaC.getSelectionModel().getSelectedIndex(), Integer.parseInt(newVal));
       });
       stockF.textProperty().addListener((obs, oldVal, newVal)-> {
           stockFisico.set(plataformaC.getSelectionModel().getSelectedIndex(), Integer.parseInt(newVal));
       });
       
       TextAreaD.setText("Descripcion: " + "\n"+"Categoria: "+"\n"+ "Etiquetas: "+"\n"+ "Idiomas: "+"\n"+ "Fecha de lanzamiento: "+ "\n"+ "Peso: "+"\n"+ "Requisitos minimos: "+"\n" +"Requisitos recomendados: ");
       
       TextAreaD.focusedProperty().addListener((obs, oldVal, newVal) -> {
    if(!newVal){
        obtenerTexto();
    }
                                                                     });
       
       Platform.runLater(()->{
           Stage stage = (Stage) overlay.getScene().getWindow();
           stage.setOnCloseRequest(event -> {

            eliminarCarpeta();
           });
       });
    }
    
    
    private void guardarPortada(File archivoOrigen) {
    try {
        
        String rutaCarpeta;
        
        if(nombre.equals("")){
            abrirMensaje("Primero digite el nombre", "cerrar");
            return; 
        }
        else{
        rutaCarpeta = System.getProperty("user.dir")+ "/src/datos/imagenes/" + nombre; 
        }
        
        File carpeta = new File(rutaCarpeta);

        
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }

        
        File archivoDestino = new File(carpeta, archivoOrigen.getName());

        
        Files.copy(
            archivoOrigen.toPath(),
            archivoDestino.toPath(),
            StandardCopyOption.REPLACE_EXISTING
        );

        System.out.println("Archivo guardado en: " + archivoDestino.getAbsolutePath());
        portadaDir = archivoDestino.getParentFile().getName() +"/" + archivoDestino.getName();
    } catch (IOException e) {
        e.printStackTrace();
    }
    
}
    private String guardarCaptura(File archivoOrigen) {
    try {
        
        String rutaCarpeta;
        
        if(nombre.equals("")){
            abrirMensaje("Primero digite el nombre", "cerrar");
            return null;
        }
        else{
        rutaCarpeta = System.getProperty("user.dir")+ "/src/datos/imagenes/" + nombre; 
        }
        
        File carpeta = new File(rutaCarpeta);

        
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }

        
        File archivoDestino = new File(carpeta, archivoOrigen.getName());

        
        Files.copy(
            archivoOrigen.toPath(),
            archivoDestino.toPath(),
            StandardCopyOption.REPLACE_EXISTING
        );

        System.out.println("Archivo guardado en: " + archivoDestino.getAbsolutePath());
        capturas.add(archivoDestino.getParentFile().getName() +"/" + archivoDestino.getName());
        return System.getProperty("user.dir")+ "/src/datos/imagenes/" + archivoDestino.getParentFile().getName() +"/" + archivoDestino.getName();
    } catch (IOException e) {
        e.printStackTrace();
    }
    return null;
    }
    
    public void obtenerTexto(){
        String texto = TextAreaD.getText();
        String[] lineas = texto.split("\\r?\\n");

    for (String linea : lineas) {
    linea = linea.trim();
    if (linea.isEmpty()) continue;

    String[] partes = linea.split(":", 2);
    if (partes.length < 2) continue;

    String clave = partes[0].trim().toLowerCase();
    String valor = partes[1].trim();

    switch (clave) {
        case "descripcion":
            descripcion = valor;
            break;

        case "categoria":
            categoria = valor;
            break;

        case "etiquetas":
            etiquetas.clear();
            for (String e : valor.split(",")) {
                etiquetas.add(e.trim());
            }
            break;

        case "idiomas":
            idioma.clear();
            for (String i : valor.split(",")) {
                idioma.add(i.trim());
            }
            break;

        case "fecha de lanzamiento":
            fechaLanzamiento = valor;
            break;

        case "peso":
            peso = valor;
            break;

        case "requisitos minimos":
            requisitosMin = valor;
            break;

        case "requisitos recomendados":
            requisitosRec = valor;
            break;
    }
}
    }
    
    public void crearNodo(MouseEvent event) throws Exception {
        Sistema.listaJuegos.insertar(new nodoVideojuego(id, nombre, plataforma, "Digital", categoria, 
                          etiquetas, descripcion, peso, requisitosMin, 
                          requisitosRec, idioma, fechaLanzamiento, portadaDir, 
                          capturas, precioDigital, precioFisico,
                          stockDigital, stockFisico, calificaciones));
        Sistema.listaJuegos.guardarArchivo();
        abrirMensaje("Se ha creador correctamente el elemento", "/Vista/PantallaGestion.fxml");
        
        
    }
    @FXML
    public void gestion(MouseEvent event) throws Exception {
        eliminarCarpeta();
        Parent root = FXMLLoader.load(getClass().getResource("/Vista/PantallaGestion.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = stage.getScene();
        scene.setRoot(root);
    }
    
    @FXML
    public void Login(MouseEvent event) throws Exception {
        if (Sesion.usuarioActual != null) {
            Sesion.usuarioActual = null;
            actualizarUsuario();
            eliminarCarpeta();
            Parent root = FXMLLoader.load(getClass().getResource("/Vista/paginaPrincipal.fxml"));
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
    private void actualizarUsuario() {
        if (Sesion.usuarioActual == null) {
            txtUsuario.setText("Invitado");
            txtAccion.setText("Ingresar");
        } else {
            txtUsuario.setText(Sesion.usuarioActual.nombreU);
            txtAccion.setText("Cerrar sesión");
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
    
    public void eliminarCarpeta(){

    File carpeta = new File(
        System.getProperty("user.dir")
        + "/src/datos/imagenes/" + nombre
    );

    portada.setImage(null);
    captura1.setImage(null);
    captura2.setImage(null);
    captura3.setImage(null);

    System.gc();

    try{

        if(portadaDir != null && !portadaDir.isEmpty()){

            File portadaA = new File(
                System.getProperty("user.dir")+ "/src/datos/imagenes/" + portadaDir
            );

            portadaA.delete();
        }

        if(capturas.size() > 0){

            File cap1A = new File(
                System.getProperty("user.dir")+ "/src/datos/imagenes/" + capturas.get(0)
            );

            cap1A.delete();
        }

        if(capturas.size() > 1){

            File cap2A = new File(
                System.getProperty("user.dir")+ "/src/datos/imagenes/" + capturas.get(1)
            );

            cap2A.delete();
        }

        if(capturas.size() > 2){

            File cap3A = new File(
                System.getProperty("user.dir")+ "/src/datos/imagenes/" + capturas.get(2)
            );

            cap3A.delete();
        }

        carpeta.delete();

    }
    catch(Exception e){

        e.printStackTrace();

    }
}
}
