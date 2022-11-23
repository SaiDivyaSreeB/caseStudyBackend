package com.cw.order.controller;

import com.cw.order.exceptionHandlers.APIRequestException;
import com.cw.order.models.orderDetails;
import com.cw.order.repository.orderRepository;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

//@CrossOrigin("http://localhost:4200")
//@CrossOrigin(origins="*")
@RestController
@RequestMapping("/orders")
public class orderController {
    Logger logger = LoggerFactory.getLogger(orderController.class);

    @Autowired
    private orderRepository or;
    @Autowired
    private RestTemplate restTemplate;

    //To get all orders
    @GetMapping("/findall")

    //  @ApiOperation(value = "gives you all orders",notes="no need to provide any input",response = List.class)
    public List<orderDetails> findallOrders() {

        logger.info("find all orders end point accessed");
        return or.findAll();
    }

    //to view the details of the order (by admin)
    @GetMapping("/findOne/{orderId}")
    public ResponseEntity<orderDetails> findById(@ApiParam(value = "provide id for contact you want to look", required = true) @PathVariable String orderId) {
        logger.info("find one by order id end point accessed");
        orderDetails order = or.findById(orderId).orElseThrow(() -> new APIRequestException("order Id not found"));
        return ResponseEntity.ok(order);
    }

    //to delete the orders when they view list of their orders
    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<Map<String, Boolean>> deleteOrderById(@PathVariable String orderId) {
        logger.info("delete order by id end point accessed");
        orderDetails order = or.findById(orderId).orElseThrow(() -> new APIRequestException("order with id " + orderId + "not found, deletion failed"));
        or.delete(order);
        Map<String, Boolean> response = new HashMap<>();
        response.put("order deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    //to book
    @PostMapping("/add")

    public String addOrder(@RequestBody orderDetails order) {
        logger.info("add order end point accessed");
        order.setStatus("Pending");
        order.setWasherName("NA");

        or.save(order);
        System.out.println(order.getOrderId());
        return order.getOrderId() ;

    }

    //to get orders that are not assigned (by admin, by washer)
    @GetMapping("/findUnassigned")
    public List<orderDetails> getUnassignedOrders() {
        logger.info("find unassigned orders end point accessed");
        return or.findAll().stream().filter(x -> x.getWasherName().contains("NA") && x.getStatus().contains("Pending")).collect(Collectors.toList());
    }

    // to update the status of order(car wash) (by washer, by customer)
    @GetMapping("/updateStatus/{orderId}")
    public ResponseEntity<orderDetails> updateStatus(@PathVariable String orderId /*,@RequestBody String status*/) {
        logger.info("update status end point accessed");
        orderDetails existingOrder = or.findById(orderId/*details.getOrderId()*/).orElseThrow(() -> new APIRequestException("Order with ID -> " +/*details.getOrderId()*/orderId + " not found, status update failed"));
        existingOrder.setStatus("Completed"/*status*/);
        orderDetails order = or.save(existingOrder);
        return ResponseEntity.ok(order);
    }

    //update the details of order (by customer)
    @PutMapping("/update/{orderId}")
    public ResponseEntity<orderDetails> updateOrder(@PathVariable String orderId, @RequestBody orderDetails orderDetails) {
        logger.info("update order end point accessed");
        orderDetails existingOrder = or.findById(orderId).orElseThrow(() -> new APIRequestException("Order with ID -> " + orderDetails.getOrderId() + " not found,update failed"));
        //OrderId won't be updated
        //WasherName can't be updated by user
        //Status can't be updated by the user
        existingOrder.setWasherName(orderDetails.getWasherName());
        existingOrder.setWashpack(orderDetails.getWashpack());
        existingOrder.setCars(orderDetails.getCars());
        existingOrder.setUserEmailId(orderDetails.getUserEmailId());
        existingOrder.setAreapincode(orderDetails.getAreapincode());
        existingOrder.setPhoneNo(orderDetails.getPhoneNo());
        existingOrder.setCars(orderDetails.getCars());
        existingOrder.setAddon(orderDetails.getAddon());
        orderDetails updatedOrder = or.save(existingOrder);
        return ResponseEntity.ok(updatedOrder);
    }
// to cancel the order
    @PutMapping("/cancelOrder")
    public String cancelOrder(@RequestBody orderDetails orderDetails) {
        logger.info("cancel order end point accessed");
        orderDetails od = or.findById(orderDetails.getOrderId()).orElseThrow(() -> new APIRequestException("Order with ID -> " + orderDetails.getOrderId() + " not found,update failed"));
        od.setStatus("Cancelled");
        or.save(od);
        return "The order with ID -> " + orderDetails.getOrderId() + " is cancelled successfully";
    }

//to get orders by email id
    @GetMapping("/findMyOrders/{useremailid}")
    public List<orderDetails> getMyOrders(@PathVariable String useremailid) {
        logger.info("find  orders by mail id end point accessed");
        return or.findAll().stream().filter(x -> x.getUserEmailId().contains(useremailid)).collect(Collectors.toList());
    }

// to assign washer to the order
    @PutMapping("/assignWasher/{orderId}")
    public /*orderDetails*/ResponseEntity<orderDetails>
    assignWasher(@PathVariable String orderId,@RequestBody String name) {
        logger.info("assign washer end point accessed");
        orderDetails existingOrder = or.findById(orderId).orElseThrow(() -> new APIRequestException("order with id " + orderId+ "notfound,status update failed"));
        if (/*doesOrderExists &&*/ existingOrder.getWasherName().contains("NA")) {
            existingOrder.setWasherName(name);
            existingOrder.setStatus("Pending");
            or.save(existingOrder);
            return ResponseEntity.ok(existingOrder);
        } else {
            throw new APIRequestException(" washer to that order with " + orderId + "assigned already");
        }

    }

//to get washer orders
    @GetMapping("/getWasherOrders/{name}")
    public List<orderDetails> getWasherOrders(@PathVariable String name) {
        List<orderDetails> orders = or.findAll().stream().filter(x -> x.getWasherName().equals(name)).collect(Collectors.toList());
        return orders;
    }
//to reject the order
    @GetMapping("/rejectOrder/{orderId}")
    public ResponseEntity<orderDetails> rejectOrder(@PathVariable String orderId /*,@RequestBody String status*/) {
        logger.info("reject order end point accessed");
        orderDetails existingOrder = or.findById(orderId/*details.getOrderId()*/).orElseThrow(() -> new APIRequestException("Order with ID -> " +/*details.getOrderId()*/orderId + " not found, status update failed"));
        existingOrder.setStatus("Pending"/*status*/);
        existingOrder.setWasherName("NA");
        orderDetails order = or.save(existingOrder);
        System.out.println("rejected");
        Optional<orderDetails> od = or.findById(orderId);
        System.out.println(od);
        return ResponseEntity.ok(order);
    }

}
