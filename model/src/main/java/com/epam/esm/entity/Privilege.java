package com.epam.esm.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.Column;

@javax.persistence.Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Audited
public class Privilege extends Entity {
    @NonNull
    @Column( unique = true)
    private String name;
}
