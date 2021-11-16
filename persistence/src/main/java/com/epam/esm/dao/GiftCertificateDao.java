package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.util.GiftCertificateQueryParam;
import com.epam.esm.util.Page;

import java.util.List;
import java.util.Optional;


/**
 * The interface Gift certificate dao.
 */
public interface GiftCertificateDao extends BaseDao<GiftCertificate, Long> {

    List<GiftCertificate> findByQueryParameters(
            GiftCertificateQueryParam giftCertificateQueryParameters, Page page);

    /**
     * Update gift certificate.
     *
     * @param giftCertificate the gift certificate
     * @return the gift certificate
     */
    GiftCertificate update(GiftCertificate giftCertificate);

    /**
     * Update last date of updating.
     *
     * @param id the id
     */
    void updateLastDate(Long id);

    /**
     * Find optional id by tag id .
     *
     * @param tagId the tag id
     * @return the optional id of certificate
     */
    Optional<Long> findIdByTagId(Long tagId);


}