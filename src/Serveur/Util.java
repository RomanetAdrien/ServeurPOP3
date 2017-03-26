package Serveur;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by p1307887 on 07/03/2017.
 */
public class Util {

        public static String readFile(String file){

            String chaine="";
            String fichier =file;

            //lecture du fichier texte
            try{
                InputStream ips=new FileInputStream(fichier);
                InputStreamReader ipsr=new InputStreamReader(ips);
                BufferedReader br=new BufferedReader(ipsr);
                String ligne;
                while ((ligne=br.readLine())!=null){
                    //System.out.println(ligne);
                    chaine+=ligne+"\n";
                }
                br.close();
            }
            catch (Exception e){
                System.out.println(e.toString());
            }
            return chaine;
        }

    public static String[] listerRepertoire(File repertoire){

        String [] listefichiers;
        listefichiers=repertoire.list();
        return listefichiers;
    }

    public static int countMessage(String file){
        String chaine="";
        String fichier =file;
        int messagesNb = 0;
        try{
            InputStream ips=new FileInputStream(fichier);
            InputStreamReader ipsr=new InputStreamReader(ips);
            BufferedReader br=new BufferedReader(ipsr);
            String ligne;
            while ((ligne=br.readLine())!=null){
                if(ligne.equals(".")){
                    messagesNb++;
                }
                chaine+=ligne+"\n";
            }
            br.close();
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
        return messagesNb;
    }

    public static long getFileSize(String file){
        long size;
        size = new File(file).length();
        return size;
    }


    public static List<String> readMessage(String file){

        String chaine="";
        String fichier =file;
        List<String> listMessage = new ArrayList<>();
        int i=0;

        //lecture du fichier texte
        try{
            InputStream ips=new FileInputStream(fichier);
            InputStreamReader ipsr=new InputStreamReader(ips);
            BufferedReader br=new BufferedReader(ipsr);
            String ligne;
            while ((ligne=br.readLine())!=null){

                while (!ligne.equals(".")){
                    if((ligne=br.readLine())==null){
                        break;
                    }
                    chaine+=ligne+"\r\n";
                    i++;
                }
                listMessage.add(chaine);

                chaine="";
            }
            br.close();
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
        return listMessage;
    }

    public static int getMessageId(String message){
        int messageId =0;

        return messageId;
    }

    public static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }
}
