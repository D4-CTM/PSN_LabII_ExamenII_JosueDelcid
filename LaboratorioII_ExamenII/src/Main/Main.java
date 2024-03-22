package Main;

import GUI.Menu;
import Logic.PSNUser;
import java.io.IOException;

/**
 *
 * @author josue
 */
public class Main {
    
    public static void main(String[] args) throws IOException {
        new Menu(new PSNUser()).setVisible(true);
    }
    
}
