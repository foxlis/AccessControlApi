package com.foxeo.accesscontrolapi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.UUID;

@Entity
@Table(name = "access_items")
@Value
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class AccessItem {

    @Id
    UUID id;

    @Column(name = "name")
    String name;

    @Column(name = "code")
    String code;

}
