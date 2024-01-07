package com.foxeo.accesscontrolapi.rest;

import com.foxeo.accesscontrolapi.usecases.GetAccessItems;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = {"/access-items"})
public class AccessItemsResource {

    private final GetAccessItems getAccessItems;

    public AccessItemsResource(GetAccessItems getAccessItems) {
        this.getAccessItems = getAccessItems;
    }

    @GetMapping("/")
    public ResponseEntity<List<AccessItemResponse>> getAccessItems() {
        var response = getAccessItems.getAccessItems().stream()
                .map(AccessItemResponse::from).toList();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/")
    public ResponseEntity<AccessItemResponse> addAccessItem(@RequestBody AccessItemRequest request) {
        var response = AccessItemResponse.from(getAccessItems.addAccessItem(AccessItemRequest.toAccessItem(request)));
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccessItemResponse> getAccessItem(@PathVariable UUID id) {
        return getAccessItems.getAccessItem(id)
                .map(accessItem -> ResponseEntity.ok().body(AccessItemResponse.from(accessItem)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccessItemResponse> updateAccessItem(@PathVariable UUID id) {
        return ResponseEntity.internalServerError().body(null);
    }
}
