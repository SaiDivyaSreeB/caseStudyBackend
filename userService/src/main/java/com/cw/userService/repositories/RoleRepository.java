package com.cw.userService.repositories;

import com.cw.userService.models.Roles;

import org.springframework.data.mongodb.repository.MongoRepository;



public interface RoleRepository extends MongoRepository<Roles,String> {
    //to find Role by its name
    Roles findByRole(String role);

}
