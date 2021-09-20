package org.example.core.checker;

/**
 * Provides checking mechanism
 */
public interface Checker {

    /**
     * Check if values is matched
     * @param valueToCheck
     * @return checking result
     */
    boolean isMatch(String valueToCheck);
}
