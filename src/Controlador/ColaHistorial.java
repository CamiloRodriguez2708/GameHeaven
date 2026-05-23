package Controlador;

import Modelo.nodoVideojuego;
import java.time.LocalDate;
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

    public void encolar(String info) {
        if (info.isEmpty()||info.equals("")) {
            return;
        }

        nodoVideojuego copia = copiarVideojuego(info);

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

    private nodoVideojuego copiarVideojuego(String info) {
        String[] partes = info.split("~");
        int id = Integer.parseInt(partes[0]);
        ArrayList<String> plataforma = new ArrayList();
        plataforma.add(partes[1]);
        String edicion = partes[2];
        String fecha = partes[3];
        
        nodoVideojuego juegoOriginal = Sistema.listaJuegos.buscarPorID(id);
        nodoVideojuego copia = new nodoVideojuego(
                id,
                juegoOriginal.nombre,
                plataforma,
                edicion,
                juegoOriginal.categoria,
                juegoOriginal.etiquetas,
                juegoOriginal.descripcion,
                juegoOriginal.peso,
                juegoOriginal.requisitosMin,
                juegoOriginal.requisitosRec,
                juegoOriginal.idioma,
                fecha,
                juegoOriginal.portada,
                juegoOriginal.capturas,
                juegoOriginal.precioDigital,
                juegoOriginal.precioFisico,
                juegoOriginal.stockDigital,
                juegoOriginal.stockFisico,
                juegoOriginal.calificaciones
        );
        copia.sig = null;
        copia.ant = null;
        return copia;
    }
}
