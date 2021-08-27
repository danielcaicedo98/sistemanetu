/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import vista.Empleado;

/**
 *
 * @author Camila
 */
public class Sesion {

    //Controlador
    public Sesion() {
    }

    ;
    
    
 ////////////////////// INSERT //////////////////////////////////////////////////    
    
    
    /**
     * 
     * @param empleado Objeto de la clase Empleado a insertar en la BBDD
     * @return rs resultado de la operación INSERT
     */
    
    public int agregarEmpleado(Empleado empleado, String password) {

        Connection con = null;
        PreparedStatement pstm;
        pstm = null;
        int rs = 0;

        try {
            con = ConexionBD.coneccion;
            String sql = "INSERT INTO empleado values (?,?,?,?,?,?,?)";
            String sql1 = "INSERT INTO login values (?,?,?)";
            pstm = con.prepareStatement(sql);

            pstm.setInt(1, empleado.getCodigo());
            pstm.setString(2, empleado.getNombre());
            pstm.setString(3, empleado.getCorreo());
            pstm.setString(4, empleado.getSexo());
            pstm.setInt(5, empleado.getDependencia());
            pstm.setInt(6, empleado.getSubDependencia());
            pstm.setString(7, "");
            rs = pstm.executeUpdate();

            pstm = con.prepareStatement(sql1);

            pstm.setInt(1, empleado.getCodigo());
            pstm.setString(2, password);
            pstm.setInt(3, 0);
            pstm.executeUpdate();

            JOptionPane.showMessageDialog(null, "EMPLEADO REGISTRADO CON EXITO\n"
                    + "CODIGO: " + empleado.getCodigo() + "\nCONTRASEÑA: " + password);

            EnviaLogin(empleado.getCorreo(), empleado.getNombre(),
                    empleado.getCodigo()+"", password);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código : "
                    + ex.getErrorCode() + "\nError :" + ex.getMessage());
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Código : "
                        + ex.getErrorCode() + "\nError :" + ex.getMessage());
            }
        }

        return rs;
    }

////////////////////// UPDATE //////////////////////////////////////////////////    
    /**
     *
     * @param empleado Objeto de la clase Empleado a modificar en la BBDD
     * @return rs resultado de la operación UPDATE
     */
    public int modificarEmpleado(Empleado empleado) {

        Connection con = null;
        PreparedStatement pstm;
        pstm = null;
        int rs = 0;

        try {
            con = ConexionBD.coneccion;
            String sql = "UPDATE empleado "
                    + "SET nombre=?, correo=?, sexo=?, dependencia=?, subdependencia=? "
                    + "WHERE codigo_Empleado=?";

            pstm = con.prepareStatement(sql);
            pstm.setString(1, empleado.getNombre());
            pstm.setString(2, empleado.getCorreo());
            pstm.setString(3, empleado.getSexo());
            pstm.setInt(4, empleado.getDependencia());
            pstm.setInt(5, empleado.getSubDependencia());
            pstm.setInt(6, empleado.getCodigo());
            rs = pstm.executeUpdate();

            JOptionPane.showMessageDialog(null, "SE ACTUALIZO LOS DATOS DEL EMPLEADO CON EXITO");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código : "
                    + ex.getErrorCode() + "\nError :" + ex.getMessage());
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Código : "
                        + ex.getErrorCode() + "\nError :" + ex.getMessage());
            }
        }
        return rs;
    }

////////////////////// DELETE //////////////////////////////////////////////////    
    /**
     *
     * @param codigo código del empleado a eliminar
     * @return rs resultado de la operación DELETE
     */
    public int eliminarEmpleado(int codigo) {

        Connection con = null;
        PreparedStatement pstm;
        PreparedStatement pstm1;
        pstm = null;
        pstm1 = null;
        ResultSet rs = null;
        int res = 0;

        try {
            con = ConexionBD.coneccion;

            String sql1 = "SELECT id_Empleado FROM administrador WHERE id_Empleado=?";
            pstm1 = con.prepareStatement(sql1);
            pstm1.setInt(1, codigo);
            rs = pstm1.executeQuery();

            while (rs.next()) {
                res = rs.getInt(1);
            }

            if (res == codigo) {
                /* sql1 = "DELETE CASCADE FROM administrador WHERE id_Empleado=?";
                pstm1 = con.prepareStatement(sql1);
                pstm1.setInt(1, codigo);
                res = pstm1.executeUpdate();*/

                JOptionPane.showMessageDialog(null, "EL EMPLEADO ELIMINADO ERA UN ADMINISTRADOR");
            }

            String sql = "DELETE FROM empleado WHERE codigo_Empleado=?";
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, codigo);
            res = pstm.executeUpdate();

            JOptionPane.showMessageDialog(null, "SE ELIMINO EL EMPLEADO CON EXITO");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código : "
                    + ex.getErrorCode() + "\nError :" + ex.getMessage());
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Código : "
                        + ex.getErrorCode() + "\nError :" + ex.getMessage());
            }
        }

        return res;
    }

    ////////////////////// SELECT //////////////////////////////////////////////////
    /**
     *
     * Se listaran todos los empleados
     *
     * @return ArrayList, lista de objetos Empleado
     */
    public List<Empleado> listadoEmpleados() {

        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Empleado> listado = new ArrayList<>();

        try {

            con = ConexionBD.coneccion;

            String sql = "";
            sql = "SELECT e.nombre,e.correo,codigo_Empleado, e.sexo , "
                    + "d.nombre_Dependencia , s.nombre_Subdependencia, \n"
                    + "e.dependencia, e.subdependencia\n"
                    + "from empleado e, dependencia d, subdependencia s\n"
                    + "where e.dependencia = d.id_Dependencia\n"
                    + "and e.subdependencia = s.id_Subdependencia\n"
                    + "order by e.codigo_Empleado ;";

            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();

            while (rs.next()) {
                Empleado empleado = new Empleado();
                empleado.setCodigo(Integer.parseInt(rs.getString("codigo_Empleado")));
                empleado.setNombre(rs.getString("nombre"));
                empleado.setCorreo(rs.getString("correo"));
                empleado.setSexo(rs.getString("sexo"));
                empleado.setDependencia(rs.getInt("dependencia"));
                empleado.setSubDependencia(rs.getInt("subdependencia"));
                empleado.setnDependencia(rs.getString("nombre_Dependencia"));
                empleado.setnSubdependencia(rs.getString("nombre_Subdependencia"));
                listado.add(empleado);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código : "
                    + ex.getErrorCode() + "\nError :" + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstm != null) {
                    pstm.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Código : "
                        + ex.getErrorCode() + "\nError :" + ex.getMessage());
            }
        }
        return listado;
    }

////////////////////// METODO PARA REGISTRAR UN EMPLEADO ADMINISTRADOR ///////////////////////////////    
    /**
     *
     * @param empleado Objeto de la clase Empleado a insertar en la BBDD
     * @return rs resultado de la operación INSERT
     */
    public int agregarAdmin(Empleado empleado, String password) {

        Connection con = null;
        PreparedStatement pstm;
        PreparedStatement pstm1;
        pstm = null;
        pstm1 = null;
        int rs = 0;

        try {
            con = ConexionBD.coneccion;
            String sql = "INSERT INTO empleado VALUES (?,?,?,?,?,?,?)";
            String sqlAdmin = "INSERT INTO administrador VALUES (?)";
            String sql1 = "INSERT INTO login values (?,?,0)";

            pstm1 = con.prepareStatement(sqlAdmin);
            pstm1.setInt(1, empleado.getCodigo());

            pstm = con.prepareStatement(sql);
            pstm.setInt(1, empleado.getCodigo());
            pstm.setString(2, empleado.getNombre());
            pstm.setString(3, empleado.getCorreo());
            pstm.setString(4, empleado.getSexo());
            pstm.setInt(5, empleado.getDependencia());
            pstm.setInt(6, empleado.getSubDependencia());
            pstm.setString(7, "");

            rs = pstm.executeUpdate();
            rs = pstm1.executeUpdate();

            pstm = con.prepareStatement(sql1);

            pstm.setInt(1, empleado.getCodigo());
            pstm.setString(2, password);

            pstm.executeUpdate();

            JOptionPane.showMessageDialog(null, "EMPLEADO ADMINISTRADOR REGISTRADO CON EXITO\n"
                    + "CODIGO: " + empleado.getCodigo() + "\nCONTRASEÑA: " + password);

            EnviaLogin(empleado.getCorreo(), empleado.getNombre() + "-ADMINISTRADOR",
                     empleado.getCodigo() + "", password);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código : "
                    + ex.getErrorCode() + "\nError :" + ex.getMessage());
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Código : "
                        + ex.getErrorCode() + "\nError :" + ex.getMessage());
            }
        }

        return rs;
    }

    //Metodo que me devuelve la dependencia, subdependencia y genero del empleado
    public ArrayList<String> consultaInfo(int codigo) {

        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ArrayList<String> listado = new ArrayList<>();

        try {

            con = ConexionBD.coneccion;

            String sql = " ";
            sql = "SELECT dependencia, subdependencia, sexo FROM empleado WHERE codigo_Empleado=?";

            pstm = con.prepareStatement(sql);
            pstm.setInt(1, codigo);

            rs = pstm.executeQuery();

            while (rs.next()) {
                listado.add(0, String.valueOf(rs.getInt(1)));
                listado.add(1, String.valueOf(rs.getInt(2)));
                listado.add(2, rs.getString(3));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código : "
                    + ex.getErrorCode() + "\nError :" + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstm != null) {
                    pstm.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Código : "
                        + ex.getErrorCode() + "\nError :" + ex.getMessage());
            }
        }
        return listado;
    }

    //Metodo para listar los empleados segun la dependencia y subdependencia y/o su nombre
    /**
     *
     * Se listaran los empleados
     *
     * @param dep id de la dependencia
     * @param sub id de la subdependencia
     * @param nombre nombre del empleado a buscar
     * @return ArrayList, lista de objetos Empleado
     */
    public List<Empleado> busquedaEmpleados(int dep, int sub, String nombre) {

        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Empleado> listado = new ArrayList<>();

        //CONSULTAS
        String sql = "";

        try {

            con = ConexionBD.coneccion;

            if (nombre.isEmpty()) {

                nombre = "'%'";

            } else {
                nombre = "'" + nombre + "%'";
            }

            if (dep == 0) {

                if (sub == 0) {

                    sql = "SELECT e.nombre,e.correo,codigo_Empleado, e.sexo , "
                            + "d.nombre_Dependencia , s.nombre_Subdependencia, \n"
                            + "e.dependencia, e.subdependencia\n"
                            + "from empleado e, dependencia d, subdependencia s\n"
                            + "where e.dependencia = d.id_Dependencia\n"
                            + "and e.subdependencia = s.id_Subdependencia\n"
                            + "and e.nombre like " + nombre + "\n"
                            + "order by e.codigo_Empleado ;";

                }

            } else if (sub == 0) {

                sql = "SELECT e.nombre,e.correo,codigo_Empleado, e.sexo , "
                        + "d.nombre_Dependencia , s.nombre_Subdependencia, \n"
                        + "e.dependencia, e.subdependencia\n"
                        + "from empleado e, dependencia d, subdependencia s\n"
                        + "where e.dependencia = d.id_Dependencia\n"
                        + "and e.dependencia = " + dep + "\n"
                        + "and e.subdependencia = s.id_Subdependencia\n"
                        + "and e.nombre like " + nombre + "\n"
                        + "order by e.codigo_Empleado ;";

            } else {

                sql = "SELECT e.nombre,e.correo,codigo_Empleado, e.sexo , "
                        + "d.nombre_Dependencia , s.nombre_Subdependencia, \n"
                        + "e.dependencia, e.subdependencia\n"
                        + "from empleado e, dependencia d, subdependencia s\n"
                        + "where e.dependencia = d.id_Dependencia\n"
                        + "and e.dependencia = " + dep + "\n"
                        + "and e.subdependencia = s.id_Subdependencia\n"
                        + "and e.subdependencia = " + sub + "\n"
                        + "and e.nombre like " + nombre + "\n"
                        + "order by e.codigo_Empleado ;";

            }

            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();

            while (rs.next()) {
                Empleado empleado = new Empleado();
                empleado.setCodigo(Integer.parseInt(rs.getString("codigo_Empleado")));
                empleado.setNombre(rs.getString("nombre"));
                empleado.setCorreo(rs.getString("correo"));
                empleado.setSexo(rs.getString("sexo"));
                empleado.setDependencia(rs.getInt("dependencia"));
                empleado.setSubDependencia(rs.getInt("subdependencia"));
                empleado.setnDependencia(rs.getString("nombre_Dependencia"));
                empleado.setnSubdependencia(rs.getString("nombre_Subdependencia"));
                listado.add(empleado);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código : "
                    + ex.getErrorCode() + "\nError :" + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstm != null) {
                    pstm.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Código : "
                        + ex.getErrorCode() + "\nError :" + ex.getMessage());
            }
        }
        System.out.println(sql);
        return listado;
    }

    //AÑADIDO POR DYLAN. Para consultar las dependencias para combos (nombre, codigo)
    public ResultSet consultarDependencias() {

        Connection con = ConexionBD.coneccion;
        String consulta = "SELECT id_Dependencia as id, nombre_Dependencia as nombre FROM dependencia";
        ResultSet resultado = null;

        try {
            PreparedStatement statement = con.prepareStatement(consulta);

            resultado = statement.executeQuery();

        } catch (SQLException ex) {
            System.out.println("ERROR AL CONSULTAR DEPENDENCIA " + ex.getMessage());
        }

        return resultado;

    }

    //AÑADIDO POR DYLAN. Para consultar las subdependencias para combos (nombre, codigo)
    public ResultSet consultarSubdependencias(int id_Dependencia) {

        Connection con = ConexionBD.coneccion;
        String consulta = "SELECT id_Subdependencia as id, nombre_Subdependencia as nombre FROM subdependencia ";
        consulta += "WHERE id_Dependencia = " + id_Dependencia;
        ResultSet resultado = null;

        try {
            PreparedStatement statement = con.prepareStatement(consulta);

            resultado = statement.executeQuery();

        } catch (SQLException ex) {
            System.out.println("ERROR AL CONSULTAR SUBDEPENDENCIA " + ex.getMessage());
        }
        return resultado;
    }

    public void EnviaLogin(String mailDestinatario, String nombreEmpleado,
            String codigoEmpleado, String contraseniaEmpleado) {
        
        System.out.println("desti: "+mailDestinatario);
        
        Properties propiedad = new Properties();
        propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
        propiedad.setProperty("mail.smtp.starttls.enable", "true");
        propiedad.setProperty("mail.smtp.port", "587");
        propiedad.setProperty("mail.smtp.auth", "true");

        Session sesion = Session.getDefaultInstance(propiedad);
        String correoEnvia = "sistemanetu2021@gmail.com";
        String contrasena = "NetU2021";
        String receptor = mailDestinatario;//Destinatario 
        String asunto = "Datos Inicio Sesión";// Asunto correo
        String mensaje = "Cordial saludo Sr(a) " + nombreEmpleado
                + "\n\nSu código es: " + codigoEmpleado
                + " \n\nSu contraseña inicial es: " + contraseniaEmpleado
                + " \n\nTiene la opcion de actualizarla luego.\n\n"
                + "Gracias por su amable atención,\n\n"
                + "Administración.";// Mensaje  

        MimeMessage mail = new MimeMessage(sesion);

        try {
            mail.setFrom(new InternetAddress(correoEnvia));
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(receptor));
            mail.setSubject(asunto);
            mail.setText(mensaje);

            Transport transporte = sesion.getTransport("smtp");
            transporte.connect(correoEnvia, contrasena);
            transporte.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));
            transporte.close();
            JOptionPane.showMessageDialog(null, "Correo Enviado");// Confirmación de envío de correo

        } catch (AddressException ex) {
            System.out.println("ERROR " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("ERROR " + ex.getMessage());
        }
    }

}
