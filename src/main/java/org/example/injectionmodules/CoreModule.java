package org.example.injectionmodules;

import com.google.inject.AbstractModule;
import org.example.core.applicationrunner.ApplicationRunner;
import org.example.core.applicationrunner.CiklumApplicationRunner;
import org.example.core.background.BackgroundProductQuantityUpdaterTaskRunner;
import org.example.core.background.BackgroundTaskRunner;
import org.example.core.checker.Checker;
import org.example.core.checker.PasswordChecker;
import org.example.core.facades.OrderFacade;
import org.example.core.facades.OrderFacadeImpl;
import org.example.core.facades.ProductFacade;
import org.example.core.facades.ProductFacadeImpl;
import org.example.core.logger.ApplicationLogger;
import org.example.core.logger.Logger;
import org.example.core.properties.PropertiesProvider;
import org.example.core.properties.PropertiesProviderImpl;
import org.example.core.scanner.ScannerProvider;
import org.example.core.scanner.ScannerProviderImpl;

/**
 * Module with core dependencies
 */
public class CoreModule extends AbstractModule {
    protected void configure() {
        // application runner
        bind(ApplicationRunner.class).to(CiklumApplicationRunner.class);

        // background task runner
        bind(BackgroundTaskRunner.class).to(BackgroundProductQuantityUpdaterTaskRunner.class);

        // password checker
        bind(Checker.class).to(PasswordChecker.class);

        // logger
        bind(Logger.class).to(ApplicationLogger.class);

        // providers
        bind(PropertiesProvider.class).to(PropertiesProviderImpl.class);

        // scanner factory
        bind(ScannerProvider.class).to(ScannerProviderImpl.class);

        // facades
        bind(OrderFacade.class).to(OrderFacadeImpl.class);
        bind(ProductFacade.class).to(ProductFacadeImpl.class);
    }
}
