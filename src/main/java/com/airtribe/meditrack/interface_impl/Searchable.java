package com.airtribe.meditrack.interface_impl;

/**
 * Interface for searchable entities.
 * Provides a contract for entities that can be searched.
 */
public interface Searchable {
    
    /**
     * Searches by ID.
     *
     * @param id the ID to search for
     * @return true if the entity matches the ID, false otherwise
     */
    boolean matchesId(String id);
    
    /**
     * Searches by name.
     *
     * @param name the name to search for
     * @return true if the entity matches the name, false otherwise
     */
    boolean matchesName(String name);
}
