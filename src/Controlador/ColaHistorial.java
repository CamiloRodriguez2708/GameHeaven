package Controlador;

import Modelo.nodoVideojuego;
import java.util.ArrayList;
import java.util.List;


/**
 * Cola FIFO para historial reutilizando nodoVideojuego como nodo base.
 * Esta clase no modifica ninguna clase existente; solo agrega la estructura.
 */
public class ColaHistorial {

    private nodoVideojuego frente;
    private nodoVideojuego finalCola;
    private int tamanio;

    public ColaHistorial() {
        this.frente = null;
        this.finalCola = null;
        this.tamanio = 0;
    }

    public int size() {
        return tamanio;
    }

    public boolean estaVacia() {
        return tamanio == 0;
    }

    public nodoVideojuego getFrente() {
        return frente;
    }

    public nodoVideojuego getFinalCola() {
        return finalCola;
    }

    public void encolar(nodoVideojuego videojuego) {
        if (videojuego == null) {
            return;
        }

        nodoVideojuego copia = copiarVideojuego(videojuego);

        if (frente == null) {
            frente = finalCola = copia;
        } else {
            finalCola.sig = copia;
            finalCola = copia;
        }
        tamanio++;
    }

    public nodoVideojuego desencolar() {
        if (frente == null) {
            return null;
        }

        nodoVideojuego salida = frente;
        frente = frente.sig;

        if (frente == null) {
            finalCola = null;
        }

        salida.sig = null;
        salida.ant = null;
        tamanio--;
        return salida;
    }

    public nodoVideojuego verFrente() {
        return frente;
    }

    public void limpiar() {
        nodoVideojuego aux = frente;
        while (aux != null) {
            nodoVideojuego siguiente = aux.sig;
            aux.sig = null;
            aux.ant = null;
            aux = siguiente;
        }
        frente = null;
        finalCola = null;
        tamanio = 0;
    }

    public List<nodoVideojuego> toList() {
        ArrayList<nodoVideojuego> lista = new ArrayList<>();
        nodoVideojuego aux = frente;
        while (aux != null) {
            lista.add(aux);
            aux = aux.sig;
        }
        return lista;
    }

    private nodoVideojuego copiarVideojuego(nodoVideojuego original) {
        nodoVideojuego copia = new nodoVideojuego(
                original.id,
                original.nombre,
                original.plataforma,
                original.edicion,
                original.categoria,
                original.etiquetas,
                original.descripcion,
                original.peso,
                original.requisitosMin,
                original.requisitosRec,
                original.idioma,
                original.fechaLanzamiento,
                original.portada,
                original.capturas,
                original.precioDigital,
                original.precioFisico,
                original.stockDigital,
                original.stockFisico,
                original.calificaciones
        );
        copia.sig = null;
        copia.ant = null;
        return copia;
    }
}
