package org.example;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;
import org.example.database.interfaces.SchemaCreator;
import org.example.core.applicationrunner.ApplicationRunner;
import org.example.injectionmodules.CoreModule;
import org.example.injectionmodules.DatabaseModule;
import org.example.injectionmodules.ServicesModule;

public class Application {
    @Inject
    private SchemaCreator schemaCreator;
    @Inject
    private ApplicationRunner applicationRunner;

    public static void main(String[] args) {
        Application application = new Application();

        application.registerGuiceDependencies(application);
        application.seedDBSchemas();
        application.runApplication();
    }

    private void registerGuiceDependencies(Application application) {
        Module databaseModule = new DatabaseModule();
        Module servicesModule = new ServicesModule();
        Module coreModule = new CoreModule();

        Injector injector = Guice.createInjector(databaseModule, servicesModule, coreModule);
        injector.injectMembers(application);
    }

    private void seedDBSchemas() {
        schemaCreator.createProductsTable();
        schemaCreator.createOrdersTable();
        schemaCreator.createOrderItemsTable();
    }

    private void runApplication() {
        applicationRunner.run();
    }
}
