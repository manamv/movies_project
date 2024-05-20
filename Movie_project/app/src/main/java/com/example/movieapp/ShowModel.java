package com.example.movieapp;

import java.io.Serializable;

public class ShowModel implements Serializable {
    private final String name;
    private final String language;
    private final String premieredDate;
    private final String summary;
    private final String imageUrl;
    private final String id;

    public ShowModel(String name, String language, String premieredDate, String summary, String imageUrl, String id) {
        this.name = name;
        this.language = language;
        this.premieredDate = premieredDate;
        this.summary = summary;
        this.imageUrl = imageUrl;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getLanguage() {
        return language;
    }

    public String getPremierDate() {
        return premieredDate;
    }

    public String getSummary() {
        return summary;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getId() {
        return id;
    }
}
