import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class creates a temporary security policy file.
 *
 * @author Gertjan Vanthienen
 */
public class PolicyFileLocator {

    public static String getLocationOfPolicyFile() {
        try {
            Logger logger = Logger.getLogger(PolicyFileLocator.class.getName());
            File tempFile = File.createTempFile("rental", ".policy");
            try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(tempFile)))) {
                out.println("grant {");
                out.println("permission java.security.AllPermission;");
                out.println("};");
            }
            tempFile.deleteOnExit();
            logger.log(Level.INFO,
                    "<PolicyFileLocator> created a temporary policy file: {0}",
                    tempFile.getAbsolutePath());
            return tempFile.getAbsolutePath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
