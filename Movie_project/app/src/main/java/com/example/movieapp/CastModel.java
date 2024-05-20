package com.example.movieapp;

public class CastModel {
    private String actorName;
    private String actorImageUrl;

    public CastModel(String actorName, String actorImageUrl) {
        this.actorName = actorName;
        this.actorImageUrl = actorImageUrl;
    }

    public String getActorName() {
        return actorName;
    }

    public String getActorImageUrl() {
        return actorImageUrl;
    }
}
