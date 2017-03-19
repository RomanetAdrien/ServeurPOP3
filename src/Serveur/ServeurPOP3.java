package Serveur;

import Serveur.ServeurConnexion;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by p1307887 on 06/03/2017.
 */
public class ServeurPOP3 {

    public static List<User> userlist = new ArrayList<>();
    public static List<String> username = new ArrayList<>();

    public static void main(String[] args) throws IOException {


        for (String user:Util.listerRepertoire(new File("Message"))) {
            username.add(user);
            //System.out.println(user);
        }

        for (String user : username) {
            Util.readFile("Message/"+user);
        }

        for (String user : username) {
            //System.out.println(Util.countMessage("Message/"+user));
        }

        ServeurConnexion cnx = new ServeurConnexion();
        cnx.connexion();

    }

}
