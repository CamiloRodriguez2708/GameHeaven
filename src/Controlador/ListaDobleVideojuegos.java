package Controlador;

import Modelo.nodoVideojuego;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListaDobleVideojuegos {

    public nodoVideojuego inicio;
    public nodoVideojuego fin;
    private int contadorID = 1;

    public ListaDobleVideojuegos() {
        this.inicio = null;
        this.fin = null;
        insertarLista(cargarDatos());
    }

    //insertar losjuegos
    public void insertar(nodoVideojuego nuevo) {
      
        if (inicio == null) {
            inicio = nuevo;
            fin = nuevo;
        } else {
            fin.sig = nuevo;
            nuevo.ant = fin;
            fin = nuevo;
        }
    }

   private List<nodoVideojuego> cargarDatos() {
  

        List<nodoVideojuego> lista = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream("/Datos/ListaJuegos.txt")))) {

            String linea;

            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;

                String[] partes = linea.split(";");
                if (partes.length <20) continue;

                int id = Integer.parseInt(partes[0]);
                String nombre = partes[1];

                ArrayList<String> plataformas = parseStringList(partes[2]);
                String edicion = partes[3];
                String categoria = partes[4];
                ArrayList<String> etiquetas = parseStringList(partes[5]);

                String descripcion = partes[6];
                String peso = partes[7];
                String reqMin = partes[8];
                String reqRec = partes[9];

                ArrayList<String> idiomas = parseStringList(partes[10]);
                String fecha = partes[11];

                String portada = partes[12];
                String captura1 = partes[13];
                String captura2 = partes[14];

                float precioD = Float.parseFloat(partes[15]);
                float precioF = Float.parseFloat(partes[16]);

                ArrayList<Integer> stockD = parseIntList(partes[17]);
                ArrayList<Integer> stockF = parseIntList(partes[18]);

                ArrayList<Float> calificaciones = parseFloatList(partes[19]);

                nodoVideojuego juego = new nodoVideojuego(
                        id, nombre, plataformas, edicion, categoria,
                        etiquetas, descripcion, peso, reqMin, reqRec,
                        idiomas, fecha, portada, captura1, captura2,
                        precioD, precioF, stockD, stockF, calificaciones
                );

                lista.add(juego);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    private ArrayList<String> parseStringList(String texto) {
        return new ArrayList<>(Arrays.asList(texto.split("\\|")));
    }

    private ArrayList<Float> parseFloatList(String texto) {
        ArrayList<Float> lista = new ArrayList<>();
        for (String s : texto.split("\\|")) {
            lista.add(Float.parseFloat(s.trim()));
        }
        return lista;
    }

    private ArrayList<Integer> parseIntList(String texto) {
        ArrayList<Integer> lista = new ArrayList<>();
        for (String s : texto.split("\\|")) {
            lista.add(Integer.parseInt(s.trim()));
        }
        return lista;
    }
    
    public void insertarLista(List<nodoVideojuego> juegos) {
    for (nodoVideojuego juego : juegos) {
        insertar(juego);
    }
}
}

