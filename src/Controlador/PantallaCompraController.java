/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.nodoVideojuego;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

/**
 *
 * @author Camilo Rodriguez & Sofia
 */
public class PantallaCompraController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private nodoVideojuego juego = Sistema.seleccionado;
    boolean favoritos = false;
    @FXML ComboBox<String> plataforma;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        buscarFavoritoID();
    } 
    private void AgregarFavoritos(){
        if(favoritos == false){
            Sesion.usuarioActual.listaDeseados.add(String.valueOf(juego.id));
        }
        else{
            Sesion.usuarioActual.listaDeseados.remove(buscarFavoritoID());
        }
    }
    
    private int buscarFavoritoID(){
        for(int i=0; i< Sesion.usuarioActual.listaDeseados.size(); i++){
            if(juego.id == Integer.parseInt(Sesion.usuarioActual.listaDeseados.get(i))){
                favoritos = true;
                return i;
            }
        }
        return -1;
    }
    
    private void AgregarFisico(){
        
        juego.stockFisico.set(plataforma.getSelectionModel().getSelectedIndex(),juego.stockFisico.get(plataforma.getSelectionModel().getSelectedIndex()) - 1);
        nodoVideojuego insertar = new nodoVideojuego( juego.id, juego.nombre, new ArrayList<String>(juego.plataforma), "Fisico", juego.categoria,
                                                      juego.etiquetas, juego.descripcion, juego.peso, juego.requisitosMin,
                                                      juego.requisitosRec, juego.idioma, juego.fechaLanzamiento, juego.portada, 
                                                      juego.capturas, juego.precioDigital, juego.precioFisico, juego.stockDigital, 
                                                      juego.stockFisico, juego.calificaciones);
        insertar.plataforma.clear();
        insertar.plataforma.add(plataforma.getSelectionModel().getSelectedItem());
        Sesion.carrito.agregarJuego(insertar);
    }
    
    private void AgregarDigital(){
        juego.stockDigital.set(plataforma.getSelectionModel().getSelectedIndex(),juego.stockDigital.get(plataforma.getSelectionModel().getSelectedIndex()) - 1);
        nodoVideojuego insertar = new nodoVideojuego( juego.id, juego.nombre, new ArrayList<String>(juego.plataforma), "Digital", juego.categoria,
                                                      juego.etiquetas, juego.descripcion, juego.peso, juego.requisitosMin,
                                                      juego.requisitosRec, juego.idioma, juego.fechaLanzamiento, juego.portada, 
                                                      juego.capturas, juego.precioDigital, juego.precioFisico, juego.stockDigital, 
                                                      juego.stockFisico, juego.calificaciones);
        insertar.plataforma.clear();
        insertar.plataforma.add(plataforma.getSelectionModel().getSelectedItem());
        Sesion.carrito.agregarJuego(insertar);
    }
    
}
