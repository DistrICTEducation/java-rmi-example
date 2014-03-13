package exception;

/**
 * @author Joris Schelfaut
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Instantiates a new UserNotFoundException object.
     * @param name 
     */
    public UserNotFoundException(String name) {
        super("The user with name " + name + " could not be"
                + " found in the library.");
    }    
}
