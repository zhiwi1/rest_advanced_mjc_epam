package com.epam.esm.util;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.OrderType;
import com.epam.esm.entity.SortType;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.List;

@UtilityClass
public class GiftCertificateSpecification {

    private static final String CERTIFICATE_TAGS_ATTRIBUTE_NAME = "tags";
    private static final String NAME_ATTRIBUTE = "name";
    private static final String DESCRIPTION_ATTRIBUTE = "description";


    public Specification<GiftCertificate> nameLike(String name) {
        return (root, query, criteriaBuilder)
                -> StringUtils.isNotEmpty(name) ? criteriaBuilder.like(root.get(NAME_ATTRIBUTE), "%" + name + "%") : null;
    }

    public Specification<GiftCertificate> descriptionLike(String description) {
        return (root, query, criteriaBuilder)
                -> StringUtils.isNotEmpty(description) ? criteriaBuilder.like(root.get(DESCRIPTION_ATTRIBUTE), "%" + description + "%") : null;
    }

    public Specification<GiftCertificate> findByTags(String[] arrayOftags) {
        return (root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(arrayOftags)) {
                return null;
            }
            Join<GiftCertificate, Tag> join = root.join(CERTIFICATE_TAGS_ATTRIBUTE_NAME, JoinType.INNER);
            query.groupBy(root);
            List<String> tags = List.of(arrayOftags);
            query.having(criteriaBuilder.equal(criteriaBuilder.count(root), tags.size()));
            return criteriaBuilder.in(join.get(NAME_ATTRIBUTE)).value(tags);
        };
    }

    public Specification<GiftCertificate> havingOrderAndSort(OrderType order, SortType sort) {
        return (root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(order)) {
                return null;
            }
            String orderFieldName = order.getName();
            createQueryOrderByAndSort(sort, query, criteriaBuilder, orderFieldName, root);
            return null;
        };
    }

    private CriteriaQuery createQueryOrderByAndSort(SortType sort, CriteriaQuery query, CriteriaBuilder criteriaBuilder, String orderFieldName, Root<GiftCertificate> root) {
        return isSortTypeIsPresentAndEqualsDesc(sort) ? query.orderBy(criteriaBuilder.desc(root.get(orderFieldName))) : query.orderBy(criteriaBuilder.asc(root.get(orderFieldName)));
    }

    private static boolean isSortTypeIsPresentAndEqualsDesc(SortType sortType) {
        return sortType == SortType.DESC;
    }
}
