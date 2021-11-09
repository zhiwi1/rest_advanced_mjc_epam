package com.epam.esm.dao.datajpa;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DataTagDao extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);

    @Query(value = "  select tags.name,tags.id from tags " +
            "join certificate_tags on tags.id=certificate_tags.tag_id " +
            "where certificate_tags.certificate_id in (select certificateId from certificate_orders" +
            " where user_id=(select user_id from certificate_orders group by user_id order by sum(price) desc limit 1) ) " +
            "group by tags.id order by count(tags.id) desc limit 1", nativeQuery = true)
    Optional<Tag> findMostPopularOfUser();

    default void attachTag(Tag tag, GiftCertificate giftCertificate) {
        giftCertificate.getTags().add(tag);
    }

    ;
}
