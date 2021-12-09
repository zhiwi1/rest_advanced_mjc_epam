package com.epam.esm.dao;

import com.epam.esm.util.Page;

import java.util.List;
import java.util.Optional;

/**
 * The interface Base dao.
 *
 * @param <T> the type parameter
 * @param <K> the type parameter
 *
 * @deprecated
 * because project use datajpa since 3 version
 *
 */
@Deprecated(since = "version 3")
public interface BaseDao<T, K> {
    /**
     * Find all elements list.
     *
     * @return the list
     */
    List<T> findAll(Page page);

    /**
     * Find optional by id .
     *
     * @param id the id
     * @return the optional
     */
    Optional<T> findById(K id);

    /**
     * Find optional by name .
     *
     * @param name the name
     * @return the optional
     */
    Optional<T> findByName(String name);

    /**
     * Create entity .
     *
     * @param entity the entity
     * @return the T
     */
    T create(T entity);

    /**
     * Delete entity.
     *
     * @param id the id
     */
    void delete(K id);

}
