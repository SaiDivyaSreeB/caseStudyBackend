package com.cw.washer.services;

import com.cw.washer.models.Users;
import com.cw.washer.models.orderDetails;
import com.cw.washer.models.washPack;
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
    private RestTemplate restTemplate;
    String url1="http://ADMIN-SERVICE/manage";
    //Url to access the methods of Order Service
    String url="http://ORDER-SERVICE/orders";
    //String url4="http://SECURITY-JWT/manage";

    // Only the methods that use rest template are below this comment
    //To see all the WashPacks
    public List<washPack> getAllWP(){
        washPack[] wp = restTemplate.getForObject(url1+"/findallWp",washPack[].class);
        return (Arrays.asList(wp));
    }
    //To see the Unassigned orders
   public List<orderDetails> getUnassignedOrders(){
        orderDetails[] unassignedList = restTemplate.getForObject(url+"/findUnassigned",orderDetails[].class);
        return Arrays.asList(unassignedList);
    }
    //To update the status of the order by Washer
    public orderDetails updateStatus(orderDetails orderDetails){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<orderDetails> updatedOrder = new HttpEntity<>(orderDetails,headers);
        orderDetails od = restTemplate.exchange(url+"/updateStatus", HttpMethod.PUT,updatedOrder,orderDetails.class).getBody();
        return od;
    }
    //To assign a washer to the order by washer
//    public orderDetails assignSelf(orderDetails orderDetails){
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        HttpEntity<orderDetails> assignedWasher = new HttpEntity<>(orderDetails,headers);
//        orderDetails od = restTemplate.exchange(url+"/assignWasher", HttpMethod.PUT,assignedWasher,orderDetails.class).getBody();
//        return od;
//    }
    public orderDetails assignSelf(String orderId, String name){
        HttpHeaders headers = new HttpHeaders();
       headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(name,headers);
       orderDetails updatedorder = restTemplate.exchange(url+"/assignWasher/"+orderId, HttpMethod.PUT,entity,orderDetails.class).getBody();
       return updatedorder;
   }

   public orderDetails updateStatus(String orderId){
        /*HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> updatedOrder = new HttpEntity<>(orderId,headers);*/
        orderDetails od = restTemplate.getForObject(url+"/updateStatus/"+orderId,orderDetails.class);
        return od;
    }
    public List<orderDetails> getOrders(String name){
        orderDetails[] myOrders = restTemplate.getForObject(url+"/getWasherOrders/"+name,orderDetails[].class);
        return Arrays.asList(myOrders);
    }
    public orderDetails acceptOrders(String orderId)
    {
        /*HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> acceptOrder = new HttpEntity<>(orderId,headers);*/
        //orderDetails od = restTemplate.exchange(url+"/acceptOrder", HttpMethod.PUT,acceptOrder,orderDetails.class).getBody();
        orderDetails od = restTemplate.getForObject(url+"/acceptOrder/"+orderId,orderDetails.class);
        return od;
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
}
