package com.cw.order.repository;

import com.cw.order.models.orderDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface orderRepository extends MongoRepository<orderDetails, String> {


    //orderDetails deleteByName(String name);
//    orderDetails findByWasher(String name);
}
