package com.nurd.your.places.repositories;

import com.nurd.your.places.models.User;
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
public class UserRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, new UserMapper());
    }

    public Optional<User> findById(String id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new Object[]{id}, new UserMapper());
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new Object[]{username}, new UserMapper());
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new Object[]{email}, new UserMapper());
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Transactional
    public User create(User obj) {
        String id = UUID.randomUUID().toString();
        String sql = "INSERT INTO users (id, username, email, point, password, role) VALUES (?, ?, ?, ?, ?, ?)";
        int result = jdbcTemplate.update(sql, id, obj.getUsername(), obj.getEmail(), obj.getPoint(), obj.getPassword(), obj.getRole());
        if (result == 0) {
            throw new RuntimeException("User not created");
        }
        return findById(id).get();
    }

    @Transactional
    public User update(String id, User obj) {
        String sql = "UPDATE users SET username = ?, email = ?, point = ?, password = ?, role = ? WHERE id = ?";
        int result = jdbcTemplate.update(sql, obj.getUsername(), obj.getEmail(), obj.getPoint(), obj.getPassword(), obj.getRole(), id);
        if (result == 0) {
            throw new RuntimeException("User not created");
        }
        return findById(id).get();
    }

    @Transactional
    public void delete(String id) {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public static class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getString("id"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setPoint(rs.getInt("point"));
            user.setRole(rs.getString("role"));
            return user;
        }

    }
}
