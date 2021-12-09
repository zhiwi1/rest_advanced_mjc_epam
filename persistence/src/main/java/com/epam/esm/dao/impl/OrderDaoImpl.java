package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.util.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
/**
 * @deprecated
 * because project use datajpa since 3 version
 *
 */
@Repository
@RequiredArgsConstructor
@Deprecated(since = "November 2021")
public class OrderDaoImpl implements OrderDao {
    private static final String FIND_BY_USER_ID = "SELECT o FROM Order o WHERE o.user = :user";
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Order create(Order order) {
        entityManager.persist(order);
        return order;
    }

    @Override
    public List<Order> findByUser(User user, Page page) {
        return entityManager.createQuery(FIND_BY_USER_ID, Order.class)
                .setParameter("user", user)
                .setFirstResult((page.getNumber() - 1) * page.getSize())
                .setMaxResults(page.getSize())
                .getResultList();
    }

    @Override
    public Optional<Order> findById(long id) {
        return Optional.ofNullable(entityManager.find(Order.class, id));
    }

}
