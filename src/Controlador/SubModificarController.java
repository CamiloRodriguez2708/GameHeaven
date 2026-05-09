/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import Modelo.nodoVideojuego;
import java.io.FileInputStream;
import java.io.InputStream;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author Camilo Rodriguez
 */
public class SubModificarController implements Initializable {

    @FXML private ImageView portada, captura1, captura2, captura3;
    @FXML private ComboBox<String> plataformaC, edicionC;
    @FXML private TextField stockT, precioT;
    @FXML private Text idTxt, nombreTxt, promedioTxt;
    @FXML private TextArea TextAreaD;
    
    private nodoVideojuego juego;
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
        
        edicionC.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
            seleccionado();
                                                                                                  });
        plataformaC.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
            seleccionado();
                                                                                                  });
        edicionC.getSelectionModel().selectFirst();
        plataformaC.getSelectionModel().selectFirst();
        
        stockT.textProperty().addListener((obs, oldVal, newVal) -> {

        if(!newVal.isEmpty()){

        try{

            int stock = Integer.parseInt(newVal);

            if(edicionC.getSelectionModel().getSelectedIndex() == 0){
                juego.stockDigital.set(plataformaC.getSelectionModel().getSelectedIndex(),stock);
            }
            else{
                juego.stockFisico.set(plataformaC.getSelectionModel().getSelectedIndex(),stock);
            }

        }catch(NumberFormatException e){
            System.out.println("Stock inválido");
        }
    }
        
});
        precioT.textProperty().addListener((obs, oldVal, newVal) -> {

        if(!newVal.isEmpty()){

        try{

            int precio = Integer.parseInt(newVal);

            if(edicionC.getSelectionModel().getSelectedIndex() == 0){
                juego.precioDigital= precio;
            }
            else{
                juego.precioFisico = precio;
            }

        }catch(NumberFormatException e){
            System.out.println("Stock inválido");
        }
    }
        });
    
    }
    
    public void cargarDatos(nodoVideojuego juego){
        this.juego = juego;
        String descripcion = "";
        try {
       
            InputStream is = new FileInputStream(System.getProperty("user.dir")+ "/src/datos/imagenes/" + juego.portada);
            if (is != null) {
                portada.setImage(new Image(is));
            }
            } catch (Exception e) {
            System.out.println("Error cargando imagen");
            
        }
        try {
       
            InputStream is = new FileInputStream(System.getProperty("user.dir")+ "/src/datos/imagenes/" +juego.capturas.get(0));
            if (is != null) {
                captura1.setImage(new Image(is));
            }
            } catch (Exception e) {
            System.out.println("Error cargando imagen");
            
        }
        try {
       
            InputStream is = new FileInputStream(System.getProperty("user.dir")+ "/src/datos/imagenes/"+juego.capturas.get(1));
            if (is != null) {
                captura2.setImage(new Image(is));
            }
            } catch (Exception e) {
            System.out.println("Error cargando imagen");
            
        }
        try {
       
            InputStream is = new FileInputStream(System.getProperty("user.dir")+ "/src/datos/imagenes/"+juego.capturas.get(2));
            if (is != null) {
                captura3.setImage(new Image(is));
            }
            } catch (Exception e) {
            System.out.println("Error cargando imagen");
            
        }
        
        idTxt.setText("ID: " + juego.id);
        nombreTxt.setText("Nombre: " + juego.nombre);
        promedioTxt.setText("Promedio de calificación: " + juego.promedioCal());
        plataformaC.getItems().addAll(juego.plataforma);
        plataformaC.getSelectionModel().selectFirst();
        edicionC.getItems().add("Digital");
        edicionC.getItems().add("Fisico");
        edicionC.getSelectionModel().selectFirst();
        
        if(edicionC.getSelectionModel().getSelectedIndex() == 0){
            stockT.setText(juego.stockDigital.get(plataformaC.getSelectionModel().getSelectedIndex()).toString());
            precioT.setText(""+ (int) juego.precioDigital);
        }
        else if(edicionC.getSelectionModel().getSelectedIndex() == 1){
            stockT.setText(juego.stockFisico.get(plataformaC.getSelectionModel().getSelectedIndex()).toString());
            precioT.setText(""+ (int) juego.precioFisico);
        }
        
        descripcion += juego.descripcion + "\n\n";
        descripcion += "Categoria: " + juego.categoria+ "\n\n";
        descripcion += "Etiquetas: " + String.join(",", juego.etiquetas)+ "\n\n";
        descripcion += "Idiomas: " + String.join(",", juego.idioma)+ "\n\n";
        descripcion += "Fecha de lanzamiento: "+ juego.fechaLanzamiento+ "\n\n";
        descripcion += "Peso: " + juego.peso + "\n\n";
        descripcion += "Requisitos minimos: " + juego.requisitosMin+ "\n\n";
        descripcion += "Requisitos recomendados: " + juego.requisitosRec;
        
        TextAreaD.setText(descripcion);
        
    }
    
    private void seleccionado (){
        if(juego == null) return;
            if(edicionC.getSelectionModel().getSelectedIndex() == 0){
            stockT.setText(juego.stockDigital.get(plataformaC.getSelectionModel().getSelectedIndex()).toString());
            precioT.setText(""+ (int) juego.precioDigital);
        }
        else if(edicionC.getSelectionModel().getSelectedIndex() == 1){
            stockT.setText(juego.stockFisico.get(plataformaC.getSelectionModel().getSelectedIndex()).toString());
            precioT.setText(""+(int) juego.precioFisico);
        }
    }
    
}
