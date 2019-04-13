package org.loadx.application.db.dao;

import org.loadx.application.db.entity.LoadxEntity;

/**
 * Generic Dao class for all entities.
 *
 * The class provides with the CRUD operations in a generic way.
 * @param <T> class implementing LoadxEntity
 * @see LoadxEntity
 */
public class Dao<T extends LoadxEntity> {

    public T getById(int id) {
        return null;
    }

}
