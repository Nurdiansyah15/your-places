package com.nurd.your.places.models;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Post {
    private String id;
    @NotNull
    private String title;
    @NotNull
    private String description;
    private String picture;
    @NotNull
    @Max(value = 5, message = "Rating must be between 0 and 5")
    @Min(value = 0, message = "Rating must be between 0 and 5")
    private Integer rating;
    private String userId;
    @NotNull
    private String placeId;
}