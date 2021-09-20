package org.example.services.interfaces;

/**
 * Main application message service.
 * Contains default application messages that represents application states
 */
public interface ApplicationMessageService {

    /**
     * Prints welcome message
     */
    void printInitializeWelcomeMessage();

    /**
     * Prints selection menu
     */
    void printSelectActionMessage();

    /**
     * Prints error message if something went wrong during working of the application
     */
    void printInvalidActionMessage();

    /**
     * Prints exit message
     */
    void printExitMessage();
}
