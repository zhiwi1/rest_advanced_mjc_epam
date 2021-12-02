package com.epam.esm.service;

import com.epam.esm.dto.CertificateTagDto;
import com.epam.esm.dto.TagCreateDto;
import com.epam.esm.dto.TagDto;


/**
 * The interface Tag service.
 */
public interface TagService extends BaseService<TagDto, Long> {

    /**
     * Attach tag to certificate.
     *
     * @param certificateTagDto the certificate tag dto
     */
    void attachTag(CertificateTagDto certificateTagDto);

    /**
     * Create tag dto.
     *
     * @param tagCreateDto the tag create dto
     * @return the tag dto
     */
    TagDto create(TagCreateDto tagCreateDto);

    /**
     * Find most popular tag with highest cost of all orders optional.
     *
     * @return the optional of tag
     */
    TagDto findMostPopularTagWithHighestCostOfAllOrders();
}

