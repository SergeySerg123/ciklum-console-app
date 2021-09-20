package org.example.core.properties;

import java.io.IOException;
import java.util.Properties;

/**
 * Implementation of
 * @see PropertiesProvider
 */
public class PropertiesProviderImpl implements PropertiesProvider{

    /**
     * Provides properties from local.properties
     * @return properties
     * @throws IOException if local.properties doesn't exist
     */
    public Properties provideProperties() throws IOException {
        Properties properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("local.properties"));
        return properties;
    }
}
