package com.nurd.your.places.repositories;

import com.nurd.your.places.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PostRepo {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Post> findAll() {
        String sql = "SELECT * FROM posts";
        return jdbcTemplate.query(sql, new PostMapper());
    }

    public Optional<Post> findById(String id) {
        String sql = "SELECT * FROM posts WHERE id = ?";
        try {
            Post post = jdbcTemplate.queryForObject(sql, new Object[]{id}, new PostMapper());
            return Optional.ofNullable(post);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

//    public List<Post> findAllUserPosts(String userId) {
//        String sql = "SELECT * FROM posts WHERE user_id = ?";
//        return jdbcTemplate.query(sql, new Object[]{userId}, new PostMapper());
//    }

    @Transactional
    public Post create(Post post) {
        String id = UUID.randomUUID().toString();
        String sql = "INSERT INTO posts (id, title, description, picture, rating, user_id, place_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int result = jdbcTemplate.update(sql, id, post.getTitle(), post.getDescription(), post.getPicture(), post.getRating(), post.getUserId(), post.getPlaceId());
        if (result == 0) {
            throw new RuntimeException("Post not created");
        }
        return findById(id).get();
    }

    @Transactional
    public Post update(String id, Post post) {
        String sql = "UPDATE posts SET title = ?, description = ?, picture = ?, rating = ?, user_id = ?, place_id = ? WHERE id = ?";
        int result = jdbcTemplate.update(sql, post.getTitle(), post.getDescription(), post.getPicture(), post.getRating(), post.getUserId(), post.getPlaceId(), id);
        if (result == 0) {
            throw new RuntimeException("Post not created");
        }
        return findById(id).get();
    }

    @Transactional
    public void delete(String id) {
        jdbcTemplate.update("DELETE FROM posts WHERE id = ?", id);
    }

    public static class PostMapper implements RowMapper<Post> {
        @Override
        public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
            Post post = new Post();
            post.setId(rs.getString("id"));
            post.setTitle(rs.getString("title"));
            post.setDescription(rs.getString("description"));
            post.setPicture(rs.getString("picture"));
            post.setRating(rs.getInt("rating"));
            post.setUserId(rs.getString("user_id"));
            post.setPlaceId(rs.getString("place_id"));
            return post;
        }
    }
}
