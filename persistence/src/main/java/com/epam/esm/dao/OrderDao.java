package com.epam.esm.dao;

import com.epam.esm.entity.Order;
import com.epam.esm.util.Page;

import java.util.List;
import java.util.Optional;

public interface OrderDao {
    Order create(Order order);

    List<Order> findByUserId(long userId, Page page);

    Optional<Order> findById(long id);
}

