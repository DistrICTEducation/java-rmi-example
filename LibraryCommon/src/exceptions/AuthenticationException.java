package exceptions;

/**
 *
 * @author Joris Schelfaut
 */
public class AuthenticationException extends RuntimeException {
    
    /**
     * @param username the username of the user that failed to authenticate.
     */
    public AuthenticationException(String username) {
        super("The user with name '" + username +
                "' was not authenticated for this operation.");
    }
}
