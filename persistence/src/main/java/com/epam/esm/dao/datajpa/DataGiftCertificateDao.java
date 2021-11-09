package com.epam.esm.dao.datajpa;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.util.GiftCertificateQueryCreator;
import com.epam.esm.util.GiftCertificateQueryParam;
import com.epam.esm.util.Page;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

public interface DataGiftCertificateDao extends JpaRepository<GiftCertificate, Long>, JpaSpecificationExecutor<GiftCertificate> {

    Optional<GiftCertificate> findByName(String name);
    @Query(value = "SELECT certificate_id FROM certificate_tags where certificate_tags.tag_id=:tagId",nativeQuery = true)
    Optional<Long> findIdByTagId (Long id);

}
