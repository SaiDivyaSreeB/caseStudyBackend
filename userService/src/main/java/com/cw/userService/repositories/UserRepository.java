package com.cw.userService.repositories;

import com.cw.userService.models.Roles;
import com.cw.userService.models.Users;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Set;

public interface UserRepository extends MongoRepository<Users, String> {
    Users findByEmail(String email);
}
