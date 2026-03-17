package com.andre.movierating.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user_movies")
@CompoundIndex(name = "user_movie_unique", def = "{'userId': 1, 'imdbId': 1}", unique = true)
public class UserMovie {

    @Id
    private String id;

    @Indexed
    private String userId;

    @Indexed
    private String imdbId;

    private Double personalRating;

    private LocalDate watchedAt;
}