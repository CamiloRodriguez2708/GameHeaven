package Controlador;

import Modelo.nodoUsuario;
import java.util.Date;

public class listaUsuarios {
    private static nodoUsuario cab;
    private static boolean datosDemoCargados = false;

    public listaUsuarios() {
        if (!datosDemoCargados) {
            cargarDatosDemo();
            datosDemoCargados = true;
        }
    }

    private void cargarDatosDemo() {
        Date hoy = new Date();
        agregarInicio(
                "Usuario Demo",
                "usuario@gameheaven.com",
                "1234",
                "Usuario Demo",
                "CC",
                "1000000000",
                "Sin especificar",
                "3000000000",
                "Pregunta",
                "",
                "Tarjeta",
                "000",
                "Cundinamarca",
                "Bogotá",
                "",
                hoy,
                0,
                1001,
                0
        );

        agregarInicio(
                "Administrador",
                "admin@gameheaven.com",
                "admin123",
                "Administrador",
                "CC",
                "1000000001",
                "Sin especificar",
                "3000000001",
                "Pregunta",
                "",
                "Tarjeta",
                "000",
                "Cundinamarca",
                "Bogotá",
                "",
                hoy,
                0,
                1002,
                1
        );
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

    public nodoUsuario crearNodo(String nombreU, String correo, String contraseña, String nombreR, String tipoD, String numD, String genero, String telefono, String pregunta, String nomBanco, String tipoMetodoP, String codigoP, String departamento, String municipio, String dirrecion, Date fechaNacimiento, int numTarjeta, int iD, int tipo) {
        if (buscarCorr(correo) != null) {
            return null;
        }
        try {
            return new nodoUsuario(nombreU, correo, contraseña, nombreR, tipoD, numD, genero, telefono, pregunta, nomBanco, tipoMetodoP, codigoP, departamento, municipio, dirrecion, fechaNacimiento, numTarjeta, iD, tipo);
        } catch (Exception e) {
            return null;
        }
    }

    public void agregarInicio(String nombreU, String correo, String contrasena, String nombreR, String tipoD, String numD, String genero, String telefono, String pregunta, String nomBanco, String tipoMetodoP, String codigoP, String departamento, String municipio, String dirrecion, Date fechaNacimiento, int numTarjeta, int iD, int tipo) {
        nodoUsuario info = crearNodo(nombreU, correo, contrasena, nombreR, tipoD, numD, genero, telefono, pregunta, nomBanco, tipoMetodoP, codigoP, departamento, municipio, dirrecion, fechaNacimiento, numTarjeta, iD, tipo);

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
}
