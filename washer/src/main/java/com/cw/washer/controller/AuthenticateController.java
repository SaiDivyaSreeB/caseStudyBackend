package com.cw.washer.controller;

import com.cw.washer.configs.JwtUtil;
import com.cw.washer.models.AuthenticationRequest;
import com.cw.washer.models.Users;
import com.cw.washer.repositories.UserRepository;
import com.cw.washer.services.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/authWasher")
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
    //logging
        logger.info("login endpoint accessed ");

        String username = authenticationRequest.getEmail();
        System.out.println(username);
     try {

      authenticationManager.authenticate((new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())));

     }
     //if the authentication fails(to handle that exception)
     catch (BadCredentialsException e) {
   // throw new Exception("Invalid username or password");
         return new ResponseEntity<>("Invalid username or password",HttpStatus.FORBIDDEN);
     }
     //logging
        String token = jwtUtil.createToken(username,this.ur.findByEmail(username).getRoles());
        Users ExistingUser = userService.findUserByEmail(username);
        return new ResponseEntity<>(userService.updateTokenById(ExistingUser,token), HttpStatus.OK);

    }
    // for user registration
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Users user){
        //logging
        logger.info("register endpoint accessed ");
       Users userExists = userService.findUserByEmail(user.getEmail());

        if(userExists!=null){
            System.out.println("oops");
            return new ResponseEntity<>("oops",HttpStatus.OK);
        }

        return new ResponseEntity<>(userService.saveUser(user),HttpStatus.CREATED);
    }

}
