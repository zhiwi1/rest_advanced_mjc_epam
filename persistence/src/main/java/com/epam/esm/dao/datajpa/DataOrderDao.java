package com.epam.esm.dao.datajpa;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DataOrderDao extends JpaRepository<Order,Long> {
    List<Order> findAllByUser(User user, Pageable pageable);
}
