package Logic;

import java.io.File;
import javax.swing.JOptionPane;

/**
 *
 * @author josue
 */
public class HashTable {
    private boolean ReActivate = false;
    private Entry Nodo;
    
    public HashTable(){
        Nodo = null;
    }
    
    public boolean add(String Username){
        if (Username.isBlank()) return false;

        int Pos = 0;
        
        if (Nodo == null){
            Nodo = new Entry(Username, Pos);
            return true;
        } else {
            if (Nodo.Username.equals(Username)){
                JOptionPane.showMessageDialog(null, "Este nombre de usuario ya esta tomado, por favor ingrese otro", "Nombre de usuario seleccionado", JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
            
            Entry Node = this.Nodo;
            if ((FoundOnFile(Username) && !PSNUser.StartUp)){
                JOptionPane.showMessageDialog(null, "La cuenta que intenta registrar ya existe", "Crear usuario", JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
            
            while (Node != null){
                Pos++;
                if (Node.Siguiente == null){
                    Node.Siguiente = new Entry(Username, Pos);
                    return true;
                }
                if (Nodo.Siguiente.Username.equals(Username)){
                    JOptionPane.showMessageDialog(null, "¡Este nombre de usuario ya esta tomado!", "Nombre de usuario tomado", JOptionPane.WARNING_MESSAGE);
                    return false;
                }
                Node = Node.Siguiente;
            }
        }
        
        return false;
    }
    
    public boolean FoundOnFile(String Name){
        File Accounts = new File("PSN Accounts\\" + Name + ".psn");
        return Accounts.exists();
    }
    
    public boolean Remove(String Username){
        if (Username.isBlank()) return false;
        
        if (Nodo != null){
            if (Nodo.Username.equals(Username)) {
                Nodo = Nodo.Siguiente;
                return true;
            }
            
            Entry Node = Nodo;
            while (Node.Siguiente != null){
                if (Node.Siguiente.Username.equals(Username)){
                    Node.Siguiente = Node.Siguiente.Siguiente;
                    return true;
                }
                
                Node = Node.Siguiente;
            }
            
        }

        if (FoundOnFile(Username) && !PSNUser.StartUp){
            if (JOptionPane.showConfirmDialog(null, "Esta cuenta ya se encuentra desactivada\n¿Desea reactivarla?", "Reactivar cuenta", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                ReActivate = true;
                Search(Username);
                return false;
            }
        }
        
        JOptionPane.showMessageDialog(null, "¡No se ha encontrado a este usuario!", "Usuario inexistente", JOptionPane.INFORMATION_MESSAGE);
        return false;
    }
    
    public int Search(String Username){
        if (Username.isBlank() || Nodo == null) return -1;
        
        if (Nodo.Username.equals(Username)) return Nodo.Pos;
        
        if (ReActivate){
            PSNUser.StartUp = true;
            add(Username);
            PSNUser.ReActivateAcc(Username);
            ReActivate = false;
            PSNUser.StartUp = false;
            JOptionPane.showMessageDialog(null, "¡Se ha reactivado la cuenta exitosamente!");
            return -1;
        }
        
        Entry Node = Nodo;
        while (Node != null){
            if (Node.Username.equals(Username)){
                return Node.Pos;
            }
            Node = Node.Siguiente;
        }
        
        return -1;
    }
    
}
