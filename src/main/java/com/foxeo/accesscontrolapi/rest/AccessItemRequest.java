package com.foxeo.accesscontrolapi.rest;

import com.foxeo.accesscontrolapi.domain.AccessItem;

import java.util.UUID;

public record AccessItemRequest(String name, String code) {

    static AccessItem toAccessItem(AccessItemRequest request) {
        return new AccessItem(UUID.randomUUID(), request.name, request.code);
    }
}
