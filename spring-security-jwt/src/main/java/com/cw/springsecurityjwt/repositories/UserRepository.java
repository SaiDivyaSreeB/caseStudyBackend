package com.cw.springsecurityjwt.repositories;

import com.cw.springsecurityjwt.models.Roles;
import com.cw.springsecurityjwt.models.Users;
import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.List;
import java.util.Set;

public interface UserRepository extends MongoRepository<Users, String> {
    Users findByEmail(String email);


    List<Users> findByRolesIn(Set<Roles> roles);
}
