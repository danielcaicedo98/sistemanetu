package Controlador;

import modelo.GestorEmpleado;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import Paquetes.Paquete;
import Paquetes.PetIniciarSesion;
import Paquetes.ResIniciarSesion;

public class Servidor extends Thread {
    
    private GestorEmpleado gestorEmpleado;
    private ServerSocket sockServer;
    private final int puerto = 12345;

    public Servidor(GestorEmpleado gestorE) {
        
        gestorEmpleado = gestorE;

        try {
            sockServer = new ServerSocket(puerto);

        } catch (IOException ex) {
            System.out.println("Erro al iniciar el socket server: " + ex.getMessage());
        }

    }

    @Override
    public void run() {
        while (true) {

            try {
               Socket sockEmpleado =  sockServer.accept();
               
               HiloEmpleado empleado = new HiloEmpleado(sockEmpleado, gestorEmpleado);
               empleado.start();
            } catch (IOException ex) {
                System.out.println("Erro al aceptar un empleado: " + ex.getMessage());
            }

        }
    }

    class HiloEmpleado extends Thread {

        private int codigo;
        private GestorEmpleado gestor;
        private Socket socket;
        private ObjectInputStream entrada;
        private ObjectOutputStream salida;

        public HiloEmpleado(Socket sockEmpleado, GestorEmpleado gestor) {

            this.gestor = gestor;
            socket = sockEmpleado;

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

        public void enviarPaquete(Paquete paquete) {

            try {
                salida.writeObject(paquete);
            } catch (IOException ex) {
                System.out.println("Erro al enviar el paquete: " + ex.getMessage());
            }

        }

        @Override
        public void run() {

            try {

                while (true) {
                    Paquete paquete = (Paquete) entrada.readObject();

                    if (paquete.getTipo() == 1) {
                        
                        ResIniciarSesion respuesta;
                        
                        respuesta = gestor.iniciarSesion((PetIniciarSesion) paquete);
                        
                        Paquete packet = (Paquete) respuesta;
                        packet.setTipo(Paquete.resIniciarSesion);
                        
                        enviarPaquete(packet);
                        
                        System.out.println("Envie respuesta");
                    }
                }

            } catch (IOException ex) {
                System.out.println("Erro al leer un paquete: " + ex.getMessage());
            } catch (ClassNotFoundException ex) {
                System.out.println("Erro al leer un paquete: " + ex.getMessage());
            }

        }

    }

}
