package exceptions;

/**
 * @author Joris Schelfaut
 */
public class BookNotFoundException extends RuntimeException {

    /**
     * Instantiates a new BookNotFoundException object.
     * @param code 
     */
    public BookNotFoundException(String code) {
        super("The book with code " + code + " could not be"
                + " found in the library.");
    }    
}
