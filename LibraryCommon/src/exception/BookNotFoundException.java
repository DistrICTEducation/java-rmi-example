package exception;

/**
 * @author Joris Schelfaut
 */
public class BookNotFoundException extends RuntimeException {

    /**
     * Instantiates a new ItemNotFoundException object.
     * @param code 
     */
    public BookNotFoundException(String code) {
        super("The item with code " + code + " could not be"
                + " found in the library.");
    }    
}
