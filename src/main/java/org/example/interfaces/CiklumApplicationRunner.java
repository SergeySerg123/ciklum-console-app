package org.example.interfaces;

import org.example.configs.DatabaseConfigs;
import org.example.core.ProductFacade;
import org.example.services.ApplicationMessageService;

import java.util.Scanner;

public class CiklumApplicationRunner implements ApplicationRunner {

    private boolean isStopped = false;
    private ProductFacade productFacade = new ProductFacade();
    private final ApplicationMessageService applicationMessageService = new ApplicationMessageService();

    @Override
    public void run() {
        applicationMessageService.printInitializeWelcomeMessage();

        while(!isStopped) {
            applicationMessageService.printSelectActionMessage();
            Scanner inputFromConsole = new Scanner(System.in);

            try {
                int userCommand = inputFromConsole.nextInt();
                proceedAction(userCommand);
            } catch (Exception exception) {
                applicationMessageService.printInvalidActionMessage();
            }
        }
    }

    private void proceedAction(int userCommand) {
        switch (userCommand) {
            case 0 -> productFacade.performCreationProcess();
            case 1 -> productFacade.provideProducts();
            default -> applicationMessageService.printInvalidActionMessage();
        }
    }
}
