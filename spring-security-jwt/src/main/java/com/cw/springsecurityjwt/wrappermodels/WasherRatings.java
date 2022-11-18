package com.cw.springsecurityjwt.wrappermodels;

//import com.cw.admin.models.Ratings;
import com.cw.springsecurityjwt.models.Ratings;

import java.util.List;

public class WasherRatings {
    private String WasherId;
    private String WasherName;
    private String WasherEmailId;
    private List<Ratings> ratings;
    public WasherRatings(){
        }

    public WasherRatings(String washerId, String washerName, String washerEmailId, List<Ratings> ratings) {
        this.WasherId = washerId;
        this.WasherName = washerName;
        this.WasherEmailId = washerEmailId;
        this.ratings = ratings;
    }

    public String getWasherId() {
        return WasherId;
    }

    public void setWasherId(String washerId) {
        this. WasherId = washerId;
    }

    public String getWasherName() {
        return WasherName;
    }

    public void setWasherName(String washerName) {
        this.WasherName = washerName;
    }

    public String getWasherEmailId() {
        return WasherEmailId;
    }

    public void setWasherEmailId(String washerEmailId) {
        this.WasherEmailId = washerEmailId;
    }

    public List<Ratings> getRatings() {
        return ratings;
    }

    public void setRatings(List<Ratings> ratings) {
        this.ratings = ratings;
    }

    @Override
    public String toString() {
        return "WasherRatings{" +
                "WasherId='" + WasherId + '\'' +
                ", WasherName='" + WasherName + '\'' +
                ", WasherEmailId='" + WasherEmailId + '\'' +
                ", ratings=" + ratings +
                '}';
    }
}
