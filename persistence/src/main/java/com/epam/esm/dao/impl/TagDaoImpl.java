package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Repository
@RequiredArgsConstructor
@Slf4j
public class TagDaoImpl implements TagDao {
    private static final String FIND_ALL = "SELECT tag from Tag tag";
    private static final String FIND_BY_NAME = "SELECT t FROM Tag t WHERE t.name = :name";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Tag> findAll(Page page) {
        return entityManager.createQuery(FIND_ALL, Tag.class)
                .setFirstResult((page.getNumber() - 1) * page.getSize())
                .setMaxResults(page.getSize())
                .getResultList();
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return entityManager.createQuery(FIND_BY_NAME, Tag.class)
                .setParameter("name", name)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public Tag create(Tag tag) {
        entityManager.persist(tag);
        return tag;
    }


    @Override
    public void delete(Long id) {
        Tag tag = entityManager.find(Tag.class, id);
        entityManager.remove(tag);
    }


    @Override
    public void attachTag(Tag tag, GiftCertificate giftCertificate) {
        giftCertificate.getTags().add(tag);
        entityManager.merge(giftCertificate);
    }

}
