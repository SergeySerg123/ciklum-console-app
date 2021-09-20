package org.example.tests.services;

import org.example.core.logger.Logger;
import org.example.database.interfaces.ProductRepository;
import org.example.models.Product;
import org.example.models.viewmodels.OrderedProductViewModel;
import org.example.models.viewmodels.ProductViewModel;
import org.example.services.ProductServiceImpl;
import org.example.services.interfaces.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class ProductServiceTests {
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private Logger logger;

    @Test
    public void getProductById_ProductRepositorySQLException_ReturnsNull() throws SQLException {
        int testId = 1;
        when(productRepository.getProductById(anyInt())).thenThrow(new SQLException());
        productService = new ProductServiceImpl(productRepository, logger);

        ProductViewModel productViewModel = productService.getProductById(testId);

        verify(logger, times(1)).printDangerMessage("Couldn't retrieve product with id '" + testId + "'. SQLException");
        Assertions.assertNull(productViewModel);
    }

    @Test
    public void getProductById_FoundProduct_ReturnsProductViewModel() throws SQLException {
        int testId = 1;
        when(productRepository.getProductById(anyInt())).thenReturn(new ProductViewModel(1, "test", 200, "in_stock"));
        productService = new ProductServiceImpl(productRepository, logger);

        ProductViewModel productViewModel = productService.getProductById(testId);

        verify(logger, never()).printDangerMessage("Couldn't retrieve product with id '" + testId + "'. SQLException");
        Assertions.assertNotNull(productViewModel);
    }

    @Test
    public void getAllOrderedProducts_NotFoundProducts_ReturnsNull() throws SQLException {
        when(productRepository.getAllOrderedProducts()).thenReturn(null);
        productService = new ProductServiceImpl(productRepository, logger);

        List<OrderedProductViewModel> productViewModelList = productService.getAllOrderedProducts();

        verify(logger, never()).printDangerMessage("Couldn't retrieve ordered products. SQLException");
        Assertions.assertNull(productViewModelList);
    }

    @Test
    public void getAllOrderedProducts_ProductRepositorySQLException_ReturnsNull() throws SQLException {
        when(productRepository.getAllOrderedProducts()).thenThrow(new SQLException());
        productService = new ProductServiceImpl(productRepository, logger);

        List<OrderedProductViewModel> productViewModelList = productService.getAllOrderedProducts();

        verify(logger, times(1)).printDangerMessage("Couldn't retrieve ordered products. SQLException");
        Assertions.assertNull(productViewModelList);
    }

    @Test
    public void getAllOrderedProducts_FoundProduct_ReturnsProductViewModel() throws SQLException {
        when(productRepository.getAllOrderedProducts()).thenReturn(getOrderedProductsList());
        productService = new ProductServiceImpl(productRepository, logger);

        List<OrderedProductViewModel> productViewModelList = productService.getAllOrderedProducts();

        verify(logger, never()).printDangerMessage("Couldn't retrieve ordered products. SQLException");
        Assertions.assertNotNull(productViewModelList);
    }

    @Test
    public void getAllProducts_NotFoundProducts_ReturnsNull() throws SQLException {
        when(productRepository.getAllProducts()).thenReturn(null);
        productService = new ProductServiceImpl(productRepository, logger);

        List<ProductViewModel> productViewModelList = productService.getAllProducts();

        verify(logger, never()).printDangerMessage("Couldn't retrieve products. SQLException");
        Assertions.assertNull(productViewModelList);
    }

    @Test
    public void getAllProducts_ProductRepositorySQLException_ReturnsNull() throws SQLException {
        when(productRepository.getAllProducts()).thenThrow(new SQLException());
        productService = new ProductServiceImpl(productRepository, logger);

        List<ProductViewModel> productViewModelList = productService.getAllProducts();

        verify(logger, times(1)).printDangerMessage("Couldn't retrieve products. SQLException");
        Assertions.assertNull(productViewModelList);
    }

    @Test
    public void getAllProducts_FoundProduct_ReturnsProductViewModel() throws SQLException {
        when(productRepository.getAllProducts()).thenReturn(getProductsList());
        productService = new ProductServiceImpl(productRepository, logger);

        List<ProductViewModel> productViewModelList = productService.getAllProducts();

        verify(logger, never()).printDangerMessage("Couldn't retrieve products. SQLException");
        Assertions.assertNotNull(productViewModelList);
    }

    @Test
    public void getProductById_NotFoundProduct_ReturnsNull() throws SQLException {
        int testId = 1;
        when(productRepository.getProductById(anyInt())).thenReturn(null);
        productService = new ProductServiceImpl(productRepository, logger);

        ProductViewModel productViewModel = productService.getProductById(testId);

        verify(logger, never()).printDangerMessage("Couldn't retrieve product with id '" + testId + "'. SQLException");
        Assertions.assertNull(productViewModel);
    }

    @Test
    public void getProductsForQuantityUpdateByOrderId_ProductRepositorySQLException_ReturnsNull() throws SQLException {
        int orderTestId = 1;
        when(productRepository.getProductIdsByOrderId(anyInt())).thenThrow(new SQLException());
        productService = new ProductServiceImpl(productRepository, logger);

        List<OrderedProductViewModel> productViewModelList = productService.getProductsForQuantityUpdateByOrderId(orderTestId);

        verify(logger, times(1)).printDangerMessage("Couldn't retrieve product IDs by order id '" + orderTestId + "'. SQLException");
        Assertions.assertNull(productViewModelList);
    }

    @Test
    public void getProductsForQuantityUpdateByOrderId_FoundProducts_ReturnsOrderedProductViewModelList() throws SQLException {
        int orderTestId = 1;
        when(productRepository.getProductIdsByOrderId(anyInt())).thenReturn(getOrderedProductsList());
        productService = new ProductServiceImpl(productRepository, logger);

        List<OrderedProductViewModel> productViewModelList = productService.getProductsForQuantityUpdateByOrderId(orderTestId);

        verify(logger, never()).printDangerMessage("Couldn't retrieve product IDs by order id '" + orderTestId + "'. SQLException");
        Assertions.assertNotNull(productViewModelList);
        Assertions.assertEquals(3, productViewModelList.size());
    }

    @Test
    public void getProductsForQuantityUpdateByOrderId_NotFoundProducts_ReturnsNull() throws SQLException {
        int orderTestId = 1;
        when(productRepository.getProductIdsByOrderId(anyInt())).thenReturn(null);
        productService = new ProductServiceImpl(productRepository, logger);

        List<OrderedProductViewModel> productViewModelList = productService.getProductsForQuantityUpdateByOrderId(orderTestId);

        verify(logger, never()).printDangerMessage("Couldn't retrieve product IDs by order id '" + orderTestId + "'. SQLException");
        Assertions.assertNull(productViewModelList);
    }

    @Test
    public void createProduct_PassValidParameter_Created() {
        Product product = new Product("test", 200, "in_stock");
        productService = new ProductServiceImpl(productRepository, logger);

        productService.createProduct(product);

        verify(logger, times(1)).printSuccessMessage("Product with name '" + product.getName() + "' was successfully created.");
        verify(logger, never()).printDangerMessage("Couldn't create product. SQLException");
    }

    @Test
    public void createProduct_ProductRepositoryThrowsSQLException_DangerMessage() throws SQLException {
        Product product = new Product("test", 200, "in_stock");
        doThrow(new SQLException()).when(productRepository).createProduct(isA(Product.class));
        productService = new ProductServiceImpl(productRepository, logger);

        productService.createProduct(product);

        verify(logger, never()).printSuccessMessage("Product with name '" + product.getName() + "' was successfully created.");
        verify(logger, times(1)).printDangerMessage("Couldn't create product. SQLException");
    }

    @Test
    public void updateProductQuantityInOrders_PassValidParameters_Updated() {
        int productId = 1;
        int quantity = 10;
        productService = new ProductServiceImpl(productRepository, logger);

        productService.updateProductQuantityInOrders(productId, quantity);

        verify(logger, times(1)).printSuccessMessage("Product quantity in the order list successfully updated.");
        verify(logger, never()).printDangerMessage("Couldn't update product quantity in the order list. SQLException");
    }

    @Test
    public void updateProductQuantityInOrders_ProductRepositoryThrowsSQLException_DangerMessage() throws SQLException {
        int productId = 1;
        int quantity = 10;
        doThrow(new SQLException()).when(productRepository).updateProductQuantityInOrderList(anyInt(), anyInt());
        productService = new ProductServiceImpl(productRepository, logger);

        productService.updateProductQuantityInOrders(productId, quantity);

        verify(logger, never()).printSuccessMessage("Product quantity in the order list successfully updated.");
        verify(logger, times(1)).printDangerMessage("Couldn't update product quantity in the order list. SQLException");
    }

    @Test
    public void deleteProductById_PassValidParameters_Updated() {
        int productId = 1;
        productService = new ProductServiceImpl(productRepository, logger);

        productService.deleteProductById(productId);

        verify(logger, times(1)).printSuccessMessage("Product with id '" + productId + "' was successfully deleted.");
        verify(logger, never()).printDangerMessage("Couldn't delete product with id '" + productId + "'. SQLException");
    }

    @Test
    public void deleteProductById_ProductRepositoryThrowsSQLException_DangerMessage() throws SQLException {
        int productId = 1;
        doThrow(new SQLException()).when(productRepository).deleteProductById(anyInt());
        productService = new ProductServiceImpl(productRepository, logger);

        productService.deleteProductById(productId);

        verify(logger, never()).printSuccessMessage("Product with id '" + productId + "' was successfully deleted.");
        verify(logger, times(1)).printDangerMessage("Couldn't delete product with id '" + productId + "'. SQLException");
    }

    @Test
    public void deleteAllProducts_Deleted_SuccessMessage() {
        productService = new ProductServiceImpl(productRepository, logger);

        productService.deleteAllProducts();

        verify(logger, times(1)).printSuccessMessage("All products with related orders were successfully deleted.");
        verify(logger, never()).printDangerMessage("Couldn't delete all products. SQLException");
    }

    @Test
    public void deleteAllProducts_ProductRepositoryThrowsSQLException_DangerMessage() throws SQLException {
        doThrow(new SQLException()).when(productRepository).deleteAllProducts();
        productService = new ProductServiceImpl(productRepository, logger);

        productService.deleteAllProducts();

        verify(logger, never()).printSuccessMessage("All products with related orders were successfully deleted.");
        verify(logger, times(1)).printDangerMessage("Couldn't delete all products. SQLException");
    }

    private List<OrderedProductViewModel> getOrderedProductsList() {
        List<OrderedProductViewModel> resultList = new ArrayList<>();

        resultList.add(new OrderedProductViewModel(1, "first", 2));
        resultList.add(new OrderedProductViewModel(2, "second", 5));
        resultList.add(new OrderedProductViewModel(3, "third", 10));

        return resultList;
    }

    private List<ProductViewModel> getProductsList() {
        List<ProductViewModel> resultList = new ArrayList<>();

        resultList.add(new ProductViewModel(1, "first", 20, "in_stock"));
        resultList.add(new ProductViewModel(2, "second", 500, "in_stock"));
        resultList.add(new ProductViewModel(3, "third", 10200, "in_stock"));

        return resultList;
    }
}
