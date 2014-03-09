package util;

import exception.DuplicateException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import remote.IRemoteLibrary;
import serializable.Book;
import serializable.Rating;

/**
 * @author Joris Schelfaut
 */
public class IOHelper {
    
    /**
     * Loads the library data from a CSV file (Comma-separated values).
     * @param library the library to load the data to.
     * @param file the CSV file to read data from.
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static void loadDataFromCSV(IRemoteLibrary library, String file)
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
