package system;

import exceptions.AuthenticationException;
import exceptions.UserNotFoundException;
import interfaces.remote.IRemoteSessionModule;
import interfaces.serializable.Session;
import interfaces.serializable.User;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

/**
 * Manages the user sessions.
 * 
 * @author Joris Schelfaut
 */
public class SessionModule implements IRemoteSessionModule {
    
    private final Library library;
    private final Set<Session> sessions;

    /**
     * @param library the library whose users are managed through sessions.
     */
    public SessionModule(Library library) {
        super();
        this.library = library;
        this.sessions = new HashSet<>();
    }

    @Override
    public Session authenticate(String username, String password)
            throws AuthenticationException {
        User user = null;
        try {
            user = this.library.lookupUser(username);
        } catch (UserNotFoundException unfe) {
            throw new AuthenticationException(username);
        }
        if (! checkPassword(user, password))
            throw new AuthenticationException(username);
        Session session = new Session(username, generateSessionkey());
        this.sessions.add(session);
        return session;
    }

    @Override
    public boolean isAuthenticated(Session session) {
        return this.sessions.contains(session);
    }

    @Override
    public void destroySession(String username) {
        for (Session s : this.sessions) {
            if (s.getUsername().equals(username)) this.sessions.remove(s);
        }
    }
    
    private String generateSessionkey() {
        String key = "";
        for (int i = 0; i < 8; i++) {
            key += "";
        }
        return key;
    }
    
    /**
     * @param user the user to be authenticated.
     * @param password the password to be checked.
     * @return whether or not the user's password equals the given password.
     */
    private boolean checkPassword(User user, String password) {
        try {
            byte[] bytes = password.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(bytes);
            String hash = digest.toString();
            if (hash.equals(user.getPassword())) return true;
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            return false;
        }
        return false;
    }
}
