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
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    @NonNull
    private String name;
    @Column(name = "description")
    @NonNull
    private String description;
    @Column(name = "price")
    @NonNull
    private BigDecimal price;
    @Column(name = "create_date")
    @NonNull
    private ZonedDateTime createDate;
    @Column(name = "last_update_date")
    @NonNull
    private ZonedDateTime lastUpdateDate;
    @Column(name = "duration")
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
