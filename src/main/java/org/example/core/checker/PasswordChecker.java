package org.example.core.checker;

import com.google.inject.Inject;
import org.example.core.logger.Logger;
import org.example.core.properties.PropertiesProvider;

import java.io.IOException;
import java.util.Properties;

/**
 * Implementation of
 * @see Checker
 */
public class PasswordChecker implements Checker {
    private final PropertiesProvider propertiesProvider;
    private final Logger logger;

    /**
     * The constructor
     * @param propertiesProvider
     * @param logger
     */
    @Inject
    public PasswordChecker(PropertiesProvider propertiesProvider, Logger logger) {
        this.propertiesProvider = propertiesProvider;
        this.logger = logger;
    }

    /**
     * Check if values is matched
     * @param valueToCheck
     * @return checking result
     */
    @Override
    public boolean isMatch(String valueToCheck){
        Properties properties = null;
        try {
            properties = propertiesProvider.provideProperties();
        } catch (IOException e) {
            logger.printDangerMessage("Couldn't provide properties for password comparison.");
            return false;
        }
        String password = properties.getProperty("password");
        return valueToCheck.equals(password);
    }
}
