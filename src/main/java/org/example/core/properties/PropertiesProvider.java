package org.example.core.properties;

import java.io.IOException;
import java.util.Properties;

/**
 * Provides application properties
 */
public interface PropertiesProvider {

    /**
     * Provides properties from local.properties
     * @return properties
     * @throws IOException if local.properties doesn't exist
     */
    Properties provideProperties() throws IOException;
}
