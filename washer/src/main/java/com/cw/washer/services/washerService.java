package com.cw.washer.services;

import com.cw.washer.models.Users;
import com.cw.washer.models.orderDetails;
import com.cw.washer.models.washPack;
import com.cw.washer.repositories.UserRepository;
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
public class washerService {
    @Autowired
    private UserRepository ur;
    @Autowired
    private RestTemplate restTemplate;
    String url1="http://ADMIN-SERVICE/manage";
    //Url to access the methods of Order Service
    String url="http://ORDER-SERVICE/orders";


    // Only the methods that use rest template are below this comment

    public List<washPack> getAllWP(){
        washPack[] wp = restTemplate.getForObject(url1+"/findallWp",washPack[].class);
        return (Arrays.asList(wp));
    }

   public List<orderDetails> getUnassignedOrders(){
        orderDetails[] unassignedList = restTemplate.getForObject(url+"/findUnassigned",orderDetails[].class);
        return Arrays.asList(unassignedList);
    }

    public orderDetails updateStatus(orderDetails orderDetails){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<orderDetails> updatedOrder = new HttpEntity<>(orderDetails,headers);
        orderDetails od = restTemplate.exchange(url+"/updateStatus", HttpMethod.PUT,updatedOrder,orderDetails.class).getBody();
        return od;
    }
    public orderDetails assignSelf(String orderId, String name){
        HttpHeaders headers = new HttpHeaders();
       headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(name,headers);
       orderDetails updatedorder = restTemplate.exchange(url+"/assignWasher/"+orderId, HttpMethod.PUT,entity,orderDetails.class).getBody();
       return updatedorder;
   }

   public orderDetails updateStatus(String orderId){
        orderDetails od = restTemplate.getForObject(url+"/updateStatus/"+orderId,orderDetails.class);
        return od;
    }
    public List<orderDetails> getOrders(String name){
        orderDetails[] myOrders = restTemplate.getForObject(url+"/getWasherOrders/"+name,orderDetails[].class);
        return Arrays.asList(myOrders);
    }

    public orderDetails rejectOrders(String orderId)
    {

        orderDetails od = restTemplate.getForObject(url+"/rejectOrder/"+orderId,orderDetails.class);
        return od;
    }
    public Users updateProfile(String id, Users user) {
        HttpHeaders header = new HttpHeaders();
        header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Users> entity = new HttpEntity<>(user,header);
        return restTemplate.exchange(url1+"/updateprofile/"+id,HttpMethod.PUT, entity, Users.class).getBody();
    }
    public String getImageByname(String washerName) {
        String image = ur.findAll().stream().filter(x -> x.getFullname().equals(washerName)).findFirst().get().getImage();
        return image;
    }

}
