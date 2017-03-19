package Serveur;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by p1307887 on 06/03/2017.
 */
public class ServeurCommunication extends Thread{
    private Socket conn_cli;
    private int ID;
    private enum etatPossible {FERME, AUTORISATION, AUTHENTIFICATION, TRANSACTION};
    private etatPossible etat;
    private int authentificationFlag;
    private String user;
    private String pass;
    private boolean connexion;
    private int messageNb;
    private long messageSize;
    private List<Message> messageList = new ArrayList<>();
    private List<Integer> idList = new ArrayList<>();

        public ServeurCommunication(Socket conn_cli, int ID) {
            this.conn_cli = conn_cli;
            this.ID = ID;
            this.authentificationFlag=0;
            this.etat=etatPossible.AUTORISATION;
            this.connexion=true;
        }

        @Override
        public void start(){
            BufferedReader in = null;

            try {
                in = new BufferedReader(new InputStreamReader(this.conn_cli.getInputStream()));
            } catch (IOException var17) {
                System.out.println("1");
            }

            OutputStream out = null;

            try {
                out = this.conn_cli.getOutputStream();
            } catch (IOException var16) {
                System.out.println("2");
            }

            String request = null;


            try {
                out.write("+OK POP3 serveur ready\r\n".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }

            while(connexion) {
                try {
                    request = in.readLine();
                    System.out.println(request);
                } catch (IOException var15) {
                    System.out.println("request null");
                }
                switch (etat) {
                    case FERME:
                        break;
                    case AUTORISATION:
                        if (request.contains("USER") && authentificationFlag == 0) {
                            System.out.println(request.substring(request.indexOf(" ")));
                            if(ServeurPOP3.username.contains(request.substring(request.indexOf(" ")+1)+".txt")){
                                this.user = request.substring(request.indexOf(" "));
                                authentificationFlag = 1;
                                etat = etatPossible.AUTHENTIFICATION;
                                try {
                                    out.write("+OK user exist\r\n".getBytes());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                        }else if (request.contains("APOP")){
                            System.out.println(request.substring(request.indexOf(" "),request.lastIndexOf(" ")));
                            System.out.println(request.substring(request.lastIndexOf(" ")));
                            this.user = request.substring(request.indexOf(" ")+1,request.lastIndexOf(" "));
                            this.pass = request.substring(request.indexOf(" "));
                            getUserInfo(user);
                            try {
                                out.write(("+OK " +messageNb+" "+messageSize + "\r\n").getBytes());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            etat = etatPossible.TRANSACTION;
                        }
                        break;
                    case AUTHENTIFICATION:
                        if (request.contains("PASS") && authentificationFlag == 1) {
                            System.out.println(request.substring(request.indexOf(" "),request.lastIndexOf(" ")));
                            this.pass = request.substring(request.lastIndexOf(" "));
                            authentificationFlag = 2;
                            getUserInfo(user);
                            try {
                                out.write(("+OK" +messageNb+" "+messageSize +"\r\n").getBytes());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            etat = etatPossible.TRANSACTION;
                        }
                        break;
                    case TRANSACTION:
                        if (request.contains("STAT")){
                            try {
                                out.write(("+OK "+messageNb+" "+messageSize +"\r\n").getBytes());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else if(request.contains("QUIT")){
                            miseajour();
                            etat=etatPossible.FERME;
                        }else if(request.contains("RETR")){
                            if(idList.contains(Integer.parseInt(request.substring(5)))){
                                System.out.println(request.substring(5));
                                for (int i=0;i<idList.size();i++
                                     ) {
                                    if(idList.get(i)==Integer.parseInt(request.substring(5))){
                                        try {
                                            out.write((messageList.get(i).getMessageTxt()+"\r\n").getBytes());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }


                            }
                            else{
                                try {
                                    out.write(("ERROR MESSAGE NOT REAL").getBytes());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                        break;
                }
            }


            try {
                out.close();

            in.close();
            this.conn_cli.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    private void getUserInfo(String user) {
        System.out.println("user info "+user);

        this.messageNb=Util.countMessage("Message/"+user+".txt");
        this.messageSize=Util.getFileSize("Message/"+user+".txt");
        System.out.println("message number "+messageNb);

        List<String> list = Util.readMessage("Message/"+user+".txt");

        for(int i=0; i<list.size(); i++){
            //System.out.println(list.get(i));
            //System.out.println(list.get(i).substring(list.get(i).indexOf("Message-ID: ")+13,  list.get(i).lastIndexOf(">")));
            messageList.add(new Message(Integer.parseInt(list.get(i).substring(list.get(i).indexOf("Message-ID: ")+13,  list.get(i).lastIndexOf(">"))),list.get(i)));
            idList.add(Integer.parseInt(list.get(i).substring(list.get(i).indexOf("Message-ID: ")+13,  list.get(i).lastIndexOf(">"))));
        }
    }


    private void miseajour() {
        this.connexion=false;
    }
}

