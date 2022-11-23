package com.cw.washer.controller;

import com.cw.washer.models.Users;
import com.cw.washer.models.orderDetails;
import com.cw.washer.models.washPack;
import com.cw.washer.services.washerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/washers")
public class washerController {
    Logger logger = LoggerFactory.getLogger(washerController.class);
    @Autowired
    washerService wr;


    //To see the Unassigned orders
    @GetMapping("/findUnassigned")
    public List<orderDetails> getUnassignedOrders(){
        logger.info("getUnassiedOrders end point is accessed");
        return wr.getUnassignedOrders();
    }
    //to assign washer to the order
    @PutMapping("/assign/{orderId}")
    public orderDetails assignSelf(@PathVariable String orderId,@RequestBody String name){
        logger.info("assignSelf end point is accessed");
      return wr.assignSelf(orderId,name);
    }
    //to update the status of the order as completed
   @GetMapping("/updateStatus/{orderId}")
    public orderDetails updateStatusoftheOrder(@PathVariable String orderId){
       logger.info("updateStatusoftheOrder end point is accessed");
        return wr.updateStatus(orderId);
    }
    //to see all washpacks
    @GetMapping("/seeWP")
    public List<washPack> getAllWP(){
        logger.info("getAllWP end point is accessed");
        return wr.getAllWP();
    }
    //to get orders by name
    @GetMapping("/getOrders/{name}")
    public List<orderDetails> getMyOrders(@PathVariable String name){
        logger.info("getMyOrders end point is accessed");
        return wr.getOrders(name);
    }
    // to reject the assigned order by washer
    @GetMapping("/reject/{orderId}")
    public orderDetails rejectOrder(@PathVariable String orderId){
        logger.info("rejectOrder end point is accessed");
        return wr.rejectOrders(orderId);
    }
    //to get the pending orders of washer
    @GetMapping("/pendingWasherOrders/{name}")
    public List<orderDetails> pending(@PathVariable String name)
    {
        logger.info("pendingOrders end point is accessed");
        return wr.getOrders(name).stream().filter(x->x.getStatus().contains("Pending")).collect(Collectors.toList());
    }
    //to update the profile photo
    @PutMapping("/updateProfile/{id}")
    public Users update(@PathVariable String id, @RequestBody Users user){
        logger.info("updateProfile end point is accessed");
        return wr.updateProfile(id,user);
    }
    //to get the profile photo by name
    @GetMapping("/getImage/{washerName}")
    public String getImageByName(@PathVariable String washerName){
        return wr.getImageByname(washerName);
    }

}
