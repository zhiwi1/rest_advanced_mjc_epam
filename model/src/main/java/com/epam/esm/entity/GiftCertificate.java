package com.epam.esm.entity;

import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
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
