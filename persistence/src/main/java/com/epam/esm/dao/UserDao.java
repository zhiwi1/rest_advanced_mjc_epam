package com.epam.esm.dao;

import com.epam.esm.entity.User;
import com.epam.esm.util.Page;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<User> findAll(Page page);

    Optional<User> findById(Long id);

    Optional<User> findByHighestCostOfAllOrders();
}
