package Modelo;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author yirley & camilo
 */
public class nodoVideojuego {
    
   
    public int id;
    public String nombre;
    public ArrayList<String> plataforma;     
    public String edicion;         
    public String categoria;       
    public ArrayList<String> etiquetas;       
    public String descripcion;     
    
    //INFO TECNICA
    public String peso;            
    public String requisitosMin;  
    public String requisitosRec;   
    public ArrayList<String> idioma;          
    public String fechaLanzamiento; 

    //imagenes
    public String portada;         
    public ArrayList<String> capturas;      
            

   
    public float precioDigital;
    public float precioFisico;
    
    public ArrayList<Integer> stockDigital;
    public ArrayList<Integer> stockFisico;
    
 
    public ArrayList<Float> calificaciones;      

  
    public nodoVideojuego sig;     
    public nodoVideojuego ant;     

    
    public nodoVideojuego(int id, String nombre, ArrayList<String> plataforma, String edicion, String categoria, 
                          ArrayList<String> etiquetas, String descripcion, String peso, String requisitosMin, 
                          String requisitosRec, ArrayList<String> idioma, String fechaLanzamiento, String portada, 
                          ArrayList<String> capturas, float precioDigital, float precioFisico,
                          ArrayList<Integer> stockDigital, ArrayList<Integer> stockFisico, ArrayList<Float> calificaciones) {
        this.id = id;
        this.nombre = nombre;
        this.plataforma = plataforma;
        this.edicion = edicion;
        this.categoria = categoria;
        this.etiquetas = etiquetas;
        this.descripcion = descripcion;
        this.peso = peso;
        this.requisitosMin = requisitosMin;
        this.requisitosRec = requisitosRec;
        this.idioma = idioma;
        this.fechaLanzamiento = fechaLanzamiento;
        this.portada = portada;
        this.capturas = capturas;
        this.precioDigital = precioDigital;
        this.precioFisico = precioFisico;
        this.stockDigital = stockDigital;
        this.stockFisico = stockFisico;
        this.calificaciones = calificaciones;
        
       
        this.sig = null;
        this.ant = null;
    }
    
    public float promedioCal(){
    float aux = 0;
    
    if (calificaciones == null || calificaciones.isEmpty()){
        return 0;
    }
    
    for(int i = 0; i<calificaciones.size(); i++){
        aux += calificaciones.get(i);
    }
    return aux/calificaciones.size();
}
}
