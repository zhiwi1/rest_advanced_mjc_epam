package com.epam.esm.entity;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;

@Entity
@Table(name = "certificates")
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

    public @NonNull String getName() {
        return this.name;
    }

    public @NonNull String getDescription() {
        return this.description;
    }

    public @NonNull BigDecimal getPrice() {
        return this.price;
    }

    public @NonNull ZonedDateTime getCreateDate() {
        return this.createDate;
    }

    public @NonNull ZonedDateTime getLastUpdateDate() {
        return this.lastUpdateDate;
    }

    public @NonNull int getDuration() {
        return this.duration;
    }

    public @NonNull Set<Tag> getTags() {
        return this.tags;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    public void setPrice(@NonNull BigDecimal price) {
        this.price = price;
    }

    public void setCreateDate(@NonNull ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public void setLastUpdateDate(@NonNull ZonedDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public void setDuration(@NonNull int duration) {
        this.duration = duration;
    }

    public void setTags(@NonNull Set<Tag> tags) {
        this.tags = tags;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof GiftCertificate)) return false;
        final GiftCertificate other = (GiftCertificate) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description))
            return false;
        final Object this$price = this.getPrice();
        final Object other$price = other.getPrice();
        if (this$price == null ? other$price != null : !this$price.equals(other$price)) return false;
        final Object this$createDate = this.getCreateDate();
        final Object other$createDate = other.getCreateDate();
        if (this$createDate == null ? other$createDate != null : !this$createDate.equals(other$createDate))
            return false;
        final Object this$lastUpdateDate = this.getLastUpdateDate();
        final Object other$lastUpdateDate = other.getLastUpdateDate();
        if (this$lastUpdateDate == null ? other$lastUpdateDate != null : !this$lastUpdateDate.equals(other$lastUpdateDate))
            return false;
        if (this.getDuration() != other.getDuration()) return false;
        final Object this$tags = this.getTags();
        final Object other$tags = other.getTags();
        if (this$tags == null ? other$tags != null : !this$tags.equals(other$tags)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof GiftCertificate;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        final Object $price = this.getPrice();
        result = result * PRIME + ($price == null ? 43 : $price.hashCode());
        final Object $createDate = this.getCreateDate();
        result = result * PRIME + ($createDate == null ? 43 : $createDate.hashCode());
        final Object $lastUpdateDate = this.getLastUpdateDate();
        result = result * PRIME + ($lastUpdateDate == null ? 43 : $lastUpdateDate.hashCode());
        result = result * PRIME + this.getDuration();
        final Object $tags = this.getTags();
        result = result * PRIME + ($tags == null ? 43 : $tags.hashCode());
        return result;
    }

    public String toString() {
        return "GiftCertificate(name=" + this.getName() + ", description=" + this.getDescription() + ", price=" + this.getPrice() + ", createDate=" + this.getCreateDate() + ", lastUpdateDate=" + this.getLastUpdateDate() + ", duration=" + this.getDuration() + ", tags=" + this.getTags() + ")";
    }
}
