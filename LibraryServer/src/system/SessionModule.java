package system;

import args.Book;
import args.Rating;
import exceptions.AuthenticationException;
import exceptions.UserNotFoundException;
import remote.IRemoteSessionModule;
import args.Session;
import exceptions.DuplicateException;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Random;
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
            throws AuthenticationException, RemoteException {
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
    public boolean isAuthenticated(Session session) throws RemoteException {
        return this.sessions.contains(session);
    }

    @Override
    public void destroySession(String username) throws RemoteException {
        for (Session s : this.sessions) {
            if (s.getUsername().equals(username)) this.sessions.remove(s);
        }
    }

    /**
     * @return a new session key.
     */
    private String generateSessionkey() {
        char[] chars = ("abcdefghijklmnopqrstuvwxyz"
                + "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789" + "-_'()!?,.:/")
                .toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 16; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }
    
    /**
     * @param user the user to be authenticated.
     * @param password the password to be checked.
     * @return whether or not the user's password equals the given password.
     */
    private boolean checkPassword(User user, String password) {
        try {
            if (md5(password).equals(user.getPassword())) return true;
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            return false;
        }
        return false;
    }
    
    /**
     * @param password the password to be hashed.
     * @return the md5 hash of the given password.
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException 
     */
    private String md5 (String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] bytes = password.getBytes("UTF-8");
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hash = md.digest(bytes);
        StringBuilder hexString = new StringBuilder();

        for (int i = 0; i < hash.length; i++) {
            if ((0xff & hash[i]) < 0x10) {
                hexString.append("0").append(Integer.toHexString((0xFF & hash[i])));
            } else {
                hexString.append(Integer.toHexString(0xFF & hash[i]));
            }
        }
        return hexString.toString();
    }
    
    /**
     * Test some functionality of this class.
     * @param args arguments for running this class main method.
     */
    public static void main(String[] args) {
        try {
            // md5(george) = 9b306ab04ef5e25f9fb89c998a6aedab
            Library l = new Library();
            
            User u = new User("george", "9b306ab04ef5e25f9fb89c998a6aedab");
            System.out.println(u);
            l.addUser(u);
            
            SessionModule sm = new SessionModule(l);
            LibraryModule lm = new LibraryModule(l, sm);
            
            Session s = sm.authenticate("george", "george");
            System.out.println(s);
            
            Book b1 = new Book("Harry Potter and the Deathly Hallows",
                "J.K. Rowling", 2009, Rating.EXCELLENT, "978-0545139700", "george");
            
            lm.addBook(b1, s);
            for (Book b : lm.getBooks()) System.out.println(b);
        } catch (NullPointerException | DuplicateException | AuthenticationException | RemoteException e) {
            e.printStackTrace();
        }
    }
}
