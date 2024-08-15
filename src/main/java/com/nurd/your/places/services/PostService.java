package com.nurd.your.places.services;

import com.nurd.your.places.exceptions.exceptions.CustomNotFoundException;
import com.nurd.your.places.models.Place;
import com.nurd.your.places.models.Post;
import com.nurd.your.places.models.User;
import com.nurd.your.places.repositories.PlaceRepo;
import com.nurd.your.places.repositories.PostRepo;
import com.nurd.your.places.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PlaceRepo placeRepo;

    @Autowired
    private GeoApiService geoApiService;

    @Autowired
    private AuthService authService;

    public List<Post> findAll(String search, Integer limit) {
        List<Post> posts = postRepo.findAll();
        if (search != null) {
            posts = posts.stream().filter((post) -> post.getTitle().toLowerCase().matches(".*" + Pattern.quote(search.toLowerCase()) + ".*")).toList();
        }
        if (limit != null) {
            return posts.stream().limit(limit).toList();
        }
        return posts;
    }


    public List<Post> findAllUserPosts(String search, Integer limit) {
        User user = authService.getUserAuthenticated();
        List<Post> posts = postRepo.findAll().stream().filter((post -> post.getUserId().equals(user.getId()))).collect(Collectors.toList());

        if (search != null) {
            posts.stream().filter(post -> post.getTitle().toLowerCase().contains(search.toLowerCase())).toList();
        }

        if (limit != null) {
            return posts.stream().limit(limit).toList();
        }
        return posts;
    }


    public Post findUserPostById(String id) {
        return postRepo.findById(id).orElseThrow(() -> new CustomNotFoundException("Post not found"));
    }

    @Transactional
    public Post createUserPost(Post post) {
        User user = authService.getUserAuthenticated();
        Place place = placeRepo.findById(post.getPlaceId()).orElse(null);

        if (place == null) {
            place = geoApiService.findById(post.getPlaceId()).orElseThrow(() -> new CustomNotFoundException("Place not found"));
            place.setRating(place.getRating() + post.getRating());
            placeRepo.create(place);
        } else {
            place.setRating(place.getRating() + post.getRating());
            placeRepo.update(place.getId(), place);
        }

        user.setPoint(user.getPoint() + 100);
        userRepo.update(user.getId(), user);

        Post newPost = Post.builder()
                .title(post.getTitle())
                .description(post.getDescription())
                .picture(post.getPicture())
                .rating(post.getRating())
                .userId(user.getId())
                .placeId(post.getPlaceId())
                .build();

        return postRepo.create(newPost);
    }

    public Post updateUserPostById(String id, Post obj) {

        Post post = postRepo.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));

        User user = authService.getUserAuthenticated();
        if (!Objects.equals(user.getId(), post.getUserId())) {
            throw new IllegalArgumentException("You can't update this post");
        }

        Place place = placeRepo.findById(obj.getPlaceId() == null ? post.getPlaceId() : obj.getPlaceId()).orElseThrow(() -> new RuntimeException("Place not found"));

        if (obj.getRating() != null) {
            place.setRating(place.getRating() - post.getRating());
            place.setRating(place.getRating() + obj.getRating());
            placeRepo.update(place.getId(), place);
        }

        post.setTitle(obj.getTitle() == null ? post.getTitle() : obj.getTitle());
        post.setDescription(obj.getDescription() == null ? post.getDescription() : obj.getDescription());
        post.setPicture(obj.getPicture() == null ? post.getPicture() : obj.getPicture());
        post.setRating(obj.getRating() == null ? post.getRating() : obj.getRating());
        post.setUserId(obj.getUserId() == null ? post.getUserId() : obj.getUserId());
        post.setPlaceId(obj.getPlaceId() == null ? post.getPlaceId() : obj.getPlaceId());

        return postRepo.update(id, post);
    }


    @Transactional
    public void deleteUserPostById(String id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        User user = authService.getUserAuthenticated();
        if (!Objects.equals(user.getId(), post.getUserId())) {
            throw new IllegalArgumentException("You can't update this post");
        }
        Place place = placeRepo.findById(post.getPlaceId()).orElseThrow(() -> new RuntimeException("Place not found"));
        place.setRating(place.getRating() - post.getRating());
        placeRepo.update(place.getId(), place);
        postRepo.delete(id);
    }


}
