package com.epam.esm.util;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.SortType;
import lombok.experimental.UtilityClass;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

    @UtilityClass
    public  class GiftCertificateQueryCreator {

        private static final String TAGS = "tags";
        private static final String NAME = "name";
        private static final String PERCENT = "%";
        private static final String DESCRIPTION = "description";

        public CriteriaQuery<GiftCertificate> createQuery(GiftCertificateQueryParam certificateQueryParamDto,
                                                          CriteriaBuilder criteriaBuilder) {
            CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
            Root<GiftCertificate> giftCertificateRoot = criteriaQuery.from(GiftCertificate.class);
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
            if (giftCertificateQueryParameters.getTagNames() != null) {
                predicates = Arrays.stream(giftCertificateQueryParameters.getTagNames())
                        .map(tagName -> criteriaBuilder.equal(root.join(TAGS).get(NAME), tagName))
                        .collect(Collectors.toList());
            }
            return predicates;
        }

        private List<Predicate> addName(GiftCertificateQueryParam giftCertificateQueryParameters,
                                        CriteriaBuilder criteriaBuilder,
                                        Root<GiftCertificate> giftCertificateRoot) {
            List<Predicate> predicates = new ArrayList<>();

            if (giftCertificateQueryParameters.getName() != null) {
                predicates.add(criteriaBuilder.like(giftCertificateRoot.get(NAME),
                        PERCENT + giftCertificateQueryParameters.getName() + PERCENT));
            }
            return predicates;
        }

        private List<Predicate> addDescription(GiftCertificateQueryParam giftCertificateQueryParameters,
                                               CriteriaBuilder criteriaBuilder,
                                               Root<GiftCertificate> giftCertificateRoot) {
            List<Predicate> predicates = new ArrayList<>();
            if (giftCertificateQueryParameters.getDescription() != null) {
                predicates.add(criteriaBuilder.like(giftCertificateRoot.get(DESCRIPTION),
                        PERCENT + giftCertificateQueryParameters.getDescription() + PERCENT));
            }
            return predicates;
        }

        private void addOrderAndSortType(GiftCertificateQueryParam giftCertificateQueryParameters,
                                         CriteriaBuilder criteriaBuilder,
                                         CriteriaQuery<GiftCertificate> criteriaQuery,
                                         Root<GiftCertificate> giftCertificateRoot) {
            if (giftCertificateQueryParameters.getOrderType() != null) {
                String orderFieldName = giftCertificateQueryParameters.getOrderType().getName();
                if (giftCertificateQueryParameters.getSortType() != null
                        && giftCertificateQueryParameters.getSortType()==SortType.DESC) {
                    //todo in if in boolean
                    criteriaQuery.orderBy(criteriaBuilder.desc(giftCertificateRoot.get(orderFieldName)));
                } else {
                    criteriaQuery.orderBy(criteriaBuilder.asc(giftCertificateRoot.get(orderFieldName)));
                }

            }
        }
    }


