package org.example.core.logger;

/**
 * Implementation of
 * @see Logger
 */
public class ApplicationLogger implements Logger{
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    /**
     * Prints the information message
     * @param message
     */
    @Override
    public void printInfoMessage(String message) {
        System.out.println(message);
    }

    /**
     * Prints the success message
     * @param message
     */
    @Override
    public void printSuccessMessage(String message) {
        System.out.println(ANSI_GREEN + message + ANSI_RESET);
    }

    /**
     * Prints the warning message
     * @param message
     */
    @Override
    public void printWarningMessage(String message) {
        System.out.println(ANSI_YELLOW + message + ANSI_RESET);
    }

    /**
     * Prints the danger message
     * @param message
     */
    @Override
    public void printDangerMessage(String message) {
        System.out.println(ANSI_RED + message + ANSI_RESET);
    }
}
