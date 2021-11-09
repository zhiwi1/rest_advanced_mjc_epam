package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;


@Entity
@Table(name = "certificate_orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Audited
public class Order extends com.epam.esm.entity.Entity {
    private BigDecimal price;
    private ZonedDateTime createDate;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinTable(
            name = "order_has_gift_certificate",
            joinColumns = @JoinColumn(name = "gift_order_id"),
            inverseJoinColumns = @JoinColumn(name = "gift_certificate_id"))
    private List<GiftCertificate> certificates;

    @PrePersist
    public void onPersist() {
        setCreateDate(ZonedDateTime.now(ZoneId.systemDefault()));
    }
}
