package org.example.core.facades;

/**
 * Facade for products manipulation
 */
public interface ProductFacade {

    /**
     * Provides product creation
     */
    void provideProductCreation();

    /**
     * Provides all products
     */
    void provideProducts();

    /**
     * Provides all ordered products
     */
    void provideOrderedProducts();

    /**
     * Provides product deletion
     */
    void provideProductDeletion();

    /**
     * Provides all products deletion
     */
    void provideAllProductDeletion();
}
