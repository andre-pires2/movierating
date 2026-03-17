package com.andre.movierating.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class MovieDetailsResponseDTO {

    private String imdbId;
    private String title;
    private String year;
    private String poster;
    private String imdbRating;

    private Double yourRating;
    private LocalDate watchedAt;
}