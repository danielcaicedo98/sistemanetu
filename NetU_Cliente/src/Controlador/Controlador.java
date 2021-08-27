/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import Paquetes.Paquete;
import Paquetes.PetIniciarSesion;
import Paquetes.ResIniciarSesion;
import vista.ChatGUI;
import vista.DescripcionGUI;
import vista.LoginGUI;
import vista.MensajeGUI;
import vista.PerfilGUI;
import vista.PrincipalGUI;
import vista.PublicacionGUI;

/**
 *
 * @author PuntoPC
 */
public class Controlador implements ActionListener {
    
    private Conexion conexion;
    private LoginGUI login;
    private PrincipalGUI pr;

    public Controlador() {
        
        conexion = new Conexion(this);
        
        login = new LoginGUI();
        
        login.addActionListener(this);
        
        conexion.escuchar();

    }
    
    public void iniciarPrincipalGUI(){
        
        pr = new PrincipalGUI();
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        //Escucha del login
        
        if(e.getSource().equals(login.getBtnLogin())){
            
            int codigo = login.getCodigo();
            String password = login.getPassword();
            
            if(codigo != -1 && !password.isEmpty()){
                
                PetIniciarSesion packet = new PetIniciarSesion(codigo, password);
                packet.setTipo(Paquete.petIniciarSesion);
                conexion.enviarPaquete(packet);
                
                
            }else{
                
                String mensaje = "ERROR: OCURRIERON ESTOS ERRORES AL INTENTAR "
                        + "INICIAR SESIÓN\n\n"  ;
                
                if(codigo == -1){
                    mensaje += "El código no es válido \n";
                    
                }
                
                if(password.isEmpty()){
                    mensaje += "Debe ingresar una contraseña";
                }
                
                desplegarMensaje(mensaje);
                
            }
            
        }
        
        
    }
    
    
    /**
     * @param args the command line arguments
     */
    
    public void gestPaqIniciarSesion(Paquete paquete){
        
        System.out.println("Iniciar principal");
        
        ResIniciarSesion res = (ResIniciarSesion) paquete;
        
        if(res.getExito() != 10){
            
            pr = new PrincipalGUI();
            
        }else{
            desplegarMensaje(res.getMensaje());
        }
        
        
    }
    
    public void desplegarMensaje(String mensaje){
        
        JOptionPane.showMessageDialog(null, mensaje);
    }
    
    public static void main(String[] args) {
        
        Controlador con = new Controlador();
    }

    
}
