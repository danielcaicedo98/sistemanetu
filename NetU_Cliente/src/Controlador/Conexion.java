/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import Paquetes.Paquete;

/**
 *
 * @author USUARIO
 */
public class Conexion {
    Controlador controlador;
    public static final String ipServidor = "127.0.0.1";
    private Socket socket;
    private ObjectInputStream entrada;
    private ObjectOutputStream salida;
    private int conexionCerrada;

    public Conexion(Controlador control) {
        
        controlador = control;

        try {
            socket = new Socket(ipServidor, 12345);
        } catch (IOException ex) {
            System.out.println("Erro al inicializar el socket: " + ex.getMessage());
        }
        
        abrirFlujos();

    }

    public void abrirFlujos() {

        try {
            salida = new ObjectOutputStream(socket.getOutputStream());
            entrada = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            System.out.println("Erro al abrir flujos: " + ex.getMessage());
        }

    }

    public void cerrarFliujos() {

        conexionCerrada = -1;

        try {
            entrada.close();
            salida.close();
            socket.close();
        } catch (IOException ex) {
            System.out.println("Erro al cerrar flujos: " + ex.getMessage());
        }

    }

    public void enviarPaquete(Paquete paquete) {

        try {
            salida.writeObject(paquete);
            System.out.println("Envié el paquete: ");
            
        } catch (IOException ex) {
            System.out.println("Erro al enviar el paquete: " + ex.getMessage());
        }

    }

    public void escuchar() {

        try {

            while (conexionCerrada == 0) {
                Paquete paquete = (Paquete) entrada.readObject();
                
                if(paquete.getTipo() == 2){
                    
                    controlador.gestPaqIniciarSesion(paquete);
                    
                }
            }
        } catch (IOException ex) {
            System.out.println("Erro al escuchar al servidor: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Erro al escuchar al servidor: " + ex.getMessage());
        }

    }

}
