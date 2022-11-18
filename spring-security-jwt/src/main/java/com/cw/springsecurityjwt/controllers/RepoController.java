package com.cw.springsecurityjwt.controllers;

import com.cw.springsecurityjwt.models.Users;
import com.cw.springsecurityjwt.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/manage")
public class RepoController {
    @Autowired
    private AuthService as;
//    @GetMapping("/all")
//    public List<Users> getAllUsers(){
//        return as.getAllUser();
//    }
    // d
//    @GetMapping("/UserByName/{name}")
//    public Users getSpecificUser(@PathVariable String name){
//        return as.getSpecificUser(name);
//    }
//    @GetMapping("/IdByMail/{mail}")
//    public String getSpecificUser(@PathVariable String mail){
//        Users user = as.getUserByMail(mail);
//        String id = user.getId();
//         return id;
//    }
//    @GetMapping("/deleteUser/{id}")
//        public ResponseEntity<Map<String,Boolean>> deleteUser(@PathVariable String id){
//        return as.deleteUser(id);
//    }
    @GetMapping("/users/{role}")
    public List<Users> getUserByRole(@PathVariable String role){
        return as.findListByRole(role);
    }
//    @GetMapping("/washer/{name}")
//    public Users getWasher(@PathVariable String name){
//        return as.getWasher(name);
//    }
    //to update the profile
    @PutMapping("/updateprofile/{id}")
    public Users updateProfile(@PathVariable String id, @RequestBody Users user)
    {
        return as.updateProfile(id,user);
    }
//    @GetMapping("/viewprofile/{id}")
//    public Users viewProfile(@PathVariable String id){
//        return as.viewProfile(id);
//    }
}
