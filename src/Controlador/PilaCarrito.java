/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.nodoVideojuego;
import java.util.ArrayList;
import java.util.Stack;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Yirley & Camilo
 */
public class PilaCarrito {
    
    private Stack<nodoVideojuego> carrito;
    
    public PilaCarrito() {
    carrito = new Stack<>();
}
    public void agregarJuego(nodoVideojuego juego) {
    carrito.push(juego);
}
    public nodoVideojuego eliminarJuego() {

    if (!carrito.isEmpty()) {
        nodoVideojuego juego = carrito.pop();
        restaurarStock(juego);
        return juego;
    }

    return null;
}
    public boolean eliminarJuego(nodoVideojuego juego) {

    if (juego != null && carrito.remove(juego)) {
        restaurarStock(juego);
        return true;
    }

    return false;
}
    public ArrayList<nodoVideojuego> obtenerJuegos() {
    return new ArrayList<>(carrito);
}
    public float calcularTotal() {

    float total = 0;

    for (nodoVideojuego juego : carrito) {
        if(juego.edicion.equals("Digital")){
            total += juego.precioDigital;
        }
        else if(juego.edicion.equals("Fisico")){
            total += juego.precioFisico;
        }
    }

    return total;
}
    public int cantidadJuegos() {
    return carrito.size();
}
    
    public void vaciar(){
        while(!carrito.isEmpty()){
            nodoVideojuego juego = carrito.pop();
            restaurarStock(juego);
        }
    
    
    
}
    public void confirmarCompra(){
        while(!carrito.isEmpty()){
            nodoVideojuego juego = carrito.pop();
            
            
            String datos = juego.id+"~"+juego.plataforma.get(0)+"~"+juego.edicion+"~"+ LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+"~";
            if(juego.edicion.equalsIgnoreCase("Fisico")){
                datos += juego.precioFisico;
            }
            if(juego.edicion.equalsIgnoreCase("Digital")){
                datos += juego.precioDigital;
            }
            if(Sesion.usuarioActual.historial.get(0).equals("#")){
            Sesion.usuarioActual.historial.remove(0);
            }
            Sesion.usuarioActual.historial.add(datos);
        }
        
        Sesion.lista.guardarArchivo();
    }
    
    private void restaurarStock(nodoVideojuego juego){
        nodoVideojuego aux = Sistema.listaJuegos.buscarPorNombre(juego.nombre);
        try{
        if(aux != null){
                for(int i = 0; i < aux.plataforma.size(); i++){
                    if(juego.plataforma.get(0).equalsIgnoreCase(aux.plataforma.get(i))){
                        if(juego.edicion.equals("Digital")){
                            aux.stockDigital.set(i, aux.stockDigital.get(i)+1);
                            Sistema.listaJuegos.guardarArchivo();
                        }
                        else if (juego.edicion.equals("Fisico")){
                            aux.stockFisico.set(i, aux.stockFisico.get(i)+1);
                            Sistema.listaJuegos.guardarArchivo();
                        }
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
}
}
