package com.andre.movierating.service;

import com.andre.movierating.domain.dto.request.MarkMovieAsWatchedRequestDTO;
import com.andre.movierating.domain.dto.response.MovieDetailsResponseDTO;
import com.andre.movierating.domain.dto.response.MovieResponseDTO;
import com.andre.movierating.domain.dto.response.UserMovieResponseDTO;
import com.andre.movierating.domain.model.UserMovie;
import com.andre.movierating.exception.ResourceNotFoundException;
import com.andre.movierating.domain.model.Movie;
import com.andre.movierating.repository.MovieRepository;
import com.andre.movierating.repository.UserMovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final OmdbService omdbService;
    private final UserMovieRepository userMovieRepository;

    public MovieResponseDTO getMovie(String imdbId) {

        Movie movie = movieRepository.findByImdbId(imdbId)
                .orElseGet(() -> {
                    Movie fetched = omdbService.fetchAndSaveMovie(imdbId);

                    if (fetched == null) {
                        throw new ResourceNotFoundException(
                                "Movie with imdbId " + imdbId + " not found");
                    }

                    return fetched;
                });

        return MovieResponseDTO.builder()
                .imdbId(movie.getImdbId())
                .title(movie.getTitle())
                .year(movie.getYear())
                .poster(movie.getPoster())
                .imdbRating(movie.getImdbRating())
                .build();
    }

    public void markMovieAsWatched(MarkMovieAsWatchedRequestDTO request) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String userId = authentication.getName();

        // Ensure movie exists
        Movie movie = movieRepository.findByImdbId(request.getImdbId())
                .orElseGet(() -> {
                    Movie fetched = omdbService.fetchAndSaveMovie(request.getImdbId());

                    if (fetched == null) {
                        throw new ResourceNotFoundException(
                                "Movie with imdbId " + request.getImdbId() + " not found");
                    }

                    return fetched;
                });

        // Create or update rating
        UserMovie userMovie = userMovieRepository
                .findByUserIdAndImdbId(userId, request.getImdbId())
                .orElse(UserMovie.builder()
                        .userId(userId)
                        .imdbId(movie.getImdbId())
                        .build());

        userMovie.setPersonalRating(request.getRating());
        userMovie.setWatchedAt(LocalDate.now());

        userMovieRepository.save(userMovie);
    }

    public MovieDetailsResponseDTO getMovieDetailsForUser(String imdbId) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String userId = authentication.getName();

        // 1️⃣ Ensure movie exists (reuse your clean logic)
        Movie movie = movieRepository.findByImdbId(imdbId)
                .orElseGet(() -> {
                    Movie fetched = omdbService.fetchAndSaveMovie(imdbId);

                    if (fetched == null) {
                        throw new ResourceNotFoundException(
                                "Movie with imdbId " + imdbId + " not found");
                    }

                    return fetched;
                });

        // 2️⃣ Get user-specific data
        UserMovie userMovie = userMovieRepository
                .findByUserIdAndImdbId(userId, imdbId)
                .orElse(null);

        // 3️⃣ Build response
        return MovieDetailsResponseDTO.builder()
                .imdbId(movie.getImdbId())
                .title(movie.getTitle())
                .year(movie.getYear())
                .poster(movie.getPoster())
                .imdbRating(movie.getImdbRating())
                .yourRating(userMovie != null ? userMovie.getPersonalRating() : null)
                .watchedAt(userMovie != null ? userMovie.getWatchedAt() : null)
                .build();
    }

    public List<UserMovieResponseDTO> getUserMovies() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String userId = authentication.getName();

        // 1️⃣ Get all user movies
        List<UserMovie> userMovies = userMovieRepository.findByUserId(userId);

        // 2️⃣ Map to response
        return userMovies.stream()
                .map(userMovie -> {

                    Movie movie = movieRepository.findByImdbId(userMovie.getImdbId())
                            .orElse(null);

                    return UserMovieResponseDTO.builder()
                            .imdbId(userMovie.getImdbId())
                            .title(movie != null ? movie.getTitle() : null)
                            .poster(movie != null ? movie.getPoster() : null)
                            .yourRating(userMovie.getPersonalRating())
                            .watchedAt(userMovie.getWatchedAt())
                            .build();
                })
                .collect(Collectors.toList());
    }
}