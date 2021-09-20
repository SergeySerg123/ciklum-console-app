package org.example.core.scanner;

import java.util.Scanner;

/**
 * Provides of the
 * @see Scanner instance as singleton.
 */
public interface ScannerProvider {

    /**
     * Provides Scanner instance
     * @return Singleton Scanner instance
     */
    Scanner provideScanner();
}
