import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import serializable.Book;
import system.Library;
import system.User;

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
            IOHelper.loadUsersFromCSV(library, "src/users.csv");
            IOHelper.loadBooksFromCSV(library, "src/books.csv");
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // show something :
        for (User u : library.getUsers()) {
            System.out.println(u);
        }
        
        for (Book b : library.getBooks()) {
            System.out.println(b);
        }
    }
}
