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


@Entity
@Table(name = "tags")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Audited
public class Tag extends com.epam.esm.entity.Entity {

    @NonNull
    private String name;
}
