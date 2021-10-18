package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
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
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
@Slf4j
public class TagDaoImpl implements TagDao {
    private static final String FIND_ALL = "SELECT tag from Tag tag";
    private static final String FIND_BY_NAME = "SELECT t FROM Tag t WHERE t.name = :name";
    private static final String FIND_BY_GIFT_CERTIFICATE_ID = "SELECT id, name FROM tags "
            + "INNER JOIN certificate_tags ON tags.id = certificate_tags.tag_id WHERE "
            + "certificate_id= :certificateId";
    private static final String SQL_ATTACH_TAG = "INSERT IGNORE INTO certificate_tags(certificate_id,tag_id) VALUES(:certificateId,:tagId) ";


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
    public List<Tag> findByCertificateId(Long giftCertificateId) {
      return  entityManager.createNativeQuery(FIND_BY_GIFT_CERTIFICATE_ID,Tag.class)
                .setParameter("certificateId", giftCertificateId)
                .getResultList();
    }

    @Override
    public void attachTag(Long tagId, Long certificateId) {
        entityManager.createNativeQuery(SQL_ATTACH_TAG)
                .setParameter("certificateId",certificateId)
                .setParameter("tagId",tagId)
                .executeUpdate();
    }

}
