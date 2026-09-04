package com.unifilm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class TmdbService {

    @Value("${tmdb.api.token}")
    private String token;

    @Value("${tmdb.api.base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();


    public String searchMovies(String query) {

        String url = UriComponentsBuilder
                .fromUriString(baseUrl + "/search/movie")
                .queryParam("query", query)
                .queryParam("language", "en-US")
                .queryParam("include_adult", false)
                .build()
                .toUriString();


        HttpHeaders headers = new HttpHeaders();

        headers.setBearerAuth(token);


        HttpEntity<String> entity =
                new HttpEntity<>(headers);


        int maxAttempts = 3;


        for (
                int attempt = 1;
                attempt <= maxAttempts;
                attempt++
        ) {

            try {

                ResponseEntity<String> response =
                        restTemplate.exchange(
                                url,
                                HttpMethod.GET,
                                entity,
                                String.class
                        );


                return response.getBody();


            } catch (Exception e) {

                if (attempt == maxAttempts) {
                    throw e;
                }


                try {

                    Thread.sleep(500);

                } catch (InterruptedException interruptedException) {

                    Thread.currentThread().interrupt();

                    throw new RuntimeException(
                            interruptedException
                    );

                }

            }

        }


        return null;
    }


    public String getMovieDetails(String movieId) {

        String url = UriComponentsBuilder
                .fromUriString(
                        baseUrl + "/movie/" + movieId
                )
                .queryParam("language", "en-US")
                .build()
                .toUriString();


        HttpHeaders headers = new HttpHeaders();

        headers.setBearerAuth(token);


        HttpEntity<String> entity =
                new HttpEntity<>(headers);


        int maxAttempts = 3;


        for (
                int attempt = 1;
                attempt <= maxAttempts;
                attempt++
        ) {

            try {

                ResponseEntity<String> response =
                        restTemplate.exchange(
                                url,
                                HttpMethod.GET,
                                entity,
                                String.class
                        );


                return response.getBody();


            } catch (Exception e) {

                if (attempt == maxAttempts) {
                    throw e;
                }


                try {

                    Thread.sleep(500);

                } catch (InterruptedException interruptedException) {

                    Thread.currentThread().interrupt();

                    throw new RuntimeException(
                            interruptedException
                    );

                }

            }

        }


        return null;
    }

}