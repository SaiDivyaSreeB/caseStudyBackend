package com.cw.userService.services;

import com.cw.userService.models.Users;
import com.cw.userService.models.rating;
import com.cw.userService.repositories.ratingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;



@Service
public class ratingService {
    @Autowired
    ratingRepository rr;
    public rating addRating(rating rate){
        return rr.save(rate);
    }
    public List<rating> getAllRatings(){

        return rr.findAll();
    }
    public List<rating> updateProfile(Users user){
        String name = user.getFullname();
        List<rating> ratings  = rr.findAll().stream().filter(x->x.getWasherName().contains(name)).collect(Collectors.toList());
        System.out.println(user.getImage());
        for(rating r:ratings) {
            r.setImage(user.getImage());
            rr.save(r);
            System.out.println(r.getImage());
        }
        return rr.findAll().stream().filter(x->x.getWasherName().contains(name)).collect(Collectors.toList());
    }

    public List<rating> washerSpecific(String washerName){
        return rr.findAll().stream().filter(x->x.getWasherName().contains(washerName)).collect(Collectors.toList());
    }
    public String deleteRating(int id) {
        rr.deleteById(id);
        return "Rating with ID -> " + id + " deleted successfully";
    }
}