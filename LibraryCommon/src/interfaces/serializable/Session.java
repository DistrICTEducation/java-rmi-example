package interfaces.serializable;

import java.io.Serializable;
import java.util.Objects;

/**
 * Session holds the current username and session key.
 * @author Joris Schelfaut
 */
public class Session implements Serializable {
    
    private final String username;
    private final String sessionkey;

    /**
     * @param username the username of the user of this session.
     * @param sessionkey a generated key that allows authentication.
     */
    public Session(String username, String sessionkey) {
        super();
        this.username = username;
        this.sessionkey = sessionkey;
    }

    public String getSessionkey() {
        return sessionkey;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (! (obj instanceof Session)) return false;
        Session s = (Session) obj;
        if (! s.getUsername().equals(this.getUsername())) return false;
        return s.getSessionkey().equals(this.getSessionkey());
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.username);
        hash = 53 * hash + Objects.hashCode(this.sessionkey);
        return hash;
    }

    @Override
    public String toString() {
        return "Session{" + this.getUsername()
                + "[" + this.getSessionkey() + "]}";
    }
}