package Logic;

public enum Trophy {
    Platino(5, "platino"), Oro(3, "oro"), Plata(2, "plata"), Bronce(1, "bronce");
    
    public final int Points;
    public final String Type;
    
    Trophy(int Points, String Type){
        this.Points = Points;
        this.Type = Type;
    }
    
}
