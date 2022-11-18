package com.cw.springsecurityjwt.services;

//import com.cw.admin.models.Ratings;
//import com.cw.admin.models.Users;
//import com.cw.admin.models.orderDetails;
//import com.cw.admin.wrappermodel.WasherRatings;
import com.cw.springsecurityjwt.models.Ratings;
import com.cw.springsecurityjwt.models.Users;
import com.cw.springsecurityjwt.models.orderDetails;
import com.cw.springsecurityjwt.wrappermodels.WasherRatings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class adminService {
    @Autowired
    private RestTemplate restTemplate;
    String url = "http://ORDER-SERVICE/orders";
//    String url4="http://SECURITY-JWT/manage";
    String url2="http://USER-SERVICE/users";
    public orderDetails assignWasher(String orderId, String washerName){
        HttpHeaders header = new HttpHeaders();
        header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(washerName,header);
        return restTemplate.exchange(url+"/assignWasher/"+orderId, HttpMethod.PUT,entity,orderDetails.class).getBody();
    }
    public List<orderDetails> getUnassignedOrders(){
        orderDetails[] unassignedList = restTemplate.getForObject(url+"/findUnassigned",orderDetails[].class);
        return Arrays.asList(unassignedList);
    }
//    public Users getOneWasher(String name){
//        return restTemplate.getForObject(url4+"/washer/"+name,Users.class);
//    }
//    public WasherRatings washerSpecificRatings(String washerName){
//        Users washer = restTemplate.getForObject(url4+"/washer/"+washerName,Users.class);
//        Ratings[] ratingsList = restTemplate.getForObject(url2+"/washerSpecificRating/"+washerName,Ratings[].class);
//        return new WasherRatings(washer.getId(),washer.getFullname(),washer.getEmail(),Arrays.asList(ratingsList));
//    }
//
//    public Users updateProfile(String id, Users user) {
//        HttpHeaders header = new HttpHeaders();
//        header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        HttpEntity<Users> entity = new HttpEntity<>(user,header);
//
//        return restTemplate.exchange(url4+"/updateprofile/"+id,HttpMethod.PUT, entity, Users.class).getBody();
//    }


}
