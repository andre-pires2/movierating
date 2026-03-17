package com.andre.movierating.service;

import com.andre.movierating.domain.model.Movie;
import com.andre.movierating.external.omdb.OmdbResponse;
import com.andre.movierating.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OmdbService {

    private final RestTemplate restTemplate;
    private final MovieRepository movieRepository;

    @Value("${omdb.api.key}")
    private String apiKey;

    public OmdbResponse fetchMovieByImdbId(String imdbId) {

        String url = "https://www.omdbapi.com/?i=" + imdbId + "&apikey=" + apiKey;

        OmdbResponse response = restTemplate.getForObject(url, OmdbResponse.class);

        if (response == null || "False".equalsIgnoreCase(response.getResponse())) {
            return null;
        }

        return response;
    }

    public Movie fetchAndSaveMovie(String imdbId) {

        OmdbResponse response = fetchMovieByImdbId(imdbId);

        if (response == null || response.getImdbId() == null) {
            return null;
        }

        Movie movie = mapToMovie(response);

        return movieRepository.save(movie);
    }

    private Movie mapToMovie(OmdbResponse response) {

        return Movie.builder()
                .imdbId(response.getImdbId())
                .title(response.getTitle())
                .year(response.getYear())
                .poster(response.getPoster())
                .build();
    }
}