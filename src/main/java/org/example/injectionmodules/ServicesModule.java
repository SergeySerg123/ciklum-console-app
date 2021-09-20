package org.example.injectionmodules;

import com.google.inject.AbstractModule;
import org.example.services.ApplicationMessageServiceImpl;
import org.example.services.OrderServiceImpl;
import org.example.services.ProductServiceImpl;
import org.example.services.interfaces.ApplicationMessageService;
import org.example.services.interfaces.OrderService;
import org.example.services.interfaces.ProductService;

/**
 * Module with services dependencies
 */
public class ServicesModule extends AbstractModule {

    protected void configure() {
        bind(ApplicationMessageService.class).to(ApplicationMessageServiceImpl.class);
        bind(OrderService.class).to(OrderServiceImpl.class);
        bind(ProductService.class).to(ProductServiceImpl.class);
    }
}
