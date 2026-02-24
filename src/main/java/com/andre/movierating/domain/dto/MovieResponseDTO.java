package com.andre.movierating.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovieResponseDTO {

    private String imdbId;
    private String title;
    private String year;
    private String poster;
    private String imdbRating;
}