package com.cw.springsecurityjwt.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="ratings")
public class Ratings {
    @Id
    String id;
    String washerName;
    String comments;
    int rating;
    public Ratings(){

    }

    public Ratings(String id, String washerName, String comments, int rating) {
        this.id = id;
        this.washerName = washerName;
        this.comments = comments;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWasherName() {
        return washerName;
    }

    public void setWasherName(String washerName) {
        this.washerName = washerName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
       this.rating = rating;
    }
}
