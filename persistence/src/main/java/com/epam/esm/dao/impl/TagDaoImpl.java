package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Deprecated(since = "November 2021")
@Repository
@RequiredArgsConstructor
@Slf4j
public class TagDaoImpl implements TagDao {
    private static final String FIND_ALL = "SELECT tag from Tag tag";
    private static final String FIND_BY_NAME = "SELECT t FROM Tag t WHERE t.name = :name";
    private static final String FIND_THE_MOST_WIDELY_USED_TAG =
          "  select tags.name,tags.id from tags " +
                  "join certificate_tags on tags.id=certificate_tags.tag_id " +
                  "where certificate_tags.certificate_id in (select certificateId from certificate_orders" +
                  " where user_id=(select user_id from certificate_orders group by user_id order by sum(price) desc limit 1) ) " +
                  "group by tags.id order by count(tags.id) desc limit 1";

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

    @Override
    public Optional<Tag> findMostPopularOfUser() {
        return entityManager.createNativeQuery(FIND_THE_MOST_WIDELY_USED_TAG, Tag.class)
                .getResultList()
                .stream()
                .findFirst();
    }
}
