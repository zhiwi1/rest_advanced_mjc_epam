package com.epam.esm.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.JoinTable;
import javax.persistence.PreUpdate;
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
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,CascadeType.MERGE})
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
