package Logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author josue
 */
public class PSNUser {
    private RandomAccessFile Leer;
    private final HashTable Users;
    private final File Archivos;
    //Variables usadas para conseguir info de usuario
    private int Trofeos;
    private int Puntos;
    
    protected static boolean StartUp = true;
    public PSNUser() throws FileNotFoundException, IOException{
        //Se crea la carpeta de usuarios
        Archivos = new File("PSN Accounts");
        if (!Archivos.exists()) Archivos.mkdir();
        //Se leen los nombres de los usuarios para recargarlos en el HashTable
        Users = new HashTable();
        ReloadHashTable();
        StartUp = false;
    }
    
    private void ReloadHashTable() throws FileNotFoundException, IOException{
        String Username;
        System.out.println("Usuarios: ");
        for (String FileName : Archivos.list()){
            Leer = new RandomAccessFile("PSN Accounts\\" + FileName, "rw");
            Leer.seek(0);
            Username = Leer.readUTF();
            if (Leer.readBoolean()){
                Users.add(Username);
                System.out.println(Username);
            }
        }
    }
    
    /*
    * Formato de archivo binario
    * String Username
    * Boolean Cuenta activa
    * String Trophy data
    * Rank: P - Platino ~~ G - Oro ~~ S - Plata ~~ B - Bronce
    */
    public boolean AddUser(String Username) throws FileNotFoundException, IOException{
        if (Users.add(Username)){
            System.out.println("nmms");
            Leer = new RandomAccessFile("PSN Accounts\\" + Username + ".psn","rw");
            Leer.seek(0);
            Leer.writeUTF(Username);
            Leer.writeBoolean(true);
            return true;
        }
        
        return false;
    }
    
    
    
    public boolean RemoveUser(String Username) throws FileNotFoundException, IOException{
        if (Users.Remove(Username)){
            Leer = new RandomAccessFile("PSN Accounts\\" + Username + ".psn","rw");
            Leer.seek(0);
            Leer.readUTF();
            Leer.writeBoolean(false);
            return true;
        }
        return false;
    }
    
    public static void ReActivateAcc(String Username){
        try {
            RandomAccessFile Leer = new RandomAccessFile("PSN Accounts\\" + Username + ".psn","rw");
            Leer.seek(0);
            Leer.readUTF();
            Leer.writeBoolean(true);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PSNUser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PSNUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*
    * Formato del trofeo:
    * String - Fecha
    * String - tipo de trofeo
    * int    - Puntos
    * String - juego del trofeo
    * String - Nombre del juego
    */
    public boolean AddTrophyTo(String Username, String TrophyGame, String TrophyName, Trophy Type) throws IOException{
        int Pos = Users.Search(Username);
        if (Pos != -1){
            Leer = new RandomAccessFile("PSN Accounts\\" + Username + ".psn","rw");
            Leer.seek(Leer.length());
            Leer.writeUTF(Calendar.getInstance().getTime().toString());
            Leer.writeUTF(Type.Type);
            Leer.writeInt(Type.Points);
            Leer.writeUTF(TrophyGame);
            Leer.writeUTF(TrophyName);
            return true;
        }
        return false;
    }
    
    public String PlayerInfo(String Username) throws FileNotFoundException, IOException{
        Puntos = 0;
        Trofeos = 0;
        if (Users.Search(Username) != -1){
            Leer = new RandomAccessFile("PSN Accounts\\" + Username + ".psn","rw");
            Leer.seek(0);
            Leer.readUTF();
            if (Leer.readBoolean()){
                String Data = "";
                long Pos;
                while (Leer.getFilePointer() < Leer.length()){
                    Trofeos++;
                    Data += "Trofeo conseguido el: "+Leer.readUTF() + "\nTrofeo de: " + Leer.readUTF() + "\tPts. ";
                    Pos = Leer.getFilePointer();
                    Puntos += Leer.readInt();
                    
                    Leer.seek(Pos);
                    
                    Data += Leer.readInt() + "\nJuego en el que se consiguio: " + Leer.readUTF() + " \nNombre del trofeo: " + Leer.readUTF()  + "\n";
                }
                return Data;
            }
        }
        return "";
    }
    
    public int getTrophyCant(){
        return Trofeos;
    }
    
    public int getUserPoints(){
        return Puntos;
    }
    
}
