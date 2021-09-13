package org.example;

import org.example.database.MySqlSchemaCreator;
import org.example.database.SchemaCreator;
import org.example.interfaces.ApplicationRunner;
import org.example.interfaces.CiklumApplicationRunner;

public class Application {
    public static void main(String[] args) {
        seedDBSchemas();

        ApplicationRunner applicationRunner = new CiklumApplicationRunner();
        applicationRunner.run();
    }

    private static void seedDBSchemas() {
        SchemaCreator schemaCreator = new MySqlSchemaCreator();
        schemaCreator.createProductTable();
    }
}
