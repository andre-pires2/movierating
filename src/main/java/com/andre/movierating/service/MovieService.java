package com.andre.movierating.service;

import com.andre.movierating.domain.dto.MovieResponseDTO;
import com.andre.movierating.domain.dto.OmdbResponse;
import com.andre.movierating.domain.model.Movie;
import com.andre.movierating.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final OmdbService omdbService;

    public MovieResponseDTO getMovie(String imdbId) {

        // 1️⃣ Check cache
        Movie movie = movieRepository.findByImdbId(imdbId)
                .orElseGet(() -> {
                    OmdbResponse response = omdbService.fetchMovieByImdbId(imdbId);

                    Movie newMovie = Movie.builder()
                            .imdbId(response.getImdbId())
                            .title(response.getTitle())
                            .year(response.getYear())
                            .poster(response.getPoster())
                            .imdbRating(response.getImdbRating())
                            .build();

                    return movieRepository.save(newMovie);
                });

        return MovieResponseDTO.builder()
                .imdbId(movie.getImdbId())
                .title(movie.getTitle())
                .year(movie.getYear())
                .poster(movie.getPoster())
                .imdbRating(movie.getImdbRating())
                .build();
    }
}