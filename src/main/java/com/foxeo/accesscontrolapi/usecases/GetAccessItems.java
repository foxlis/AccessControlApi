package com.foxeo.accesscontrolapi.usecases;

import com.foxeo.accesscontrolapi.domain.AccessItem;
import com.foxeo.accesscontrolapi.infrastructure.repository.AccessItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GetAccessItems {
    private final AccessItemRepository accessItemRepository;

    public GetAccessItems(AccessItemRepository accessItemRepository) {
        this.accessItemRepository = accessItemRepository;
    }

    public Optional<AccessItem> getAccessItem(UUID id) {
        return accessItemRepository.findById(id);
    }

    public AccessItem addAccessItem(AccessItem accessItem) {
        return accessItemRepository.save(accessItem);
    }

    public List<AccessItem> getAccessItems() {
        return accessItemRepository.findAll();
    }
}
