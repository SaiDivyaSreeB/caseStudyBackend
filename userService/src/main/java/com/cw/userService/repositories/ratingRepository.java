package com.cw.userService.repositories;

import com.cw.userService.models.rating;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ratingRepository extends MongoRepository<rating, Integer> {
}