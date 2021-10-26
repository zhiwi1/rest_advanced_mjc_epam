package com.epam.esm.dao;

import com.epam.esm.entity.User;
import com.epam.esm.util.Page;

import java.util.List;
import java.util.Optional;

/**
 * The interface User dao.
 */
public interface UserDao {
    /**
     * Find all list.
     *
     * @param page the page
     * @return the list with users
     */
    List<User> findAll(Page page);

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional of user
     */
    Optional<User> findById(Long id);

    /**
     * Find by highest cost of all orders optional.
     *
     * @return the optional of user
     */
    Optional<User> findByHighestCostOfAllOrders();
}
