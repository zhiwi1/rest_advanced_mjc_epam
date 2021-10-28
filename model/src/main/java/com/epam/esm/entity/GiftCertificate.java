package com.epam.esm.entity;

import lombok.RequiredArgsConstructor;
import lombok.NonNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.persistence.Entity;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;

@Entity
@Table(name = "certificates")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Audited
public class GiftCertificate extends com.epam.esm.entity.Entity {

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
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinTable(
            name = "certificate_tags",
            joinColumns = @JoinColumn(name = "certificate_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @NonNull
    private Set<Tag> tags;
    @PreUpdate
    public void onUpdate() {
        setLastUpdateDate(ZonedDateTime.now(ZoneId.systemDefault()));
    }
}
