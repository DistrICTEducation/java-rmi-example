
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import remote.IRemoteLibraryModule;
import remote.IRemoteSessionModule;
import rmi.PolicyFileLocator;
import rmi.RMISettings;
import ui.ApplicationView;

/**
 * @author Joris Schelfaut
 */
public class Main {
    public static void main (String[] args) {
        try {
            setupRMI();
            
            Registry registry = LocateRegistry.getRegistry(RMISettings.REGISTRY_HOST, RMISettings.REGISTRY_PORT);
            
            IRemoteSessionModule sessionModule = (IRemoteSessionModule) registry.lookup(RMISettings.SESSION_SERVICE_NAME);
            IRemoteLibraryModule libraryModule = (IRemoteLibraryModule) registry.lookup(RMISettings.LIBRARY_SERVICE_NAME);
            
            ApplicationView applicationView = new ApplicationView(sessionModule, libraryModule);
        } catch (RemoteException | NotBoundException e) {
            System.err.println(e.getClass().getName() + " : " + e.getLocalizedMessage());
        }
    }
    
    static final void setupRMI() {
        System.setProperty("java.security.policy", PolicyFileLocator.getLocationOfPolicyFile());
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
    }

    final static void disableSecurityManager() {
        if (System.getSecurityManager() != null) {
            System.setSecurityManager(null);
        }
    }
}
