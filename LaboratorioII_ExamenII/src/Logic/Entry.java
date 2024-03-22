package Logic;

/**
 *
 * @author josue
 */
public class Entry {
    protected Entry Siguiente;
    protected String Username;
    protected int Pos;
    
    public Entry(String Username, int Pos){
        this.Username = Username;
        this.Siguiente = null;
        this.Pos = Pos;
    }
    
    public Entry getSiguiente() {
        return Siguiente;
    }

    public String getUsername() {
        return Username;
    }

    public int getPos() {
        return Pos;
    }
    
}
