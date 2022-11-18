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

//@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/washers")
public class washerController {
    Logger logger = LoggerFactory.getLogger(washerController.class);
    @Autowired
    washerService wr;

    //Only the methods that consume rest template are below this comment
    //To see the Unassigned orders
    @GetMapping("/findUnassigned")
    public List<orderDetails> getUnassignedOrders(){
        logger.info("getUnassiedOrders end point is accessed");
        return wr.getUnassignedOrders();
    }
    //The status of the order can be either pending or completed
    /*@PutMapping("/updateStatus")
    public orderDetails updateStatusoftheOrder(@RequestBody orderDetails orderDetails){
        return wr.updateStatus(orderDetails);
    }*/
    //To assign a washer to the order by washer
//    @PutMapping("/assign")
//    public orderDetails assignSelf(@RequestBody orderDetails orderDetails){
//        return wr.assignSelf(orderDetails);
//    }
    @PutMapping("/assign/{orderId}")
    public orderDetails assignSelf(@PathVariable String orderId,@RequestBody String name){
        logger.info("assignSelf end point is accessed");
      return wr.assignSelf(orderId,name);
    }
   @GetMapping("/updateStatus/{orderId}")
    public orderDetails updateStatusoftheOrder(@PathVariable String orderId){
       logger.info("updateStatusoftheOrder end point is accessed");
        return wr.updateStatus(orderId);
    }
    @GetMapping("/seeWP")
    public List<washPack> getAllWP(){
        logger.info("getAllWP end point is accessed");
        return wr.getAllWP();
    }
    @GetMapping("/getOrders/{name}")
    public List<orderDetails> getMyOrders(@PathVariable String name){
        logger.info("getMyOrders end point is accessed");
        return wr.getOrders(name);
    }
    /*@PostMapping("/order/{id}")
    public ResponseEntity<?> AcceptOrRejectOrder(@PathVariable("id" )Long id){
        return  null;
    }*/
    @GetMapping("/accept/{orderId}")
    public orderDetails acceptOrder(@PathVariable String orderId){
        logger.info("acceptOrder end point is accessed");
        return wr.acceptOrders(orderId);
    }
    @GetMapping("/reject/{orderId}")
    public orderDetails rejectOrder(@PathVariable String orderId){
        logger.info("rejectOrder end point is accessed");
        return wr.rejectOrders(orderId);
    }
   /* @GetMapping("/unassignedWasherOrders/{name}")
    public List<orderDetails> unassigned(@PathVariable String name)
    {
        return wr.getOrders(name).stream().filter(x->x.getStatus().contains("NA")).collect(Collectors.toList());
    }*/
    @GetMapping("/pendingWasherOrders/{name}")
    public List<orderDetails> pending(@PathVariable String name)
    {
        logger.info("pendingOrders end point is accessed");
        return wr.getOrders(name).stream().filter(x->x.getStatus().contains("Pending")).collect(Collectors.toList());
    }
    @PutMapping("/updateProfile/{id}")
    public Users update(@PathVariable String id, @RequestBody Users user){
        logger.info("updateProfile end point is accessed");
        return wr.updateProfile(id,user);
    }

}
