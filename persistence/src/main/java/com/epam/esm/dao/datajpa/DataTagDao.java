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

    @Query(value = "  select tags.name,tags.id from tags\n" +
            "            join certificate_tags on tags.id=certificate_tags.tag_id \n" +
            "            where certificate_tags.certificate_id in (select gift_certificate_id from order_has_gift_certificate join certificate_orders on certificate_orders.id=order_has_gift_certificate.gift_order_id\n" +
            "            where user_id=(select gift_certificate_id from order_has_gift_certificate join certificate_orders on certificate_orders.id=order_has_gift_certificate.gift_order_id\n" +
            "            group by user_id order by sum(price) desc limit 1) )\n" +
            "            group by tags.id order by count(tags.id) desc limit 1", nativeQuery = true)
    Optional<Tag> findMostPopularOfUser();

    default void attachTag(Tag tag, GiftCertificate giftCertificate) {
        giftCertificate.getTags().add(tag);
    }
}
