package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.User;

import com.epam.esm.util.Page;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {
    private static final String FIND_ALL = "SELECT u FROM User u";
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<User> findAll(Page page) {
        return entityManager.createQuery(FIND_ALL, User.class)
                .setFirstResult((page.getNumber() - 1) * page.getSize())
                .setMaxResults(page.getSize())
                .getResultList();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }


}