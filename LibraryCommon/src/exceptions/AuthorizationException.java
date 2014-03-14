package exceptions;

import args.Session;

/**
 * @author Joris Schelfaut
 */
public class AuthorizationException extends RuntimeException {

    public AuthorizationException(Session session) {
        super("The user with name '" + session.getUsername()
                + "' was not authorized to perform this operation.");
    }
}
