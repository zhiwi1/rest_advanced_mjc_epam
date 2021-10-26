package com.epam.esm.entity;

import lombok.RequiredArgsConstructor;
import lombok.NonNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.CascadeType;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Set;

@Entity
@Table(name = "certificates")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Audited
public class GiftCertificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NonNull
    private String name;
    @NonNull
    private String description;
    @NonNull
    private BigDecimal price;
    @NonNull
    private ZonedDateTime createDate;
    @NonNull
    private ZonedDateTime lastUpdateDate;
    @NonNull
    private int duration;
    @ManyToMany(cascade = { CascadeType.ALL})
    @JoinTable(
            name = "certificate_tags",
            joinColumns = @JoinColumn(name = "certificate_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @NonNull
    private Set<Tag> tags;
}
