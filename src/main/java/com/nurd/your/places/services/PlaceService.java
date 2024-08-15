package com.nurd.your.places.services;

import com.nurd.your.places.models.Place;
import com.nurd.your.places.repositories.PlaceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class PlaceService {

    @Autowired
    private GeoApiService geoApiService;

    @Autowired
    private PlaceRepo placeRepo;

    public List<Place> findAll(String search, Integer limit, Boolean popular) {

        List<Place> places = placeRepo.findAll();
        List<Place> placeListFromApi;

        if (limit == null && search == null) {
            placeListFromApi = geoApiService.findAll();
        } else {
            placeListFromApi = geoApiService.findAll(limit, search);
        }

        Set<String> existingPlaceIds = places.stream()
                .map(Place::getId)
                .collect(Collectors.toSet());


        List<Place> newPlaces = placeListFromApi.stream()
                .filter(place -> !existingPlaceIds.contains(place.getId()))
                .toList();

        places.addAll(newPlaces);

        if (popular != null) {
            places = places.stream()
                    .sorted((p1, p2) -> p2.getRating().compareTo(p1.getRating()))
                    .toList();
        }

        if (search != null) {
            places = places.stream()
                    .filter((place) -> Pattern.matches(".*" + Pattern.quote(search.toLowerCase()) + ".*", place.getName().toLowerCase()))
                    .toList();
        }

        if (limit != null) {
            places = places.stream()
                    .limit(limit)
                    .toList();
        }

        return places;
    }

    public Place findById(String id) {
        Place place = placeRepo.findById(id).orElse(null);

        if (place == null) {
            place = geoApiService.findById(id).orElseThrow(() -> new RuntimeException("Place not found"));
        }

        return place;
    }
}
