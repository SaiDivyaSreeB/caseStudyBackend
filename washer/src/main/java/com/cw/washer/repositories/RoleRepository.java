package com.cw.washer.repositories;

import com.cw.washer.models.Roles;
import org.springframework.data.mongodb.repository.MongoRepository;



public interface RoleRepository extends MongoRepository<Roles,String> {
    //to find Role by its name
    Roles findByRole(String role);

}
