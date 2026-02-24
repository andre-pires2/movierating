package com.andre.movierating.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "movies")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    private String id;

    @Indexed(unique = true)
    private String imdbId;

    private String title;

    private String year;

    private String poster;

    private String imdbRating;
}