package com.example.lab1.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SunriseSunsetResponse {

    @JsonProperty("results")
    private Results results;

    @JsonProperty("status")
    private String status;

    // Getters and setters
    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class Results {
        @JsonProperty("sunrise")
        private String sunrise;

        @JsonProperty("sunset")
        private String sunset;

        // Getters and setters
        public String getSunrise() {
            return sunrise;
        }

        public void setSunrise(String sunrise) {
            this.sunrise = sunrise;
        }

        public String getSunset() {
            return sunset;
        }

        public void setSunset(String sunset) {
            this.sunset = sunset;
        }
    }
}