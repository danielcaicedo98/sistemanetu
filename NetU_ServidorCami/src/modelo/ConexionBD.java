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

/**
 *
 * @author Admin
 */
public class ConexionBD {
    

    public static Connection coneccion;
    static final String driver = "com.mysql.cj.jdbc.Driver";
    static final String user = "root";
    static final String password = "";
    static final String url = "jdbc:mysql://localhost:3306/sistemanetu?serverTimezone=UTC";

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
