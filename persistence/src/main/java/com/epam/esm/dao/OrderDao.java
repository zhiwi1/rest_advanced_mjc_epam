package com.epam.esm.dao;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.util.Page;

import java.util.List;
import java.util.Optional;

/**
 * The interface Order dao.
 */
public interface OrderDao {
    /**
     * Create order.
     *
     * @param order the order
     * @return the created order
     */
    Order create(Order order);

    /**
     * Find by user id list.
     *
     * @param userId the user id
     * @param page   the page
     * @return the list with orders
     */
    List<Order> findByUser(User user, Page page);

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional of order
     */
    Optional<Order> findById(long id);
}

