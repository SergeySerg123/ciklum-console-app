package org.example.core.logger;

/**
 * Main application logger
 */
public interface Logger {

    /**
     * Prints the information message
     * @param message
     */
    void printInfoMessage(String message);

    /**
     * Prints the success message
     * @param message
     */
    void printSuccessMessage(String message);

    /**
     * Prints the warning message
     * @param message
     */
    void printWarningMessage(String message);

    /**
     * Prints the danger message
     * @param message
     */
    void printDangerMessage(String message);
}
