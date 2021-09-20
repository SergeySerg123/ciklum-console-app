package org.example.core.scanner;

import com.google.inject.Singleton;

import java.util.Scanner;

/**
 * Implementation of
 * @see ScannerProvider
 */
@Singleton
public class ScannerProviderImpl implements ScannerProvider {
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Provides Scanner instance
     * @return Singleton Scanner instance
     */
    @Override
    public Scanner provideScanner() {
        return scanner;
    }
}
