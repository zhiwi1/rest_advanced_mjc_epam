package com.epam.esm.util;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.SortType;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Gift certificate query creator.
 */
@UtilityClass
public class GiftCertificateQueryCreator {

    private static final String TAGS = "tags";
    private static final String NAME = "name";
    private static final String PERCENT = "%";
    private static final String DESCRIPTION = "description";

    /**
     * Create query criteria query for search by tagNames,description, name,and sort (asc,desc) in order of(name,createDate...)
     *
     * @param certificateQueryParamDto the certificate query param dto
     * @param criteriaBuilder          the criteria builder
     * @return the criteria query
     */
    public CriteriaQuery<GiftCertificate> createQuery(GiftCertificateQueryParam certificateQueryParamDto,
                                                      CriteriaBuilder criteriaBuilder) {
        var criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        var giftCertificateRoot = criteriaQuery.from(GiftCertificate.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.addAll(addTagNames(certificateQueryParamDto, criteriaBuilder, giftCertificateRoot));
        predicates.addAll(addName(certificateQueryParamDto, criteriaBuilder, giftCertificateRoot));
        predicates.addAll(addDescription(certificateQueryParamDto, criteriaBuilder, giftCertificateRoot));
        criteriaQuery.select(giftCertificateRoot)
                .where(predicates.toArray(new Predicate[]{}));
        addOrderAndSortType(certificateQueryParamDto, criteriaBuilder, criteriaQuery, giftCertificateRoot);
        return criteriaQuery;
    }

    private List<Predicate> addTagNames(GiftCertificateQueryParam giftCertificateQueryParameters,
                                        CriteriaBuilder criteriaBuilder,
                                        Root<GiftCertificate> root) {
        List<Predicate> predicates = new ArrayList<>();
        if (ArrayUtils.isNotEmpty(giftCertificateQueryParameters.getTagNames())) {
            predicates = Arrays.stream(giftCertificateQueryParameters.getTagNames()).filter(tagName->!tagName.isBlank())
                    .map(tagName -> criteriaBuilder.equal(root.join(TAGS).get(NAME), tagName))
                    .collect(Collectors.toList());
        }
        return predicates;
    }

    private List<Predicate> addName(GiftCertificateQueryParam giftCertificateQueryParameters,
                                    CriteriaBuilder criteriaBuilder,
                                    Root<GiftCertificate> giftCertificateRoot) {
        return createPredicates(giftCertificateQueryParameters.getName(), criteriaBuilder, giftCertificateRoot, NAME);
    }

    private List<Predicate> addDescription(GiftCertificateQueryParam giftCertificateQueryParameters,
                                           CriteriaBuilder criteriaBuilder,
                                           Root<GiftCertificate> giftCertificateRoot) {

        return createPredicates(giftCertificateQueryParameters.getDescription(), criteriaBuilder, giftCertificateRoot, DESCRIPTION);
    }

    private void addOrderAndSortType(GiftCertificateQueryParam queryParam,
                                     CriteriaBuilder criteriaBuilder,
                                     CriteriaQuery<GiftCertificate> criteriaQuery,
                                     Root<GiftCertificate> giftCertificateRoot) {
        if (queryParam.getOrderType() != null) {
            String orderFieldName = queryParam.getOrderType().getName();
            if (isSortTypeIsPresentAndEqualsDesc(queryParam)) {
                criteriaQuery.orderBy(criteriaBuilder.desc(giftCertificateRoot.get(orderFieldName)));
            } else {
                criteriaQuery.orderBy(criteriaBuilder.asc(giftCertificateRoot.get(orderFieldName)));
            }

        }
    }

    private boolean isSortTypeIsPresentAndEqualsDesc(GiftCertificateQueryParam queryParam) {
        return queryParam.getSortType() != null
                && queryParam.getSortType() == SortType.DESC;
    }

    private List<Predicate> createPredicates(String fieldValue,
                                             CriteriaBuilder criteriaBuilder,
                                             Root<GiftCertificate> giftCertificateRoot,
                                             String rootField) {
        List<Predicate> predicates = new ArrayList<>();
        if (StringUtils.isNotEmpty(fieldValue)) {
            predicates.add(criteriaBuilder.like(giftCertificateRoot.get(rootField),
                    PERCENT + fieldValue + PERCENT));
        }
        return predicates;
    }
}


