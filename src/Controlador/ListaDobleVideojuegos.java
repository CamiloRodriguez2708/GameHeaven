package Controlador;

import Modelo.nodoVideojuego;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ListaDobleVideojuegos {

    public nodoVideojuego inicio;
    public nodoVideojuego fin;
    private int contadorID = 1;

    public ListaDobleVideojuegos() {
        this.inicio = null;
        this.fin = null;
        insertarLista(cargarDatos());
    }

    //insertar Juegos
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
    
    public nodoVideojuego buscarPorNombre(String nombre) {

    nodoVideojuego aux = inicio;

    while (aux != null) {
        if (aux.nombre.equalsIgnoreCase(nombre)) {
            return aux;
        }
        aux = aux.sig;
    }
 
    return null;
}
    public nodoVideojuego buscarPorID(int id) {

    nodoVideojuego aux = inicio;

    while (aux != null) {
        if (aux.id == id) {
            return aux;
        }
        aux = aux.sig;
    }
    return null;
    }
    
   private List<nodoVideojuego> cargarDatos() {
  

        List<nodoVideojuego> lista = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream("/Datos/ListaJuegos.txt")))) {

            String linea;

            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;

                String[] partes = linea.split("//");
                if (partes.length <19) continue;

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
                ArrayList<String> capturas = parseStringList(partes[13]);
                
                float precioD = Float.parseFloat(partes[14]);
                float precioF = Float.parseFloat(partes[15]);

                ArrayList<Integer> stockD = parseIntList(partes[16]);
                ArrayList<Integer> stockF = parseIntList(partes[17]);

                ArrayList<Float> calificaciones = parseFloatList(partes[18]);

                nodoVideojuego juego = new nodoVideojuego(
                        id, nombre, plataformas, edicion, categoria,
                        etiquetas, descripcion, peso, reqMin, reqRec,
                        idiomas, fecha, portada, capturas,
                        precioD, precioF, stockD, stockF, calificaciones);

                lista.add(juego);
            }
            br.close();
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
    public int generarID(){
        if(inicio == null){
            return 1;
        }
        nodoVideojuego aux = inicio;
        int max = Integer.MIN_VALUE;
        while(aux != null){
            if(aux.id > max){
                max = aux.id;
            }
            aux = aux.sig;
        }
        return max+1;
    }
    
    public boolean eliminar(int iD) {

    nodoVideojuego aux = inicio;

    while (aux != null) {

        if (aux.id == iD) {

            if (inicio == fin) {
                inicio = fin = null;
            } else if (aux == inicio) {
                inicio = inicio.sig;
                if (inicio != null) inicio.ant = null;
            } else if (aux == fin) {
                fin = fin.ant;
                if (fin != null) fin.sig = null;
            } else {
                aux.ant.sig = aux.sig;
                aux.sig.ant = aux.ant;
            }

            return true;
        }

        aux = aux.sig;
    }

    return false;
    
}
    public void guardarArchivo(){
        try (BufferedWriter bw = new BufferedWriter(
            new FileWriter("src/Datos/ListaJuegos.txt"))) {

        nodoVideojuego temp = inicio;

        while (temp != null) {

            
            String linea = temp.id + "//" +
                    temp.nombre + "//" +
                    listaToString(temp.plataforma) + "//" +
                    temp.edicion + "//" +
                    temp.categoria + "//" +
                    listaToString(temp.etiquetas) + "//" +
                    temp.descripcion + "//" +
                    temp.peso + "//" +
                    temp.requisitosMin + "//" +
                    temp.requisitosRec + "//" +
                    listaToString(temp.idioma) + "//" +
                    temp.fechaLanzamiento + "//" +
                    temp.portada + "//" +
                    listaToString(temp.capturas) + "//" +
                    temp.precioDigital + "//" +
                    temp.precioFisico + "//" +
                    listaIntToString(temp.stockDigital) + "//" +
                    listaIntToString(temp.stockFisico) + "//" +
                    listaFloatToString(temp.calificaciones);

            bw.write(linea);
            bw.newLine();

            temp = temp.sig;
        }
        bw.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
    public static String listaToString(ArrayList<String> lista) {
    return String.join("|", lista);
}
    public static String listaIntToString(ArrayList<Integer> lista) {
    return lista.stream().map(String::valueOf).collect(Collectors.joining("|"));
}
    public static String listaFloatToString(ArrayList<Float> lista) {
    return lista.stream().map(String::valueOf).collect(Collectors.joining("|"));
}
}


