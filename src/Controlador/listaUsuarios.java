/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
import Modelo.nodoUsuario;
import java.util.Date;
import javax.swing.JTextField;
/**
 *
 * @author Camilo Rodriguez
 */
public class listaUsuarios {
    nodoUsuario cab;
    
    public listaUsuarios(){
    cab = null;
    }
    public nodoUsuario buscarID(String a){
        if(cab == null){
            return null;
        }
        else{
            nodoUsuario b = cab;
            do{
                if(b.iD == Integer.parseInt(a)){
                    return b;
                }
                else{
                    b=b.sig;
                }
            } while(b != cab);
            return null;
        }
    }
    
    public nodoUsuario buscarCorr(String a){
        if(cab == null){
            return null;
        }
        else{
            nodoUsuario b = cab;
            do{
                if(b.correo.equals(a)){
                    return b;
                }
                else{
                    b=b.sig;
                }
            } while(b != cab);
            return null;
        }
    }
    
    public nodoUsuario crearNodo(String nombreU, String correo, String contraseña, String nombreR, String tipoD, String numD, String genero, String telefono, String pregunta, String nomBanco, String tipoMetodoP, String codigoP, String departamento, String municipio, String dirrecion, Date fechaNacimiento, int numTarjeta, int iD, int tipo){
        
        if (buscarCorr(correo) != null){
           
            return null;
        }
        else{
            
            
            try{
                nodoUsuario n = new nodoUsuario(nombreU, correo, contraseña, nombreR, tipoD, numD, genero, telefono, pregunta, nomBanco, tipoMetodoP, codigoP, departamento, municipio, dirrecion, fechaNacimiento, numTarjeta, iD, tipo);
                return n;
            }
            catch (Exception e){
                return null;
            }
            
        }
    }
    
    
    
    public void agregarInicio(String nombreU, String correo, String contrasena, String nombreR, String tipoD, String numD, String genero, String telefono, String pregunta, String nomBanco, String tipoMetodoP, String codigoP, String departamento, String municipio, String dirrecion, Date fechaNacimiento, int numTarjeta, int iD, int tipo){
        nodoUsuario info = crearNodo(nombreU, correo, contrasena, nombreR, tipoD, numD, genero, telefono, pregunta, nomBanco, tipoMetodoP, codigoP, departamento, municipio, dirrecion, fechaNacimiento, numTarjeta, iD, tipo);
        
        if(info != null){
            if(cab == null){
                cab = info;
                cab.sig = cab;
                cab.at = cab;
            }
            
            else {
                info.sig = cab;
                info.at = cab.at;
                cab.at.sig = info;
                cab.at = info;
                cab = info;
                
            }
        }
    }
    
    public boolean eliminar(String iD){
        nodoUsuario aux = buscarID(iD);
        if (aux == null){
            return false;
        }
        else{
        if(cab== null){
            return false;
        }
        else if(cab != null && cab.sig == cab){
            cab.sig = null;
            cab.at = null;
            cab = null;
            return true;
        }
        else if(aux == cab && cab != null && cab.sig != null){
            cab.sig.at =cab.at;
            cab.at.sig = cab.sig;
            cab = cab.sig;
            aux.sig = null;
            aux.at = null;
            aux =null;
            return true;
        }
        else if(aux == cab.at && cab != null && cab.sig != null){
            cab.at.at.sig = cab;
            cab.at = cab.at.at;
            aux.at = null;
            aux.sig= null;
            aux = null;
            return true;
        }
        else if(aux != cab.at && aux != cab && cab != null && cab.sig != null){
            aux.sig.at = aux.at;
            aux.at.sig = aux.sig;
            aux.sig = null;
            aux.at = null;
            aux = null;
            return true;
        }
        return false;
        }
    }
    
    public nodoUsuario iniciarSesion(String correo, String contrasena){
        nodoUsuario aux = cab;
        if (aux == null){
            return null;
        }
        else{
        do{
            if (correo.equals(aux.correo) && contrasena.equals(aux.contrasena)){
                return aux;
            }
            aux = aux.sig;
        } while(aux != cab);
        return null;
        }
    }
    
}

