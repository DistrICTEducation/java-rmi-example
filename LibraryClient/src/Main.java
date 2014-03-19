
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import remote.IRemoteLibraryModule;
import remote.IRemoteSessionModule;
import rmi.RMISettings;
import ui.ApplicationView;

/**
 * @author Joris Schelfaut
 */
public class Main {
    public static void main (String[] args) {
        try {
            // Set the security manager :
            if (System.getSecurityManager() != null) {
                System.setSecurityManager(null);
            }
            
            // Locate the registry :
            Registry registry = LocateRegistry.getRegistry(RMISettings.REGISTRY_HOST, RMISettings.REGISTRY_PORT);
            
            IRemoteSessionModule sessionModule = (IRemoteSessionModule) registry.lookup(RMISettings.SESSION_SERVICE_NAME);
            IRemoteLibraryModule libraryModule = (IRemoteLibraryModule) registry.lookup(RMISettings.LIBRARY_SERVICE_NAME);
            
            ApplicationView applicationView = new ApplicationView(sessionModule, libraryModule);
        } catch (RemoteException | NotBoundException e) {
            System.err.println(e.getClass().getName() + " : " + e.getLocalizedMessage());
        }
    }
}
