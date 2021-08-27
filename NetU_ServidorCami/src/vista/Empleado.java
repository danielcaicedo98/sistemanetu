/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

/**
 *
 * @author Camila
 */

public class Empleado {
    
    //Atributos del Empleado
    private int codigo;
    private String nombre;
    private String correo;
    private String sexo;
    private String nDependencia;
    private String nSubdependencia;
    private int dependencia;
    private int subDependencia;
    
    
    
    //Constructores
    
    public Empleado(){
        
    }
    
    public Empleado(int codigo, String nombre, String correo, String sexo, int dependencia, int subDependencia) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.correo = correo;
        this.sexo = sexo;
        this.dependencia = dependencia;
        this.subDependencia = subDependencia;
    }

    public int getCodigo() {
        return codigo;
    }
    
    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getnDependencia() {
        return nDependencia;
    }

    public void setnDependencia(String nDependencia) {
        this.nDependencia = nDependencia;
    }

    public String getnSubdependencia() {
        return nSubdependencia;
    }

    public void setnSubdependencia(String nSubdependencia) {
        this.nSubdependencia = nSubdependencia;
    }
    
    

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getDependencia() {
        return dependencia;
    }

    public void setDependencia(int dependencia) {
        this.dependencia = dependencia;
    }

    public int getSubDependencia() {
        return subDependencia;
    }

    public void setSubDependencia(int subDependencia) {
        this.subDependencia = subDependencia;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    
    
    

    
    
    
    
}
