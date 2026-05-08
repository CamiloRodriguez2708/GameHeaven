package Controlador;

import Modelo.nodoUsuario;
import Modelo.nodoVideojuego;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class listaUsuarios {
    private  nodoUsuario cab;
    

    public listaUsuarios() {
        insertarLista(cargarUsuarios());
    }
    
    public nodoUsuario buscarID(String a) {
        if (cab == null) {
            return null;
        }
        nodoUsuario b = cab;
        do {
            if (b.iD == Integer.parseInt(a)) {
                return b;
            }
            b = b.sig;
        } while (b != cab);
        return null;
    }

    public nodoUsuario buscarCorr(String a) {
        if (cab == null) {
            return null;
        }
        nodoUsuario b = cab;
        do {
            if (b.correo.equalsIgnoreCase(a)) {
                return b;
            }
            b = b.sig;
        } while (b != cab);
        return null;
    }

    public nodoUsuario crearNodo(String nombreU, String correo, String contraseña, String nombreR, String tipoD, String numD, String genero, String telefono, String nomBanco, String tipoMetodoP, String codigoP, String departamento, String municipio, String dirrecion, Date fechaNacimiento, int numTarjeta, int iD, int tipo, ArrayList<String> ListaDeseados, ArrayList<String> historial ) {
        if (buscarCorr(correo) != null) {
            return null;
        }
        try {
            return new nodoUsuario(nombreU, correo, contraseña, nombreR, tipoD, numD, genero, telefono, nomBanco, tipoMetodoP, codigoP, departamento, municipio, dirrecion, fechaNacimiento, numTarjeta, iD, tipo, ListaDeseados, historial);
        } catch (Exception e) {
            return null;
        }
    }

    public void agregarInicio(String nombreU, String correo, String contrasena, String nombreR, String tipoD, String numD, String genero, String telefono, String nomBanco, String tipoMetodoP, String codigoP, String departamento, String municipio, String dirrecion, Date fechaNacimiento, int numTarjeta, int iD, int tipo, ArrayList<String> ListaDeseados, ArrayList<String> historial) {
        nodoUsuario info = crearNodo(nombreU, correo, contrasena, nombreR, tipoD, numD, genero, telefono, nomBanco, tipoMetodoP, codigoP, departamento, municipio, dirrecion, fechaNacimiento, numTarjeta, iD, tipo, ListaDeseados, historial);

        if (info != null) {
            if (cab == null) {
                cab = info;
                cab.sig = cab;
                cab.at = cab;
            } else {
                info.sig = cab;
                info.at = cab.at;
                cab.at.sig = info;
                cab.at = info;
                cab = info;
            }
        }
    }

    public boolean eliminar(String iD) {
        nodoUsuario aux = buscarID(iD);
        if (aux == null) {
            return false;
        }
        if (cab == null) {
            return false;
        } else if (cab.sig == cab) {
            cab.sig = null;
            cab.at = null;
            cab = null;
            return true;
        } else if (aux == cab) {
            cab.sig.at = cab.at;
            cab.at.sig = cab.sig;
            cab = cab.sig;
            return true;
        } else if (aux == cab.at) {
            cab.at.at.sig = cab;
            cab.at = cab.at.at;
            return true;
        } else {
            aux.sig.at = aux.at;
            aux.at.sig = aux.sig;
            return true;
        }
    }
    
    public int calcularID(){
        if(cab == null){
            return 1;
        }
        nodoUsuario aux = cab;
        int max = Integer.MIN_VALUE;
        
       do{
           if(aux.iD > max){
               max = aux.iD;
           }
           aux = aux.sig;
       }while(aux != cab);
       return max+1;
    }

    public nodoUsuario iniciarSesion(String correo, String contrasena) {
        if (cab == null) {
            return null;
        }
        nodoUsuario aux = cab;
        do {
            if (correo.equalsIgnoreCase(aux.correo) && contrasena.equals(aux.contrasena)) {
                return aux;
            }
            aux = aux.sig;
        } while (aux != cab);
        return null;
    }
    
    private List<nodoUsuario> cargarUsuarios() {

    List<nodoUsuario> lista = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(
            new InputStreamReader(getClass().getResourceAsStream("/Datos/ListaUsuarios.txt")))) {

        String linea;

        while ((linea = br.readLine()) != null) {

            if (linea.trim().isEmpty()) continue;

            String[] partes = linea.split("//");

            if (partes.length < 20) continue;

            String nombreU = partes[0];
            String correo = partes[1];
            String contrasena = partes[2];
            String nombreR = partes[3];
            String tipoD = partes[4];
            String numD = partes[5];
            String genero = partes[6];
            String telefono = partes[7];
            String nomBanco = partes[8];
            String tipoMetodoP = partes[9];
            String codigoP = partes[10];
            String departamento = partes[11];
            String municipio = partes[12];
            String direccion = partes[13];

            Date fechaNacimiento = new Date(Long.parseLong(partes[14]));
            int numTarjeta = Integer.parseInt(partes[15]);
            int id = Integer.parseInt(partes[16]);
            int tipo = Integer.parseInt(partes[17]);

            ArrayList<String> listaDeseados = parseStringList(partes[18]);
            ArrayList<String> historial = parseStringList(partes[19]);

            nodoUsuario usuario = new nodoUsuario(
                    nombreU, correo, contrasena, nombreR, tipoD, numD,
                    genero, telefono, nomBanco, tipoMetodoP, codigoP,
                    departamento, municipio, direccion, fechaNacimiento,
                    numTarjeta, id, tipo, listaDeseados, historial
            );

            lista.add(usuario);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return lista;
}
     private ArrayList<String> parseStringList(String texto) {
        return new ArrayList<>(Arrays.asList(texto.split("\\|")));
    }
    public void insertarLista(List<nodoUsuario> usuarios) {
    for (nodoUsuario usuario : usuarios) {
        agregarInicio(usuario.nombreU, usuario.correo, usuario.contrasena, usuario.nombreR, usuario.tipoD, usuario.numD, usuario.genero, usuario.telefono, usuario.nomBanco, usuario.tipoMetodoP, usuario.codigoP,
                usuario.departamento, usuario.municipio, usuario.dirrecion, usuario.fechaNacimiento, 
                usuario.numTarjeta, usuario.iD, usuario.tipo, usuario.listaDeseados, usuario.historial);
    }
    
    
}
    public void guardarArchivo() {
    try (BufferedWriter bw = new BufferedWriter(
            new FileWriter("src/Datos/ListaUsuarios.txt"))) {

        nodoUsuario temp = cab;

        if (temp != null) {
            do{
            String linea = temp.nombreU + "//" +
                    temp.correo + "//" +
                    temp.contrasena + "//" +
                    temp.nombreR + "//" +
                    temp.tipoD + "//" +
                    temp.numD + "//" +
                    temp.genero + "//" +
                    temp.telefono + "//" +
                    temp.nomBanco + "//" +
                    temp.tipoMetodoP + "//" +
                    temp.codigoP + "//" +
                    temp.departamento + "//" +
                    temp.municipio + "//" +
                    temp.dirrecion + "//" +
                    temp.fechaNacimiento.getTime() + "//" +
                    temp.numTarjeta + "//" +
                    temp.iD + "//" +
                    temp.tipo + "//" +
                    listaToString(temp.listaDeseados) + "//" +
                    listaToString(temp.historial);

            bw.write(linea);
            bw.newLine();

            temp = temp.sig;
            }while (temp != cab);
        }

    } catch (IOException e) {
        e.printStackTrace();
    }
}
    private String listaToString(ArrayList<String> lista) {
    if (lista == null) return "";
    return String.join(",", lista);
}
    
    
    public void modificar(nodoUsuario nuevo){
       nodoUsuario aux = buscarID(String.valueOf(nuevo.iD));
       aux = nuevo;
    }
}
