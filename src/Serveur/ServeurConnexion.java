package Serveur;

import Serveur.ServeurCommunication;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by p1307887 on 06/03/2017.
 */
public class ServeurConnexion {

    private int portServeur = 110;
    private InetAddress addr;
    ServerSocket ss;
    private int ID = 0;

    public ServeurConnexion() throws IOException {
        this.ss = new ServerSocket(this.portServeur);
        System.out.println("connexion socket ok");
    }

    public void connexion() throws IOException {
        while (true) {
            Socket conn_cli = new Socket();

            try {
                conn_cli = this.ss.accept();
                System.out.println("connexion client ok");
            } catch (IOException var3) {
                System.out.println("connection timed out");
            }


            ServeurCommunication com = new ServeurCommunication(conn_cli, this.ID++);
            com.start();
        }
    }
}


