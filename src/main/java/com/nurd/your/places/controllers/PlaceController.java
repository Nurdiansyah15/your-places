package com.nurd.your.places.controllers;

import com.nurd.your.places.services.PlaceService;
import com.nurd.your.places.utils.dtos.formator.ResponseBuilder;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/places")
@RequiredArgsConstructor
public class PlaceController {
    @Autowired
    private final PlaceService placeService;

    @GetMapping
    public ResponseEntity<?> findAllPlaces(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Boolean popular
    ) {
        return ResponseBuilder.renderJSON(placeService.findAll(search, limit, popular), "Success", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findPlaceById(@PathVariable String id) {
        return ResponseBuilder.renderJSON(placeService.findById(id), "Success", HttpStatus.OK);
    }
}
