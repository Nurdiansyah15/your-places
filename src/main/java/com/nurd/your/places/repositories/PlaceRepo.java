package com.nurd.your.places.repositories;

import com.nurd.your.places.models.Place;
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

@Repository
public class PlaceRepo {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<Place> findAll() {
        String sql = "SELECT * FROM places";
        return jdbcTemplate.query(sql, new PlaceMapper());
    }

    public Optional<Place> findById(String id) {
        String sql = "SELECT * FROM places WHERE id = ?";

        try {
            Place place = jdbcTemplate.queryForObject(sql, new Object[]{id}, new PlaceMapper());
            return Optional.ofNullable(place);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Transactional
    public Place create(Place obj) {
        String sql = "INSERT INTO places (id, name, latitude, longitude, rating) VALUES (?, ?, ?, ?, ?)";
        int result = jdbcTemplate.update(sql, obj.getId(), obj.getName(), obj.getLatitude(), obj.getLongitude(), obj.getRating());
        if (result == 0) {
            throw new RuntimeException("Place not created");
        }
        return findById(obj.getId()).get();
    }


    @Transactional
    public Place update(String id, Place obj) {
        String sql = "UPDATE places SET name = ?, latitude = ?, longitude = ?, rating = ? WHERE id = ?";
        int result = jdbcTemplate.update(sql, obj.getName(), obj.getLatitude(), obj.getLongitude(), obj.getRating(), id);
        if (result == 0) {
            throw new RuntimeException("Place not created");
        }
        return findById(id).get();
    }

    @Transactional
    public void delete(String id) {
        String sql = "DELETE FROM places WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public static class PlaceMapper implements RowMapper<Place> {
        @Override
        public Place mapRow(ResultSet rs, int rowNum) throws SQLException {
            Place place = new Place();
            place.setId(rs.getString("id"));
            place.setName(rs.getString("name"));
            place.setLatitude(rs.getDouble("latitude"));
            place.setLongitude(rs.getDouble("longitude"));
            place.setRating(rs.getInt("rating"));
            return place;
        }
    }

}

