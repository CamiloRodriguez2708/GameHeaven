/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.nodoVideojuego;
import java.util.Stack;

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
        return carrito.pop();
    }

    return null;
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
    
    
}
