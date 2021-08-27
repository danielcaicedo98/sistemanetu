/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import modelo.Sesion;
import modelo.ConexionBD;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.List;
;
import javax.swing.JOptionPane;
import modelo.GestorEmpleado;
import vista.Empleado;
import vista.LoginServidor;
import vista.VentanaServidor;
import vista.itemCombo;

/**
 *
 * @author Camila
 */


public class Controlador implements ActionListener, ItemListener {

    private GestorEmpleado gestorEmpleado;
    private Servidor servidor;
    private VentanaServidor vistaPrincipal;
    private LoginServidor login;
    private Sesion modelo;

    //Constructor
    public Controlador() {

        //CREO LA CONEXION CON LA BD
        ConexionBD.Conectar();

        modelo = new Sesion();
        gestorEmpleado = new GestorEmpleado();
        servidor = new Servidor(gestorEmpleado);

        servidor.start();
        //login = new LoginServidor();
        //login.addActionListener(this);

        abrirVentanaPrincipal();

    }

    public void ocultarLogin(boolean ocultar) {
        login.setVisible(ocultar);
    }

    //ESTE MÉTODO LLENA LOS DOS COMBOS DE LAS DEPENDENCIAS
    public void llenarCombosDependencias() {

        vistaPrincipal.setDependencias(modelo.consultarDependencias());

        vistaPrincipal.llenarCDependencias();

    }

    //METODO DE TABLA
    public void llenaTabla() {
        List<Empleado> listadoEmpleados;
        listadoEmpleados = this.modelo.busquedaEmpleados(0, 0, "");
        this.vistaPrincipal.cargarEmpleados(listadoEmpleados);

    }

    @Override
    public void itemStateChanged(ItemEvent ie) {

        if (ie.getStateChange() == ItemEvent.SELECTED) {

            if (ie.getSource().equals(vistaPrincipal.getCbxDepListado())) {

                itemCombo item = (itemCombo) vistaPrincipal.getCbxDepListado().getSelectedItem();
                System.out.println(item.getId());
                if (item.getId() != 0) {
                    vistaPrincipal.llenarCSubdpendencias(vistaPrincipal.VCxSubDepListados, modelo.consultarSubdependencias(item.getId()));
                } else {
                    vistaPrincipal.llenarCSubdpendencias(vistaPrincipal.VCxSubDepListados, null);
                }
            } else if (ie.getSource().equals(vistaPrincipal.getCbxDependencias())) {

                itemCombo item = (itemCombo) vistaPrincipal.getCbxDependencias().getSelectedItem();

                if (item.getId() != 0) {
                    vistaPrincipal.llenarCSubdpendencias(vistaPrincipal.VCbxSubDep, modelo.consultarSubdependencias(item.getId()));
                } else {
                    vistaPrincipal.llenarCSubdpendencias(vistaPrincipal.VCbxSubDep, null);
                }
                /*System.out.println(item.getId());

                if (item.getId() != 0) {

                    vistaPrincipal.llenarCSubdpendencias(vistaPrincipal.VCbxSubDep, modelo.consultarSubdependencias(item.getId()));
                }*/

            }

        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getActionCommand().equalsIgnoreCase("REGISTRAR EMPLEADO")) {
            vistaPrincipal.accionRegistrar(1);
        } else if (ae.getActionCommand().equalsIgnoreCase("REGISTRAR ADMINISTRADOR")) {
            vistaPrincipal.accionRegistrar(2);
        } else if (ae.getActionCommand().equalsIgnoreCase("MODIFICAR")) {
            if (vistaPrincipal.accionModificar() != 0) {
                vistaPrincipal.cargarEmpleadoAModificar();
            }

        } else if (ae.getActionCommand().equalsIgnoreCase("GUARDAR")) {
            registro(1);
        } else if (ae.getActionCommand().equalsIgnoreCase("Registrar admin")) {
            registro(2);
        } else if (ae.getActionCommand().equalsIgnoreCase("Registrar emple")) {
            registro(1);
        } else if (ae.getActionCommand().equalsIgnoreCase("ACTUALIZAR")) {
            actualizar();
        } else if (ae.getActionCommand().equalsIgnoreCase("ELIMINAR")) {
            if (vistaPrincipal.accionEliminar() != 0) {
                eliminar();
            }

        } else if (ae.getActionCommand().equalsIgnoreCase("CANCELAR")) {
            vistaPrincipal.accionRegistrar(1);
        } else if (ae.getActionCommand().equalsIgnoreCase("BUSCAR")) {
            busqueda();
        }

        if (login != null) {
            if (ae.getSource().equals(login.getBtnLogin())) {

                int codigo = login.getCodigo();
                String password = login.getPassword();

                if (codigo != -1 && !password.isEmpty()) {

                    System.out.println(password);

                } else {

                    String mensaje = "ERROR: OCURRIERON ESTOS ERRORES AL INTENTAR "
                            + "INICIAR SESIÓN\n\n";

                    if (codigo == -1) {
                        mensaje += "El código no es válido \n";

                    }

                    if (password.isEmpty()) {
                        mensaje += "Debe ingresar una contraseña";
                    }

                    JOptionPane.showMessageDialog(null, mensaje);

                }

            }
        }
    }

    public void registro(int referencia) {

        itemCombo dep = (itemCombo) (vistaPrincipal.getCbxDependencias().getSelectedItem());
        itemCombo sub = (itemCombo) (vistaPrincipal.getCbxSubdependencias().getSelectedItem());

        if (vistaPrincipal.getCodigoInt() == -1) {
            vistaPrincipal.gestionMensajes("INGRESE UN CÓDIGO VÁLIDO PARA EL EMPLEADO", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
        } else if (vistaPrincipal.getNombre().equals("")) {
            vistaPrincipal.gestionMensajes("INGRESE EL NOMBRE DEL EMPLEADO", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
        } else if (vistaPrincipal.getCorreo().equals("")) {
            vistaPrincipal.gestionMensajes("INGRESE EL CORREO DEL EMPLEADO", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
        } else if (vistaPrincipal.getSexo().equals("Seleccionar")) {
            vistaPrincipal.gestionMensajes("INGRESE EL SEXO DEL EMPLEADO", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
        } else if (dep.getId() == 0) {
            vistaPrincipal.gestionMensajes("INGRESE LA DEPENDENCIA DEL EMPLEADO", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
        } else if (sub.getId() == 0) {
            vistaPrincipal.gestionMensajes("INGRESE LA SUBDEPENDENCIA DEL EMPLEADO", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
        } else {

            Empleado empleado = new Empleado();

            empleado.setCodigo(vistaPrincipal.getCodigoInt());
            empleado.setNombre(vistaPrincipal.getNombre());
            empleado.setCorreo(vistaPrincipal.getCorreo());
            empleado.setSexo(vistaPrincipal.getSexo());
            empleado.setDependencia(dep.getId());
            empleado.setSubDependencia(sub.getId());

            int resultado = 0;

            if (referencia == 1) {
                resultado = modelo.agregarEmpleado(empleado, generarContraseña());
            } else {
                resultado = modelo.agregarAdmin(empleado, generarContraseña());
            }

            if (resultado == 1) {

                llenaTabla();

                vistaPrincipal.activarControles(false);
                vistaPrincipal.accionRegistrar(1);
            } else {
                vistaPrincipal.gestionMensajes("Error al grabar",
                        "Confirmación", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void actualizar() {
        String mensaje = "";
        String sexo = vistaPrincipal.getSexo();
        String nombre = vistaPrincipal.getNombre();
        String correo = vistaPrincipal.getCorreo();

        itemCombo dep = (itemCombo) (vistaPrincipal.getCbxDependencias().getSelectedItem());
        itemCombo sub = (itemCombo) (vistaPrincipal.getCbxSubdependencias().getSelectedItem());

        if (nombre.isEmpty()) {
            mensaje += "Debe escribir un nombre\n";
        }

        if (correo.isEmpty()) {
            mensaje += "Debe escribir un correo\n";
        }

        if (dep.getId() == 0) {
            mensaje += "Debe seleccionar una dependencia\n";
        }

        if (sub.getId() == 0) {
            mensaje += "Debe seleccionar una subdependencia\n";
        }

        if (sexo.equalsIgnoreCase("Seleccionar")) {
            mensaje += "Debe seleccionar un sexo\n";
        }

        if (!mensaje.isEmpty()) {
            mensaje = "ACTUALIZACION FALLIDA\n\n" + mensaje;
        } else {

            Empleado empleado = new Empleado();

            empleado.setCodigo(vistaPrincipal.getCodigoInt());
            empleado.setNombre(nombre);
            empleado.setCorreo(correo);
            empleado.setSexo(sexo);
            empleado.setDependencia(dep.getId());
            empleado.setSubDependencia(sub.getId());

            if (modelo.modificarEmpleado(empleado) == 1) {

                vistaPrincipal.activarControles(false);
                vistaPrincipal.accionModificar();

                vistaPrincipal.seleccionarItem(vistaPrincipal.VCbxDepListado, 0);
                llenaTabla();
            } else {
                vistaPrincipal.gestionMensajes(
                        "ACTUALIZACION FALLIDA", "Confirmación ", JOptionPane.ERROR_MESSAGE);
            }

        }

    }

    private void eliminar() {

        Empleado empleado = vistaPrincipal.getEmpleadoFromTable();

        int respuesta = JOptionPane.showConfirmDialog(null,
                "¿DESEA ELIMINAR EL EMPLEADO DE CODIGO : " + empleado.getCodigo() + " ?",
                "Confirmación de Acción", JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {

            if (modelo.eliminarEmpleado(empleado.getCodigo()) == 1) {
                llenaTabla();
            } else {
                JOptionPane.showMessageDialog(null,
                        "ERROR AL ELIMINAR EL EMPLEADO", "Confirmación de acción", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    public void abrirVentanaPrincipal() {

        vistaPrincipal = new VentanaServidor();

        vistaPrincipal.addListenerBtnRegistrarEmpleados(this);
        vistaPrincipal.addListenerBtnRegistrarAdmin(this);
        vistaPrincipal.addListenerBtnModEmpleados(this);
        vistaPrincipal.addListenerBtnElmEmpleados(this);
        vistaPrincipal.addListenerBtnBusListado(this);

        llenaTabla();

        //LLENO LOS COMBOS DE LAS DEPENDENCIAS
        vistaPrincipal.addListenerDependencias(this);
        llenarCombosDependencias();
    }

    public void llenarDependencias() {

        vistaPrincipal.setDependencias(modelo.consultarDependencias());

    }

    private void busqueda() {

        itemCombo dep = (itemCombo) (vistaPrincipal.getCbxDepListado().getSelectedItem());
        itemCombo sub = (itemCombo) (vistaPrincipal.getCbxSubdependenciasBusq().getSelectedItem());
        String nombre = vistaPrincipal.getNombreBusq().trim();

        vistaPrincipal.cargarEmpleados(modelo.busquedaEmpleados(dep.getId(), sub.getId(), nombre));
    }

    private String generarContraseña() {

        String password = "";
        String abecedario = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTU"
                + "VWXYZ1234567890";

        for (int i = 0; i < 10; i++) {

            password += "" + abecedario.charAt((int) Math.floor(Math.random() * 10));

        }

        return password;
    }

    public static void main(String[] args) throws SQLException {

        Controlador control = new Controlador();
        System.out.println(control.generarContraseña());

        //VentanaServidor n = new VentanaServidor();
        //n.modificaCombos(s.consultarDependencias(), 2, "FACULTADES", "Femenino");
    }

}
