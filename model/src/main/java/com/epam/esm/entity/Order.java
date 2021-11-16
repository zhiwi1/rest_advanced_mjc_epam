package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.ZonedDateTime;


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
    @ManyToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private Long certificateId;
}
