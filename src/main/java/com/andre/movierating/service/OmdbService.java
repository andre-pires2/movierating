package com.andre.movierating.service;

import com.andre.movierating.external.omdb.OmdbResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OmdbService {

    private final RestTemplate restTemplate;

    @Value("${omdb.api.key}")
    private String apiKey;

    public OmdbResponse fetchMovieByImdbId(String imdbId) {

        String url = "https://www.omdbapi.com/?i=" + imdbId + "&apikey=" + apiKey;

        return restTemplate.getForObject(url, OmdbResponse.class);
    }
}