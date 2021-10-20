package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

/**
 * The interface Tag dao.
 */
public interface TagDao extends BaseDao<Tag, Long> {
    public void attachTag(Tag tag, GiftCertificate giftCertificate);
    public Optional<Tag> findMostPopularOfUser(long userId);
}
