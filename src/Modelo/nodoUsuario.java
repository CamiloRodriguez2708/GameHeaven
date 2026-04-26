/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.Date;

/**
 *
 * @author USUARIO
 */
public class nodoUsuario {
    
    // DATOS DEL USUARIO
    public String nombreU, correo, contrasena, nombreR, tipoD
            , numD, genero, telefono, pregunta, nomBanco, tipoMetodoP,
            codigoP, departamento, municipio, dirrecion;
    
    public Date fechaNacimiento;
    
    public int numTarjeta, iD, tipo;
    
    public nodoUsuario sig, at;

    public nodoUsuario(String nombreU, String correo, String contrasena, String nombreR, String tipoD, String numD, String genero, String telefono, String pregunta, String nomBanco, String tipoMetodoP, String codigoP, String departamento, String municipio, String dirrecion, Date fechaNacimiento, int numTarjeta, int iD, int tipo) {
        this.nombreU = nombreU;
        this.correo = correo;
        this.contrasena = contrasena;
        this.nombreR = nombreR;
        this.tipoD = tipoD;
        this.numD = numD;
        this.genero = genero;
        this.telefono = telefono;
        this.pregunta = pregunta;
        this.nomBanco = nomBanco;
        this.tipoMetodoP = tipoMetodoP;
        this.codigoP = codigoP;
        this.departamento = departamento;
        this.municipio = municipio;
        this.dirrecion = dirrecion;
        this.fechaNacimiento = fechaNacimiento;
        this.numTarjeta = numTarjeta;
        this.sig = null;
        this.at = null;
        this.iD = iD;
        this.tipo = tipo;
    }
    
    
}
