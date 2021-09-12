package org.example.services;

public class ApplicationMessageService {

    public void printInitializeWelcomeMessage() {
        System.out.println("===== Welcome to Ciklum app! =====");
    }

    public void printSelectActionMessage() {
        System.out.println("Select an action from menu below, please.");
        System.out.println("0 - create new product");
        System.out.println("1 - show products list");
        System.out.println("2 - create new order with products");
        System.out.println("3 - show orders with products");
        System.out.println("4 - update order entries quantity");
    }

    public void printInvalidActionMessage() {
        System.out.println("You entered invalid value. Please, try again.");
    }
}
