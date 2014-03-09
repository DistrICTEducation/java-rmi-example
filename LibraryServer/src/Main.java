import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import system.Library;
import util.IOHelper;

/**
 * The Main class contains the main method to run the server.
 * 
 * @author Joris Schelfaut, Gertjan Vanthienen
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Instantiate a new library object :
        Library library = new Library();
        
        // --> register service with RMI Naming.
        
        // Load some test data :
        try {
            IOHelper.loadDataFromCSV(library, "src/data.csv");
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
