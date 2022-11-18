package com.cw.userService.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="ratings")
public class rating {
    @Id
    String id;
    String washerName;
    String comments;
    int rating;
//    @DBRef
    String image;

    public rating(String id, String washerName, String comments, int rating, String image) {
        this.id = id;
        this.washerName = washerName;
        this.comments = comments;
        this.rating = rating;
        this.image = image;
    }

    public rating(){

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    /* @Override
    public String toString() {
        return "Rating{" +
                "id=" + id +
                ", washerName='" + washerName + '\'' +
                ", comments='" + comments + '\'' +
                ", rating=" + rating +
                '}';
    }*/
}