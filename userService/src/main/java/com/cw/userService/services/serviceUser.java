package com.cw.userService.services;

import com.cw.userService.WrapperModel.OrderReceipt;
import com.cw.userService.exceptionHandler.APIRequestException;
import com.cw.userService.models.Users;
import com.cw.userService.models.orderDetails;
import com.cw.userService.models.washPack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class serviceUser {
    @Autowired
    private RestTemplate restTemplate;
    String url = "http://ORDER-SERVICE/orders";
    String url1="http://ADMIN-SERVICE/admins";
    //String url4="http://SECURITY-JWT/manage";
    public orderDetails updateOrder(String orderId,orderDetails order) {
        HttpHeaders header = new HttpHeaders();
        header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<orderDetails> entity = new HttpEntity<>(order, header);
        return restTemplate.exchange(url + "/update/"+orderId, HttpMethod.PUT, entity, orderDetails.class).getBody();
    }
//    public orderDetails addOrder(orderDetails order){
//        HttpEntity<orderDetails> entity = new HttpEntity<>(order);
//        return restTemplate.postForObject(url + "/add",order,orderDetails.class);
//    }
public String addOrder(orderDetails order){
    HttpEntity<orderDetails> entity = new HttpEntity<>(order);
    return restTemplate.postForObject(url + "/add",order,String.class);
}
    public String cancelOrder(orderDetails order){
        HttpHeaders header = new HttpHeaders();
        header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<orderDetails> entity = new HttpEntity<>(order,header);
        return restTemplate.exchange(url+"/cancelOrder", HttpMethod.PUT,entity,String.class).getBody();
    }
   public List<washPack> getAllWp(){
        washPack[] packs = restTemplate.getForObject(url1+"/findallWp",washPack[].class);
        return (Arrays.asList(packs));
    }
    public OrderReceipt getReciept(String id) {
        orderDetails od = restTemplate.getForObject(url1 + "/findOne/"+id, orderDetails.class);
        washPack wp = restTemplate.getForObject(url1 + "/wpByName/"+od.getWashpack(), washPack.class);
        if (od.getStatus().contains("Completed")) {
            return new OrderReceipt(id, od.getUserEmailId(), od.getWasherName(), wp.getName(), wp.getDescription(), wp.getCost());
        } else {
            throw new APIRequestException("Your order with id " + id + " is still pending");
        }
    }
    public List<orderDetails> getPendingOrders(String mail){
        orderDetails[] orders = restTemplate.getForObject(url+"/findMyOrders/"+mail,orderDetails[].class);
        List<orderDetails> pendingOrders= Arrays.asList(orders).stream().filter(x->x.getStatus().contains("Pending")).collect(Collectors.toList());
        return pendingOrders;
    }

}
