package org.example.tests.core;

import org.example.core.checker.Checker;
import org.example.core.checker.PasswordChecker;
import org.example.core.logger.Logger;
import org.example.core.properties.PropertiesProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class PasswordCheckerTests {

    private Checker passwordChecker;

    @Mock
    private PropertiesProvider propertiesProvider;

    @Mock
    private Properties mockProperties;

    @Mock
    private Logger logger;

    @Test
    public void isMatch_PropertiesProviderException_ReturnsFalse() throws IOException {
        when(propertiesProvider.provideProperties()).thenThrow(new IOException());

        passwordChecker = new PasswordChecker(propertiesProvider, logger);
        boolean result = passwordChecker.isMatch("some value string");

        verify(logger, times(1)).printDangerMessage("Couldn't provide properties for password comparison.");
        assertFalse(result);
    }

    @Test
    public void isMatch_MatchedPasswords_ReturnsTrue() throws IOException {
        String userPassword = "000000";
        String propertiesPassword = "000000";
        when(propertiesProvider.provideProperties()).thenReturn(mockProperties);
        when(mockProperties.getProperty("password")).thenReturn(propertiesPassword);

        passwordChecker = new PasswordChecker(propertiesProvider, logger);
        boolean result = passwordChecker.isMatch(userPassword);

        Assertions.assertTrue(result);
    }

    @Test
    public void isMatch_NotMatchedPasswords_ReturnsFalse() throws IOException {
        String userPassword = "111111";
        String propertiesPassword = "000000";
        when(propertiesProvider.provideProperties()).thenReturn(mockProperties);
        when(mockProperties.getProperty("password")).thenReturn(propertiesPassword);

        passwordChecker = new PasswordChecker(propertiesProvider, logger);
        boolean result = passwordChecker.isMatch(userPassword);

        Assertions.assertFalse(result);
    }
}
