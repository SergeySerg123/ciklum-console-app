package org.example.models.viewmodels;

/**
 * Represents product after retrieving from database
 */
public class ProductViewModel {
    private int id;

    private String name;

    private int price;

    private String status;

    public ProductViewModel(int id, String name, int price, String status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }
}
