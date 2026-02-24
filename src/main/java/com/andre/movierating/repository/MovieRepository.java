package com.andre.movierating.repository;

import com.andre.movierating.domain.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MovieRepository extends MongoRepository<Movie, String> {
    Optional<Movie> findByImdbId(String imdbId);
}