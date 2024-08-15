package com.nurd.your.places.models;


import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Place {
    private String id;
    private String name;
    private double latitude;
    private double longitude;
    private Integer rating;
    private List<Post> posts;
}