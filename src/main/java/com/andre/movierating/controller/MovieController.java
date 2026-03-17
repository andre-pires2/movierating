package com.andre.movierating.controller;

import com.andre.movierating.domain.dto.request.MarkMovieAsWatchedRequestDTO;
import com.andre.movierating.domain.dto.response.MovieDetailsResponseDTO;
import com.andre.movierating.domain.dto.response.MovieResponseDTO;
import com.andre.movierating.domain.dto.response.UserMovieResponseDTO;
import com.andre.movierating.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/{imdbId}")
    public MovieResponseDTO getMovie(@PathVariable String imdbId) {
        return movieService.getMovie(imdbId);
    }

    @PostMapping("/watched")
    public void markMovieWatched(
            @Valid @RequestBody MarkMovieAsWatchedRequestDTO request) {

        movieService.markMovieAsWatched(request);
    }

    @GetMapping("/{imdbId}/me")
    public MovieDetailsResponseDTO getMovieDetailsForUser(
            @PathVariable String imdbId) {

        return movieService.getMovieDetailsForUser(imdbId);
    }

    @GetMapping("/users/me/movies")
    public List<UserMovieResponseDTO> getUserMovies() {
        return movieService.getUserMovies();
    }
}