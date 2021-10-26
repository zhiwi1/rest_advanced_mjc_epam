package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.math.BigDecimal;
import java.time.ZonedDateTime;


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
    private Long id;
    private BigDecimal price;
    private ZonedDateTime createDate;
    private Long userId;
    private Long certificateId;
}
