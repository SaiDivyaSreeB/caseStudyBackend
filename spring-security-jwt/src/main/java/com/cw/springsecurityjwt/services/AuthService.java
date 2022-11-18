package com.cw.springsecurityjwt.services;

import com.cw.springsecurityjwt.exceptionhandler.APIRequestException;
import com.cw.springsecurityjwt.models.Roles;
import com.cw.springsecurityjwt.models.Users;
import com.cw.springsecurityjwt.repositories.RoleRepository;
import com.cw.springsecurityjwt.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthService {
    @Autowired
    private UserRepository ur;
    @Autowired
    private RoleRepository roleRepository;
    //To get all users from DB
//    public List<Users> getAllUser(){
//        return ur.findAll();
//    }
//    //find user by name
//    public Users getSpecificUser(String name){
//        return ur.findAll().stream().filter(x->x.getFullname().contains(name)).findFirst().get();
//    }
//    public Users getUserByMail(String mail){
//        return ur.findAll().stream().filter(x->x.getEmail().contains(mail)).findFirst().get();
//    }
//    //to delete a user
//    public ResponseEntity<Map<String,Boolean>> deleteUser(String id){
//        Users user = ur.findById(id).orElseThrow(()->new APIRequestException("User with Id -> "+id+" not found, deletion failed"));
//        ur.delete(user);
//        Map<String, Boolean> response = new HashMap<>();
//        response.put("User Deleted",Boolean.TRUE);
//        return ResponseEntity.ok(response);
//    }
//    //to find list of users with role
    public List<Users> findListByRole(String role){
        Roles r = roleRepository.findByRole(role);
        Set<Roles> roles = new HashSet<>();
        roles.add(r);
        return ur.findByRolesIn(roles);
    }
//    public Users getWasher(String name){
//        Roles r = roleRepository.findByRole("WASHER");
//        Set<Roles> roles = new HashSet<>();
//        roles.add(r);
//        return ur.findByRolesIn(roles).stream().filter(x->x.getFullname().contains(name)).findFirst().orElseThrow(()->new APIRequestException("Washer with name "+name+" not found"));
//    }
    public Users updateProfile(String id,Users user){
        Users existingUser=ur.findById(id).orElseThrow(() -> new APIRequestException("User with ID -> "+user.getId()+" not found,update failed"));

       //existingUser.setEmail(user.getEmail());
        existingUser.setImage(user.getImage());
        existingUser.setFullname(user.getFullname());


        return ur.save(existingUser);
    }


//    public Users viewProfile(String id) {
//        return ur.findById(id).get();
//    }
}
