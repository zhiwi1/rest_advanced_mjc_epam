package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import java.util.Optional;


/**
 * The interface Tag dao.
 */
public interface TagDao extends BaseDao<Tag, Long> {
    /**
     * Attach tag.
     *
     * @param tag             the tag
     * @param giftCertificate the gift certificate
     */
    void attachTag(Tag tag, GiftCertificate giftCertificate);

    /**
     * Find most popular of user optional.
     *
     * @return the optional
     */
    Optional<Tag> findMostPopularOfUser();
}
