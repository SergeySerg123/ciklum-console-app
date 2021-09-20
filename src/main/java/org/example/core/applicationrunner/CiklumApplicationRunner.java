package org.example.core.applicationrunner;

import com.google.inject.Inject;
import org.example.core.facades.OrderFacade;
import org.example.core.facades.ProductFacade;
import org.example.core.scanner.ScannerProvider;
import org.example.services.interfaces.ApplicationMessageService;

import java.util.Scanner;

/**
 * Implementation of
 * @see ApplicationRunner
 */
public class CiklumApplicationRunner implements ApplicationRunner {

    private boolean isStopped = false;

    private final ProductFacade productFacade;
    private final OrderFacade orderFacade;

    private final ApplicationMessageService applicationMessageService;
    private final ScannerProvider scannerProvider;

    /**
     * The constructor
     * @param productFacade
     * @param orderFacade
     * @param applicationMessageService
     * @param scannerProvider
     */
    @Inject
    public CiklumApplicationRunner(ProductFacade productFacade, OrderFacade orderFacade, ApplicationMessageService applicationMessageService, ScannerProvider scannerProvider) {
        this.productFacade = productFacade;
        this.orderFacade = orderFacade;
        this.applicationMessageService = applicationMessageService;
        this.scannerProvider = scannerProvider;
    }

    /**
     * Run application instance
     */
    @Override
    public void run() {
        applicationMessageService.printInitializeWelcomeMessage();

        try (Scanner inputFromConsole = scannerProvider.provideScanner()) {
            while(!isStopped) {
                try {
                    applicationMessageService.printSelectActionMessage();
                    int userCommand = inputFromConsole.nextInt();
                    proceedAction(userCommand);
                } catch (Exception exception) {
                    applicationMessageService.printInvalidActionMessage();
                    inputFromConsole.nextLine(); // move to next line
                }
            }
        }

        applicationMessageService.printExitMessage();
    }

    private void proceedAction(int userCommand) {
        switch (userCommand) {
            case 0 -> productFacade.provideProductCreation();
            case 1 -> productFacade.provideProducts();
            case 2 -> productFacade.provideOrderedProducts();
            case 3 -> productFacade.provideProductDeletion();
            case 4 -> productFacade.provideAllProductDeletion();
            case 5 -> orderFacade.provideOrderCreation();
            case 6 -> orderFacade.provideOrderById();
            case 7 -> orderFacade.provideOrders();
            case 8 -> orderFacade.provideOrderQuantityUpdates();
            case 9 -> { isStopped = true; }
            default -> applicationMessageService.printInvalidActionMessage();
        }
    }
}
