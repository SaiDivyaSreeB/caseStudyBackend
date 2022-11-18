package com.cw.springsecurityjwt.controllers;

import com.cw.springsecurityjwt.configs.JwtUtil;
import com.cw.springsecurityjwt.models.AuthenticationRequest;
import com.cw.springsecurityjwt.models.Roles;
import com.cw.springsecurityjwt.models.Users;
import com.cw.springsecurityjwt.repositories.UserRepository;
import com.cw.springsecurityjwt.services.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authAdmin")
public class AuthenticateController {
    Logger logger = LoggerFactory.getLogger(AuthenticateController.class);
    @Autowired
  AuthenticationManager authenticationManager;
   @Autowired
  JwtUtil jwtUtil;
   @Autowired
   UserRepository ur;
   @Autowired
   private CustomUserDetailsService userService;
    //to login
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        System.out.println("before logger");
        logger.info("login end point accessed");
     System.out.println("after logger");
        //logging username to console
        String username = authenticationRequest.getEmail();
        System.out.println(username);
     try {
    System.out.println("first auth manager");
      authenticationManager.authenticate((new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())));
         System.out.println("second auth manager");
     }
     //if the authentication fails(to handle that exception)
     catch (BadCredentialsException e) {
      throw new Exception("Invalid username or password");
     }
     //logging
       // System.out.println(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,authenticationRequest.getPassword())));
     System.out.println("authentication controller");
     String token = jwtUtil.createToken(username,this.ur.findByEmail(username).getRoles());
     System.out.println("token"+token);
     Users ExistingUser = userService.findUserByEmail(username);

     return new ResponseEntity<>( userService.updateTokenById(ExistingUser,token),HttpStatus.OK);
       // return ResponseEntity.ok(new AuthenticationResponse(token));
    }
    //to register as new user
    @PostMapping("/register")

    public ResponseEntity<?> register(@RequestBody Users user){//
        logger.info("register end point accessed");
       Users userExists = userService.findUserByEmail(user.getEmail());
       if(userExists!=null){
            return new ResponseEntity<>("User exists already, try with a different email address",HttpStatus.OK);
        }
       return new ResponseEntity<>(userService.saveUser(user),HttpStatus.CREATED);
    }

}
