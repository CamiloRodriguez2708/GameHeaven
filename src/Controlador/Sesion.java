/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
import Modelo.nodoUsuario;
/**
 *
 * @author Camilo Rodriguez
 */
public class Sesion {
    public static nodoUsuario usuarioActual;
    public static listaUsuarios lista = new listaUsuarios();
    public static PilaCarrito carrito = new PilaCarrito();
    public static String buscar = "";
    public static String paginaActual = "/Vista/paginaPrincipal.fxml";
    public static String paginaAnterior = "";
}
