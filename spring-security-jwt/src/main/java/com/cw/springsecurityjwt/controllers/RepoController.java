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

    @GetMapping("/users/{role}")
    public List<Users> getUserByRole(@PathVariable String role){
        return as.findListByRole(role);
    }

    //to update the profile
    @PutMapping("/updateprofile/{id}")
    public Users updateProfile(@PathVariable String id, @RequestBody Users user)
    {
        return as.updateProfile(id,user);
    }

}
