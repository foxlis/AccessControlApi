package com.foxeo.accesscontrolapi.infrastructure.repository;

import com.foxeo.accesscontrolapi.domain.AccessItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccessItemRepository extends JpaRepository<AccessItem, UUID> {
}
