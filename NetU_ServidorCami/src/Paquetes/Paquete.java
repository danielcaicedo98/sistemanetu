/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Paquetes;

import java.io.Serializable;

/**
 *
 * @author Dylan Caicedo Clase paquete. Padre de todos los paquetes.
 */
public class Paquete implements Serializable {

    public static final int petIniciarSesion = 1;
    public static final int resIniciarSesion = 2;
    public static final int error = 10;

    private int tipo;

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

}
