package Client;

import javafx.scene.control.TextInputDialog;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

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

        if(!answer.contains("+OK POP3 Server ready")){
            System.out.println("Could not connect to Server");
            System.out.println(answer);

            return;
        }

        String message="";
        do {
            OutputStream out = socket.getOutputStream();
            Scanner userinput = new Scanner(System.in);
            System.out.print("Identify yourself for the server :");
            String identification = userinput.nextLine();
            System.out.print(identification);

            message = "APOP " + identification +"\r\n";
            out.write(message.getBytes());
            InputStream input = socket.getInputStream();
            message = listen(input);
            System.out.println(message);

        }while (!message.startsWith("+OK"));
        String command ="";
        do {
            OutputStream out = socket.getOutputStream();
            Scanner userinput = new Scanner(System.in);
            System.out.print("Write a command for the server :");
            command = userinput.nextLine();
            System.out.print(command);

            message = command + "\r\n";
            out.write(message.getBytes());
            if(command.contains("RETR")){
                do{
                    InputStream input = socket.getInputStream();
                    message = listen(input);
                    System.out.println(message);
                }while(!message.equals("."));
            }else{
                InputStream input = socket.getInputStream();
                message = listen(input);
                System.out.println(message);
            }


        }while (!command.contains("QUIT"));






    }

    private  String listen(InputStream input) throws IOException {
        boolean cr = false;
        boolean lf = false;
        String message = "";

        while(!cr || !lf){
            int data = input.read();
            if(data == -1){
                return "quitnonsafe";
            }
            char c = (char)data;
            message += c;

            if(cr && c == '\n')
                lf = true;
            else
                cr = false;
            if(c == '\r')
                cr = true;
        }
        if(message.equalsIgnoreCase("quitnonsafe")){
            message += ".";
        }
        message = message.replaceAll("[\r,\n]", "");
        return message;
    }
}



