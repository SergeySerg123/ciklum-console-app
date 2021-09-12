package org.example;

import org.example.interfaces.ApplicationRunner;
import org.example.interfaces.CiklumApplicationRunner;

public class Application {
    public static void main(String[] args) {
        ApplicationRunner applicationRunner = new CiklumApplicationRunner();
        applicationRunner.run();
    }
}
