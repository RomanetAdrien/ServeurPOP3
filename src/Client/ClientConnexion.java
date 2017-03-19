package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by p1206717 on 06/03/2017.
 */
public class ClientConnexion {

    private int port=110;
    private InetAddress serveurAdresse;
    Socket socket;

    public ClientConnexion() throws IOException {
        this.socket=new Socket(serveurAdresse, port);
    }

    public ClientConnexion(String adresse) throws IOException {
        this.serveurAdresse= InetAddress.getByName(adresse);
        this.socket=new Socket(serveurAdresse, port);

        BufferedReader in = null;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String answer = null;
        answer = in.readLine();

        System.out.println(answer);

        OutputStream out = null;

    }

}


