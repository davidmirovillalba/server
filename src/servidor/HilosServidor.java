/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidor;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import servidor.VentanaServidor;

/**
 *
 * @author david
 */
public class HilosServidor extends Thread {

    private final String[] users = {"lluis","daniel"};
    private final String[] passwords = {"12345","abcde"};
    private boolean comprovacio = false;
    private Socket s = null;
    String user;
    String password;
    VentanaServidor ventana = new VentanaServidor();

    public HilosServidor(Socket s, String user, String password, VentanaServidor ventana) {
        this.s = s;
        this.user = user;
        this.password = password;
        this.ventana = ventana;
    }

        @Override
        public void run () {

        while (!comprovacio) {
                try {
                    DataInputStream in = new DataInputStream(s.getInputStream());

                    for (int i = 0; i < users.length; i++) {
                        if (users[i].equals(user)) {
                            if (passwords[i].equals(password)) {
                                ventana.imprimirDatos("Ha accedit el " + users[i] + " amb ip " + s.getInetAddress() + " ");
                                comprovacio = in.readBoolean();
                            }
                        }

                        if (comprovacio) {
                            ventana.imprimirDatos("Ha fet logout el " + users[i] + " amb ip " + s.getInetAddress() + " ");
                            s.close();
                            comprovacio = false;
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(HilosServidor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }
