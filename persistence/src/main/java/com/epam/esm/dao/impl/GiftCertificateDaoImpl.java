package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.util.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.Query;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
@Slf4j
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    private static final String FIND_ALL = "SELECT g FROM GiftCertificate g";
    private static final String FIND_BY_NAME = "SELECT g FROM GiftCertificate g WHERE g.name = :name";
    private static final String FIND_BY_QUERY_PARAM = "SELECT certificates.id,certificates.name, "
            + "description, price, duration, create_date, last_update_date FROM certificates "
            + "LEFT JOIN certificate_tags ON certificates.id = certificate_tags.certificate_id "
            + "LEFT JOIN tags ON certificate_tags.tag_id = tags.id ";
    private static final String SQL_UPDATE_LAST_UPD_DATE = "UPDATE GiftCertificate g SET g.lastUpdateDate =:lastUpdateDate WHERE g.id=:certificateId";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        return Optional.ofNullable(entityManager.find(GiftCertificate.class, id));
    }

    @Override
    public Optional<GiftCertificate> findByName(String name) {
        return entityManager.createQuery(FIND_BY_NAME, GiftCertificate.class)
                .setParameter("name", name)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public List<GiftCertificate> findAll(Page page) {
        return entityManager.createQuery(FIND_ALL, GiftCertificate.class)
                .setFirstResult((page.getNumber() - 1) * page.getSize())
                .setMaxResults(page.getSize())
                .getResultList();
    }

    @Override
    public GiftCertificate create(GiftCertificate certificate) {
        certificate.setCreateDate(ZonedDateTime.now(ZoneId.systemDefault()));
        certificate.setLastUpdateDate(ZonedDateTime.now(ZoneId.systemDefault()));
        entityManager.persist(certificate);
        return certificate;
    }

    @Override
    public void delete(Long id) {
        GiftCertificate giftCertificate = entityManager.find(GiftCertificate.class, id);
        entityManager.remove(giftCertificate);
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        giftCertificate.setLastUpdateDate(ZonedDateTime.now(ZoneId.systemDefault()));
        return entityManager.merge(giftCertificate);
    }

    @Override
    public Optional<Long> findIdByTagId(Long tagId) {
        return Optional.ofNullable((Long) entityManager.createNativeQuery(
                        "SELECT certificate_id FROM certificate_tags where certificate_tags.tag_id=:tagId")
                .setParameter("tagId", tagId)
                .getSingleResult());

    }


    @Override
    public List<GiftCertificate> findByQueryParameters(String query, Page page) {
        return entityManager.createNativeQuery(FIND_BY_QUERY_PARAM + query,GiftCertificate.class)
                .setFirstResult((page.getNumber() - 1) * page.getSize())
                .setMaxResults(page.getSize())
                .getResultList();

    }

    @Override
    public void updateLastDate(Long id) {
        entityManager.createQuery(SQL_UPDATE_LAST_UPD_DATE)
                .setParameter("lastUpdateDate",ZonedDateTime.now(ZoneId.systemDefault()))
                .setParameter("certificateId",id)
                .executeUpdate();
    }
}