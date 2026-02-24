package com.andre.movierating.controller;

import com.andre.movierating.domain.dto.MovieResponseDTO;
import com.andre.movierating.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/{imdbId}")
    public MovieResponseDTO getMovie(@PathVariable String imdbId) {
        return movieService.getMovie(imdbId);
    }
}