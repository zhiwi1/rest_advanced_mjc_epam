package com.epam.esm.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
public abstract class Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
}
