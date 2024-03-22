package Logic;

/**
 *
 * @author josue
 */
public class HashTable {
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
            if (Nodo.Username.equals(Username)) return false;
            
            Entry Node = this.Nodo;
            while (Node != null){
                if (Nodo.Siguiente.Username.equals(Username)) return false;

                Node = Node.Siguiente;
                Pos++;
                if (Node.Siguiente == null){
                    Node.Siguiente = new Entry(Username, Pos);
                    return true;
                }
            }
        }
        
        return false;
    }
    
    public boolean Remove(String Username){
        if (Username.isBlank()) return false;
        
        if (Nodo != null){
            if (Nodo.Username.equals(Username)) {
                Nodo = Nodo.Siguiente;
                return true;
            }
            
            Entry Node = Nodo;
            while (Node != null){
                if (Node.Siguiente.Username.equals(Username)){
                    Node.Siguiente = Node.Siguiente.Siguiente;
                    return true;
                }
                
                Node = Node.Siguiente;
            }
            
        }

        return false;
    }
    
    public int Search(String Username){
        if (Username.isBlank() || Nodo == null) return -1;
        
        if (Nodo.Username.equals(Username)) return Nodo.Pos;
        
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
