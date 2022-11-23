package com.cw.userService.services;

import com.cw.userService.models.Users;
import com.cw.userService.models.rating;
import com.cw.userService.repositories.ratingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;



@Service
public class ratingService {
    @Autowired
    ratingRepository rr;
   @Autowired
    private RestTemplate restTemplate;
   String url ="http://WASHER-SERVICE/washers/getImage/";
    public rating addRating(rating rate){
        String image = restTemplate.getForObject(url+rate.getWasherName(), String.class);
        rate.setImage(image);
        rr.save(rate);
        return rate;
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