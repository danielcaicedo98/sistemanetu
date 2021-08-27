/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Paquetes;

/**
 *
 * @author Dylan Joel
 * Clase PetIniciarSesion. La clase hereda de Paquete. Su función es la de hacer
 * una peticion de inicio de sesion desde el cliente
 */
public class PetIniciarSesion extends Paquete {
    
    private int codigo;
    private String password;

    public PetIniciarSesion(int codigo, String password) {
        this.codigo = codigo;
        this.password = password;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getPassword() {
        return password;
    }
    
}
