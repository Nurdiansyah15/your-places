package com.nurd.your.places.controllers;

import com.nurd.your.places.models.Post;
import com.nurd.your.places.services.PostService;
import com.nurd.your.places.utils.dtos.formator.ResponseBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    @Autowired
    private final PostService postService;

    @GetMapping("/public/posts")
    public ResponseEntity<?> findAll(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Integer limit
    ) {
        return ResponseBuilder.renderJSON(postService.findAll(search, limit), "Success", HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<?> findAllUserPosts(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Integer limit
    ) {
        return ResponseBuilder.renderJSON(postService.findAllUserPosts(search, limit), "Success", HttpStatus.OK);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<?> findUserPostById(@PathVariable String id) {
        return ResponseBuilder.renderJSON(postService.findUserPostById(id), "Success", HttpStatus.OK);
    }

    @PostMapping("/posts")
    public ResponseEntity<?> createUserPost(@Valid  @RequestBody Post post) {
        return ResponseBuilder.renderJSON(postService.createUserPost(post), "Success", HttpStatus.CREATED);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<?> updateUserPostById(@PathVariable String id, @RequestBody Post post) {
        return ResponseBuilder.renderJSON(postService.updateUserPostById(id, post), "Success", HttpStatus.CREATED);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<?> deleteUserPostById(@PathVariable String id) {
        postService.deleteUserPostById(id);
        return ResponseBuilder.renderJSON(null, "Success", HttpStatus.OK);
    }
}
