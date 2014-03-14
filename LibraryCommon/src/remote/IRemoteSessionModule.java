package remote;

import exceptions.AuthenticationException;
import args.Session;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * IRemoteSessionModule manages user sessions. The interface provides
 * functionality to create and remove sessions and verify whether or not
 * a user is authenticated/authorized to perform a certain action.
 * 
 * @author Joris Schelfaut
 */
public interface IRemoteSessionModule extends Remote {
    
    /**
     * @param username the username of the user to authenticate.
     * @param password the password of the user to authenticate.
     * @return the session for the authenticated user.
     * @throws AuthenticationException
     * @throws RemoteException
     */
    public Session authenticate(String username, String password)
            throws AuthenticationException, RemoteException;

    /**
     * @param session the session to validate.
     * @return whether or not the session is valid.
     * @throws RemoteException
     */
    public boolean isAuthenticated(Session session) throws RemoteException;
    
    /**
     * @param username the username of the user to log off.
     * @throws RemoteException
     */
    public void destroySession(String username) throws RemoteException;
}
