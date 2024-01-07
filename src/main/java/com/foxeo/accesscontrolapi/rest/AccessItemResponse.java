package com.foxeo.accesscontrolapi.rest;

import com.foxeo.accesscontrolapi.domain.AccessItem;

import java.util.UUID;

public record AccessItemResponse(UUID id, String name, String code) {

    public static AccessItemResponse from(AccessItem accessItem) {
        return new AccessItemResponse(accessItem.getId(), accessItem.getName(), accessItem.getCode());
    }
}
