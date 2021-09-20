package org.example.tests.core;

import org.example.core.checker.Checker;
import org.example.core.facades.ProductFacade;
import org.example.core.facades.ProductFacadeImpl;
import org.example.core.logger.Logger;
import org.example.core.scanner.ScannerProvider;
import org.example.models.Product;
import org.example.models.viewmodels.ProductViewModel;
import org.example.services.interfaces.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class ProductFacadeImplTests {

    ProductFacade productFacade;

    @Mock
    private Logger logger;

    @Mock
    private ProductService productService;

    @Mock
    private Checker checker;

    @Mock
    private ScannerProvider scannerProvider;

    @Test
    public void provideProductCreation_ProvideValidArguments_Created() {
        String scannerFakeData = "\n testing \n 100 \n 0 \n";
        System.setIn(new ByteArrayInputStream(scannerFakeData.getBytes()));
        when(scannerProvider.provideScanner()).thenReturn(new Scanner(System.in));

        productFacade = new ProductFacadeImpl(logger, productService, checker, scannerProvider);
        productFacade.provideProductCreation();

        verify(logger, times(6)).printInfoMessage(anyString());
        verify(logger, never()).printDangerMessage(anyString());
        verify(productService, times(1)).createProduct(isA(Product.class));
        verify(scannerProvider, times(1)).provideScanner();
    }

    @Test
    public void provideProductCreation_Provide2InvalidStockValues_Created() {
        String scannerFakeData = "\n testing \n 100 \n 5 \n 3 \n 0 \n";
        System.setIn(new ByteArrayInputStream(scannerFakeData.getBytes()));
        when(scannerProvider.provideScanner()).thenReturn(new Scanner(System.in));

        productFacade = new ProductFacadeImpl(logger, productService, checker, scannerProvider);
        productFacade.provideProductCreation();

        verify(logger, times(14)).printInfoMessage(anyString());
        verify(logger, times(2)).printDangerMessage(anyString());
        verify(productService, times(1)).createProduct(isA(Product.class));
        verify(scannerProvider, times(1)).provideScanner();
    }

    @Test
    public void provideProductDeletion_ProvideValidArguments_Deleted() {
        int deletionProductId = 1;
        String validPassword = "000000";

        String scannerFakeData = deletionProductId + "\n" + validPassword;
        System.setIn(new ByteArrayInputStream(scannerFakeData.getBytes()));
        when(scannerProvider.provideScanner()).thenReturn(new Scanner(System.in));
        when(productService.getProductById(deletionProductId)).thenReturn(new ProductViewModel(deletionProductId, "test", 100, "in_stock"));
        when(checker.isMatch(validPassword)).thenReturn(true);
        doNothing().when(productService).deleteProductById(deletionProductId);

        productFacade = new ProductFacadeImpl(logger, productService, checker, scannerProvider);
        productFacade.provideProductDeletion();

        verify(logger, times(2)).printInfoMessage(anyString());
        verify(logger, times(0)).printWarningMessage(anyString());
        verify(logger, times(0)).printDangerMessage(anyString());
        verify(checker, times(1)).isMatch(anyString());
        verify(scannerProvider, times(1)).provideScanner();
    }

    @Test
    public void provideProductDeletion_ProvideInvalidAndValidProductIDs_Deleted() {
        int invalidProductId = 0;
        int validProductId = 1;
        String validPassword = "000000";

        String scannerFakeData = invalidProductId + "\n" + validProductId + "\n" + validPassword;
        System.setIn(new ByteArrayInputStream(scannerFakeData.getBytes()));
        when(scannerProvider.provideScanner()).thenReturn(new Scanner(System.in));
        when(productService.getProductById(anyInt())).thenReturn(null).thenReturn(new ProductViewModel(validProductId, "test", 100, "in_stock"));
        when(checker.isMatch(validPassword)).thenReturn(true);
        doNothing().when(productService).deleteProductById(validProductId);

        productFacade = new ProductFacadeImpl(logger, productService, checker, scannerProvider);
        productFacade.provideProductDeletion();

        verify(logger, times(3)).printInfoMessage(anyString());
        verify(logger, times(1)).printWarningMessage(anyString());
        verify(logger, times(0)).printDangerMessage(anyString());
        verify(checker, times(1)).isMatch(anyString());
        verify(scannerProvider, times(1)).provideScanner();
    }

    @Test
    public void provideProductDeletion_ProvideValidProductIdAndInvalidAndValidPasswords_Deleted() {
        int validProductId = 1;
        String invalidPassword = "111111";
        String validPassword = "000000";

        String scannerFakeData = validProductId + "\n" + invalidPassword + "\n" + validPassword;
        System.setIn(new ByteArrayInputStream(scannerFakeData.getBytes()));
        when(scannerProvider.provideScanner()).thenReturn(new Scanner(System.in));
        when(productService.getProductById(anyInt())).thenReturn(new ProductViewModel(validProductId, "test", 100, "in_stock"));
        when(checker.isMatch(anyString())).thenReturn(false).thenReturn(true);
        doNothing().when(productService).deleteProductById(validProductId);

        productFacade = new ProductFacadeImpl(logger, productService, checker, scannerProvider);
        productFacade.provideProductDeletion();

        verify(logger, times(3)).printInfoMessage(anyString());
        verify(logger, times(0)).printWarningMessage(anyString());
        verify(logger, times(1)).printDangerMessage(anyString());
        verify(checker, times(2)).isMatch(anyString());
        verify(scannerProvider, times(1)).provideScanner();
    }

    @Test
    public void provideAllProductDeletion_ProvideInvalidAndValidPasswords_DeletedAll() {
        String invalidPassword = "111111";
        String validPassword = "000000";

        String scannerFakeData = "\n" + invalidPassword + "\n" + validPassword;
        System.setIn(new ByteArrayInputStream(scannerFakeData.getBytes()));
        when(scannerProvider.provideScanner()).thenReturn(new Scanner(System.in));
        when(checker.isMatch(anyString())).thenReturn(false).thenReturn(true);
        doNothing().when(productService).deleteAllProducts();

        productFacade = new ProductFacadeImpl(logger, productService, checker, scannerProvider);
        productFacade.provideAllProductDeletion();

        verify(logger, times(2)).printInfoMessage(anyString());
        verify(logger, times(0)).printWarningMessage(anyString());
        verify(logger, times(1)).printDangerMessage(anyString());
        verify(checker, times(2)).isMatch(anyString());
        verify(scannerProvider, times(1)).provideScanner();
    }
}
