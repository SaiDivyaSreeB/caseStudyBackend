package com.cw.springsecurityjwt.controllers;
import com.cw.springsecurityjwt.models.orderDetails;
import com.cw.springsecurityjwt.models.washPack;
import com.cw.springsecurityjwt.services.adminService;
import com.cw.springsecurityjwt.services.washPackService;
import com.cw.springsecurityjwt.wrappermodels.WasherRatings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admins")
public class adminController {
    Logger logger = LoggerFactory.getLogger(adminController.class);
    @Autowired
    washPackService wps;
    @Autowired
    adminService as;
    //to get all washpacks
    @GetMapping("/findallWp")
    public List<washPack> findallWp(){

        logger.info("find all washpacks end point accessed");
        return wps.findAllWp();
    }
    //get washpack by Id
    @GetMapping("/findoneWp/{id}")
    public ResponseEntity<washPack> findWpById(@PathVariable String id){
        logger.info("find one washpack by id end point accessed");
        return wps.findById(id);
    }
//    @GetMapping("/wpByName/{name}")
//    public washPack findWpByName(@PathVariable String name){
//        logger.info("find washpack by name end point accessed");
//        return wps.findByName(name);
//    }
    //get washpack cost
    @GetMapping("/costByName/{name}" )
    public Integer costByName(@PathVariable String name){
        logger.info("find cost by washpack name end point accessed");
        washPack wp = wps.findByName(name);
        return(wp.getCost());
    }
    // to add new washpack
    @PostMapping("/addWp")
    public washPack addWp(@RequestBody washPack washpack)
    {
        logger.info("add washpack end point accessed");
        return wps.addWp(washpack);
    }
    // to delete a washpack
    @DeleteMapping("/deleteWp/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteById(@PathVariable String id){
        logger.info("delete washpack by id end point accessed");
        return wps.deleteWp(id);
    }
    //to update washpack
    @PutMapping("/updateWp/{id}")
    public ResponseEntity<washPack> updateById(@PathVariable String id, @RequestBody washPack pack){
        logger.info("update washpack by id end point accessed");
        return wps.updateWp(id,pack);
    }
    //to assign washer to the order
    @PutMapping("/assignWasher/{orderId}")
    public orderDetails assignWasher(@PathVariable String orderId,@RequestBody String washerName){
        logger.info("assign washer to order end point accessed");
        return as.assignWasher(orderId,washerName);
    }
    //to find unassigned orders
    @GetMapping("/findUnassigned")
    public List<orderDetails> getUnassignedOrders(){
        logger.info("find unassigned end point accessed");
        return as.getUnassignedOrders();
    }
//    @GetMapping("/oneWasher/{name}")
//        public Users getOneWasher(@PathVariable String name) {
//        return as.getOneWasher(name);
//    }
//    @GetMapping("/washerRating/{name}")
//    public WasherRatings washerSpecificRatings(@PathVariable String name){
//        return as.washerSpecificRatings(name);
//    }
//    @PutMapping("/updateProfile/{id}")
//    public Users update(@PathVariable String id, @RequestBody Users user){
//        return as.updateProfile(id,user);
//    }
    //@GetMapping("/viewProfile/{id}")
}
