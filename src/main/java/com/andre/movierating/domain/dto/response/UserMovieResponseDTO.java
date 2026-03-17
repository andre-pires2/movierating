package com.andre.movierating.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class UserMovieResponseDTO {

    private String imdbId;
    private String title;
    private String poster;

    private Double yourRating;
    private LocalDate watchedAt;
}