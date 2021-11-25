package com.epam.esm.dao.datajpa;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface DataGiftCertificateDao extends JpaRepository<GiftCertificate, Long>, JpaSpecificationExecutor<GiftCertificate> {

    Optional<GiftCertificate> findByName(String name);
    @Query(value = "SELECT certificate_id FROM certificate_tags where certificate_tags.tag_id=:tagId",nativeQuery = true)
    Optional<Long> findIdByTagId (Long id);

}
