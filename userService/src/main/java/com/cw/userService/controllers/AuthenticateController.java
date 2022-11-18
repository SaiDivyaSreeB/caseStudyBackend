package com.cw.userService.controllers;

import com.cw.userService.configs.JwtUtil;
import com.cw.userService.models.AuthenticationRequest;
import com.cw.userService.models.Users;
import com.cw.userService.repositories.UserRepository;
import com.cw.userService.services.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authUser")
public class AuthenticateController {
    Logger logger = LoggerFactory.getLogger(AuthenticateController.class);
    @RequestMapping("/hello")
    public String hello(){
        return "hello world";
    }

    @Autowired
    AuthenticationManager authenticationManager;
   @Autowired
   /*private*/ JwtUtil jwtUtil;
   @Autowired
   UserRepository ur;
   @Autowired
   private CustomUserDetailsService userService;
    //authentication end point
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
     //logging username to console
        logger.info("login end point accessed");
        String username = authenticationRequest.getEmail();
        System.out.println(username);
     try {

      authenticationManager.authenticate((new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())));

     }
     //if the authentication fails(to handle that exception)
     catch (BadCredentialsException e) {
      throw new Exception("Invalid username or password");
     }
     //logging
        System.out.println(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,authenticationRequest.getPassword())));
    // System.out.println(this.ur.findByEmail(username).getRoles());
     String token = jwtUtil.createToken(username,this.ur.findByEmail(username).getRoles());
     //method defined in user repository
   Users ExistingUser = userService.findUserByEmail(username);
   //  System.out.println("username");
     return new ResponseEntity<>(userService.updateTokenById(ExistingUser,token), HttpStatus.OK);
       // return ResponseEntity.ok(new AuthenticationResponse(token));
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Users user){
        logger.info("register end point accessed");
       Users userExists = userService.findUserByEmail(user.getEmail());

        if(userExists!=null){
            return new ResponseEntity<>("oops",HttpStatus.OK);
        }

        return new ResponseEntity<>(userService.saveUser(user),HttpStatus.CREATED);
    }

}
