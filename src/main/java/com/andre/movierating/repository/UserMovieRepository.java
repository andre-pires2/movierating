package com.andre.movierating.repository;

import com.andre.movierating.domain.model.UserMovie;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserMovieRepository extends MongoRepository<UserMovie, String> {

    Optional<UserMovie> findByUserIdAndImdbId(String userId, String imdbId);

    List<UserMovie> findByUserId(String userId);
}