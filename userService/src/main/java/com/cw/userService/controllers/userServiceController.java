package com.cw.userService.controllers;

//import com.cw.userService.models.orderDetails;
import com.cw.userService.WrapperModel.OrderReceipt;
import com.cw.userService.models.Users;
import com.cw.userService.models.orderDetails;
import com.cw.userService.models.rating;
import com.cw.userService.models.washPack;
import com.cw.userService.repositories.ratingRepository;
import com.cw.userService.services.ratingService;
//import com.cw.userService.services.serviceUser;
import com.cw.userService.services.serviceUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
public class userServiceController {
    Logger logger = LoggerFactory.getLogger(userServiceController.class);
    @Autowired
    ratingRepository rr;
    @Autowired
    ratingService rs;
   @Autowired
   serviceUser us;
   //to give rating to the washer
    @PostMapping("/addRating")
    public rating addRating(@RequestBody rating rate)
    {
        logger.info("addRating end point accessed");
        return rs.addRating(rate);
    }
    //to get all the ratings in the collection
    @GetMapping("/getAllRatings")
    public List<rating> getAllRatings()
    {
        logger.info("get all ratings end point accessed");
        return rs.getAllRatings();
    }
    //to get the ratings of specific washer
    @GetMapping("/washerSpecificRating/{washerName}")
    public List<rating> washerSpecific(@PathVariable String washerName)
    {
        logger.info("washer specific ratings end point accessed");
        return rs.washerSpecific(washerName);
    }
    //to place an order
    @PostMapping("/addOrder")
    public String addOrder(@RequestBody orderDetails order){
        logger.info("add order accessed");
        return us.addOrder(order);
    }
    //to update the details of order
    @PutMapping("/updateOrder/{orderId}")
    public orderDetails updateOrder(@PathVariable String orderId,@RequestBody orderDetails order)
    {
        logger.info("update order accessed");
        return us.updateOrder(orderId,order);
    }
    //to cancel the order
    @PutMapping("/cancelOrder")
    public String cancelOrder(@RequestBody orderDetails order){
        logger.info("cancel order accessed");
        return us.cancelOrder(order);
    }
    //to get all washpacks
   @GetMapping("/seeWp")
    public List<washPack> getAllWp()
    {
        logger.info("see washpacks end point accessed");
        return us.getAllWp();
    }
    @GetMapping("/getReceipt/{id}")
    public OrderReceipt getReceipt(@PathVariable String id){
        logger.info("get receipt end point accessed");
        return us.getReciept(id);
    }
    //to get all orders of user
    @GetMapping("/pendingOrders/{email}")
    public List<orderDetails> getPendingOrders(@PathVariable String email){
        logger.info("pending orders end point accessed");
        return us.getPendingOrders(email);
    }
    //to update the profile picture in ratings
    @PostMapping("/updatePic")
    public List<rating> updatePic(@RequestBody Users user){
        return rs.updateProfile(user);
    }

}
