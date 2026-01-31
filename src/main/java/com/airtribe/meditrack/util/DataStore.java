package com.airtribe.meditrack.util;

import java.util.*;

/**
 * Generic DataStore for storing and managing entities.
 * @param <T> the type of entity stored in this data store
 */
public class DataStore<T> {
    
    private List<T> data;
    
    /**
     * Constructs an empty DataStore.
     */
    public DataStore() {
        this.data = new ArrayList<>();
    }
    
    /**
     * Adds an entity to the data store.
     *
     * @param entity the entity to add
     */
    public void add(T entity) {
        if (entity != null) {
            data.add(entity);
        }
    }
    
    /**
     * Removes an entity from the data store.
     *
     * @param entity the entity to remove
     * @return true if the entity was removed, false otherwise
     */
    public boolean remove(T entity) {
        return data.remove(entity);
    }
    
    /**
     * Gets all entities in the data store.
     *
     * @return a list of all entities
     */
    public List<T> getAll() {
        return new ArrayList<>(data);
    }
    
    /**
     * Gets the number of entities in the data store.
     *
     * @return the number of entities
     */
    public int size() {
        return data.size();
    }
    
    /**
     * Checks if the data store is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return data.isEmpty();
    }
    
    /**
     * Clears all entities from the data store.
     */
    public void clear() {
        data.clear();
    }
    
    /**
     * Gets an entity by index.
     *
     * @param index the index of the entity
     * @return the entity at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public T get(int index) {
        return data.get(index);
    }
    
    /**
     * Checks if the data store contains a specific entity.
     *
     * @param entity the entity to search for
     * @return true if the entity is in the store, false otherwise
     */
    public boolean contains(T entity) {
        return data.contains(entity);
    }
}
