package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "certificate_orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Audited
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "create_date")
    private ZonedDateTime createDate;
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//    @ManyToOne
//    @JoinColumn(name="certificate_id")
//    private GiftCertificate giftCertificate;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "certificate_id")
    private Long giftCertificateId;

}
