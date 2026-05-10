package Controlador;

import Modelo.nodoVideojuego;
import java.util.ArrayList;
import java.util.List;

/**
 * Lista doble para favoritos reutilizando nodoVideojuego como nodo base.
 * Esta clase no modifica ninguna clase existente; solo agrega la estructura.
 */
public class ListaFavoritos {

    private nodoVideojuego cabeza;
    private nodoVideojuego cola;
    private int tamanio;

    public ListaFavoritos() {
        this.cabeza = null;
        this.cola = null;
        this.tamanio = 0;
    }

    public int size() {
        return tamanio;
    }

    public boolean estaVacia() {
        return tamanio == 0;
    }

    public nodoVideojuego getCabeza() {
        return cabeza;
    }

    public nodoVideojuego getCola() {
        return cola;
    }

    public void agregarAlFinal(nodoVideojuego videojuego) {
        if (videojuego == null) {
            return;
        }

        nodoVideojuego copia = copiarVideojuego(videojuego);

        if (cabeza == null) {
            cabeza = cola = copia;
        } else {
            cola.sig = copia;
            copia.ant = cola;
            cola = copia;
        }
        tamanio++;
    }

    public void agregarAlInicio(nodoVideojuego videojuego) {
        if (videojuego == null) {
            return;
        }

        nodoVideojuego copia = copiarVideojuego(videojuego);

        if (cabeza == null) {
            cabeza = cola = copia;
        } else {
            copia.sig = cabeza;
            cabeza.ant = copia;
            cabeza = copia;
        }
        tamanio++;
    }

    public nodoVideojuego buscarPorId(int id) {
        nodoVideojuego aux = cabeza;
        while (aux != null) {
            if (aux.id == id) {
                return aux;
            }
            aux = aux.sig;
        }
        return null;
    }

    public boolean contiene(int id) {
        return buscarPorId(id) != null;
    }

    public boolean eliminarPorId(int id) {
        nodoVideojuego aux = buscarPorId(id);
        if (aux == null) {
            return false;
        }

        if (aux.ant != null) {
            aux.ant.sig = aux.sig;
        } else {
            cabeza = aux.sig;
        }

        if (aux.sig != null) {
            aux.sig.ant = aux.ant;
        } else {
            cola = aux.ant;
        }

        aux.sig = null;
        aux.ant = null;
        tamanio--;
        return true;
    }

    public void limpiar() {
        nodoVideojuego aux = cabeza;
        while (aux != null) {
            nodoVideojuego siguiente = aux.sig;
            aux.sig = null;
            aux.ant = null;
            aux = siguiente;
        }
        cabeza = null;
        cola = null;
        tamanio = 0;
    }

    public List<nodoVideojuego> toList() {
        ArrayList<nodoVideojuego> lista = new ArrayList<>();
        nodoVideojuego aux = cabeza;
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
