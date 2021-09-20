package org.example.services;

import com.google.inject.Inject;
import org.example.core.logger.Logger;
import org.example.services.interfaces.ApplicationMessageService;

/**
 * Implementation of
 * @see ApplicationMessageService
 */
public class ApplicationMessageServiceImpl implements ApplicationMessageService {

    private final Logger logger;

    /**
     * The constructor
     * @param logger
     */
    @Inject
    public ApplicationMessageServiceImpl(Logger logger) {
        this.logger = logger;
    }

    /**
     * Prints welcome message
     */
    @Override
    public void printInitializeWelcomeMessage() {
        logger.printInfoMessage("===== Welcome to Ciklum app! =====");
    }

    /**
     * Prints selection menu
     */
    @Override
    public void printSelectActionMessage() {
        logger.printInfoMessage("Select an action from menu below, please.");
        logger.printInfoMessage("0 - create new product");
        logger.printInfoMessage("1 - show products list");
        logger.printInfoMessage("2 - show ordered products list");
        logger.printInfoMessage("3 - delete product by ID");
        logger.printInfoMessage("4 - delete all products");
        logger.printInfoMessage("5 - create new order with products");
        logger.printInfoMessage("6 - show order by id");
        logger.printInfoMessage("7 - show orders list");
        logger.printInfoMessage("8 - update products quantity in the order list by order id (background)");
        logger.printInfoMessage("9 - FINISH APP");
    }

    /**
     * Prints error message if something went wrong during working of the application
     */
    @Override
    public void printInvalidActionMessage() {
        logger.printDangerMessage("You entered invalid value. Please, try again.");
    }

    /**
     * Prints exit message
     */
    @Override
    public void printExitMessage() {
        logger.printInfoMessage("===== Ciklum app FINISHED! =====");
    }
}
