package com.airtribe.meditrack.interface_impl;

/**
 * Interface for payable entities.
 * Provides a contract for entities that have payment information.
 */
public interface Payable {
    
    /**
     * Gets the total amount to be paid.
     *
     * @return the total amount
     */
    double getAmount();
    
    /**
     * Marks the payment as completed.
     */
    void markAsPaid();
    
    /**
     * Checks if payment is completed.
     *
     * @return true if paid, false otherwise
     */
    boolean isPaid();
}
