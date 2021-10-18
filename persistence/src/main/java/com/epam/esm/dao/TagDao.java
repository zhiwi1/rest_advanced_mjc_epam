package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;

/**
 * The interface Tag dao.
 */
public interface TagDao extends BaseDao<Tag, Long> {
    public void attachTag(Long tagId, Long certificateId);
    public List<Tag> findByCertificateId(Long giftCertificateId);
}
