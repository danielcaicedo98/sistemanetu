/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import modelo.ConexionBD;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Paquetes.Paquete;
import Paquetes.PetIniciarSesion;
import Paquetes.ResIniciarSesion;

/**
 *
 * @author USUARIO
 */
public class GestorEmpleado {

    public ResIniciarSesion iniciarSesion(PetIniciarSesion peticion) {

        String mensaje = "";

        ResIniciarSesion respuesta = new ResIniciarSesion();

        String cExiteEmpleado = "SELECT * FROM empleado WHERE codigo_Empleado = "
                + peticion.getCodigo();

        String cPassCorrect = "SELECT * FROM login, empleado \n"
                + "WHERE login.codigo_Empleado = empleado.codigo_Empleado \n"
                + "AND login.codigo_Empleado = " + peticion.getCodigo()+"\n"
                + "AND contraseña = " +"'" +peticion.getPassword()+"'";
        
      

        String cCambiarEstado = "UPDATE login SET estado = 1 \n"
                + "WHERE login.codigo_Empleado = " + peticion.getPassword();

        try {
            ResultSet result;
            
            result = ejecutarQuery(cExiteEmpleado);

            if (!result.next()) {

                mensaje = "No existe un empleado registrado con el código "
                        + peticion.getCodigo()+"\n";

            }

            result = ejecutarQuery(cPassCorrect);

            if (!result.next() && mensaje.isEmpty()) {
                
                mensaje += "La contraseña ingresada es incorrecta";

            }
            
            if(mensaje.isEmpty()){
                
                
                
            }else{
                
                respuesta.setExito(Paquete.error);
                
                
                
            }
            
            respuesta.setMensaje(mensaje);
            
            

        } catch (SQLException ex) {
            System.out.println("Error al iniciar sesion BD " + ex.getMessage());
        }

        return respuesta;

    }

    public ResultSet ejecutarQuery(String consulta) {
        
        System.out.println(consulta);

        ResultSet result = null;
        PreparedStatement stment;

        try {
            stment = ConexionBD.coneccion.prepareStatement(consulta);
            result = stment.executeQuery();
        } catch (SQLException ex) {
            System.out.println("Error al ejecutar el query " + ex.getMessage());
        }

        return result;

    }

    public void ejecutarUpdate(String update) {
        
        PreparedStatement stment;
        int bandera = -1;

        try {
            stment = ConexionBD.coneccion.prepareStatement(update);
            bandera = stment.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error al ejecutar el query " + ex.getMessage());
        }
        
        if(bandera == 1){
            System.out.println("Se actualizó el estado de un empleado");
        }

    }
    
    /*public static void main(String[] args){
        
        ConexionBD.Conectar();
        
        GestorEmpleado ges = new GestorEmpleado();
        
        PetIniciarSesion peticion = new PetIniciarSesion(1925333, "password2");
        ResIniciarSesion res = ges.iniciarSesion(peticion);
        
        JOptionPane.showMessageDialog(null, res.getMensaje() + "-"+res.getExito());
        
    };*/

}
