/*
 * Programa	: ConexionBD.java
 * Fecha	: 10/04/2016
 * Objetivo	: Manejar las operaciones de conexión a la BD
 * Programador	: Luis Yovany Romo Portilla
 */

package modelo;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 *
 * @author Admin
 */
public class ConexionBD {
    

    public static Connection coneccion;
    static final ResourceBundle rb = ResourceBundle.getBundle("modelo.jdbc");
    static final String driver = rb.getString("driver");
    static final String user = rb.getString("usr");
    static final String password = rb.getString("pwd");
    static final String url = rb.getString("url");

    
    public static void Conectar() {

        try {
            coneccion = null;

            Class.forName(driver);

            try {
                coneccion = DriverManager.getConnection(url, user, password);

            } catch (SQLException ex) {
                System.out.println("SQL EX Ocurrió este error: " + ex.getMessage());
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("CNF Ocurrió este error: " + ex.getMessage());
        }

    }

    /*public static void main(String[] args) {
        ConexionBD.Conectar();
        Connection conexion = ConexionBD.coneccion;
        String consulta = "SELECT * FROM dependencia";
        ResultSet resultado = null;

        try {
            PreparedStatement statement = conexion.prepareStatement(consulta);

            resultado = statement.executeQuery();

        } catch (SQLException ex) {
            System.out.println("ERROR AL CONSULTAR DEPENDENCIA " + ex.getMessage());
        }

    }*/
}
