package org.loadx.application.db.dao;

import org.loadx.application.db.entity.LoadxEntity;

import java.util.List;

public interface Dao<T extends LoadxEntity> {

    /**
     * Returns one row for given id and entity type.
     *
     * @param id   identifier of the persisted entity in database.
     * @param type entity type.
     * @return item from database.
     */
    T getById(int id, Class<T> type);

    /**
     * Returns all rows for given type of entity.
     *
     * @param type entity for which row will be returned.
     * @return list of records for given type.
     */
    List<T> getAll(Class<T> type);

    /**
     * Saves the new object to database.
     * <p>
     * Returns the generated identifier.
     *
     * @param item object to insert to database
     * @return genereated identifier
     */
    int save(T item);

    /**
     * Saves the bunch of items to database.
     * <p>
     * Returns the list of generated identifiers for those objects.
     *
     * @param items list of items to be persisted to database.
     * @return list of generated identifiers.
     */
    List<Integer> save(List<T> items);

    /**
     * Updates existing records in database.
     *
     * @param item record to be updated.
     */
    void update(T item);

    /**
     * Removes given item from database.
     *
     * @param item to be removed.
     */
    void remove(T item);

    /**
     * TODO: write javadoc
     * @param items
     */
    void remove(List<T> items);

    /**
     * Saves all outside of transaction.
     *
     * @param items objects to persist in data base.
     * @return list of saved ids.
     */
    List<Integer> saveAll(List<T> items);
}
