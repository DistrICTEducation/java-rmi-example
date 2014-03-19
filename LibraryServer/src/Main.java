import exceptions.DuplicateException;
import remote.IRemoteLibraryModule;
import remote.IRemoteSessionModule;
import args.Book;
import args.Rating;
import system.User;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import system.Library;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.StringTokenizer;
import rmi.RMISettings;
import system.LibraryModule;
import system.SessionModule;

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
        try {
            // Instantiate a new library object :
            Library library = new Library();
            
            // Load some test data :
            try {
                loadUsersFromCSV(library, "src/users.csv");
                loadBooksFromCSV(library, "src/books.csv");
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            // Set the security manager :
            if (System.getSecurityManager() != null) {
                System.setSecurityManager(null);
            }
            
            // Create a new registry with given port :
            Registry registry = LocateRegistry.createRegistry(RMISettings.REGISTRY_PORT);
            
            // Register the remote objects :
            IRemoteSessionModule sessionModule = new SessionModule(library);
            IRemoteSessionModule stubSessionModule = (IRemoteSessionModule) UnicastRemoteObject.exportObject(sessionModule, 0);
            registry.rebind(RMISettings.SESSION_SERVICE_NAME, stubSessionModule);

            IRemoteLibraryModule libraryModule = new LibraryModule(library, sessionModule);
            IRemoteLibraryModule stubLibraryModule = (IRemoteLibraryModule) UnicastRemoteObject.exportObject(libraryModule, 0);
            registry.rebind(RMISettings.LIBRARY_SERVICE_NAME, stubLibraryModule);
            
        } catch (RemoteException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * @param library the library to load the users to.
     * @param file the CSV file to load the users from.
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static void loadUsersFromCSV(Library library, String file)
            throws FileNotFoundException, IOException {
        
        BufferedReader in = new BufferedReader(new FileReader(file));
        
        while (in.ready()) {
            String line = in.readLine();
            if (line.startsWith("#")) continue;
            StringTokenizer csvReader = new StringTokenizer(line, ",");
            User user = null;
            try {
                user = new User(csvReader.nextToken(),
                        csvReader.nextToken());
            } catch (IllegalArgumentException iae) {
                printException(iae);
            }
            
            try {
                library.addUser(user);
            } catch (DuplicateException | NullPointerException ex) {
                printException(ex);
            }
        }
    }
    
    /**
     * Loads the library data from a CSV file (Comma-separated values).
     * @param library the library to load the data to.
     * @param file the CSV file to read data from.
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static void loadBooksFromCSV(Library library, String file)
            throws FileNotFoundException, IOException {
        
        BufferedReader in = new BufferedReader(new FileReader(file));
        
        while (in.ready()) {
            String line = in.readLine();
            if (line.startsWith("#")) continue;
            StringTokenizer csvReader = new StringTokenizer(line, ",");
            Book book = null;
            try {
                book = new Book(csvReader.nextToken(),
                        csvReader.nextToken(),
                        Integer.parseInt(csvReader.nextToken()),
                        Rating.valueOf(csvReader.nextToken()),
                        csvReader.nextToken(),
                        csvReader.nextToken());
            } catch (IllegalArgumentException iae) {
                printException(iae);
            }
            
            try {
                library.addBook(book);
            } catch (DuplicateException | NullPointerException ex) {
                printException(ex);
            }
        }
    }
    
    /**
     * @param ex the exception to be printed.
     */
    private static void printException(Exception ex) {
        System.err.println(ex.getClass().getName() + " : " + ex.getLocalizedMessage());
    }
}
