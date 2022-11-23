package com.cw.userService.services;

import com.cw.userService.models.Roles;
import com.cw.userService.models.Users;
import com.cw.userService.repositories.RoleRepository;
import com.cw.userService.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
   // @Autowired
    //private AuthService as;
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;
    // to find user by mailId
    public Users findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }
    //to update the token of the user everytime the user logins
    public Users updateTokenById(Users ExistingUser, String token){
        ExistingUser.setToken(token);
        return userRepository.save(ExistingUser);
    }
    //After registering save user by his role
    public Users saveUser(Users user){
        //Bcrypt is a one way strong Hashing function

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        System.out.println(user.getRoles());

        Roles userRole = roleRepository.findByRole("USER");
        user.setRoles((new HashSet<>(Arrays.asList(userRole))));

        System.out.println(user);
        return userRepository.save(user);
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(email);
        if(user!=null){
            List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
            return builderUserForAuthentication(user, authorities);
        }
        else{
            throw new UsernameNotFoundException("username not found in database");
        }
    }
    public List<GrantedAuthority> getUserAuthority(Set<Roles> userRoles){
        Set<GrantedAuthority> roles = new HashSet<>();
        userRoles.forEach((role)->{
            roles.add(new SimpleGrantedAuthority(role.getRole()));
        });
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }
    public UserDetails builderUserForAuthentication(Users user, List<GrantedAuthority> authorities){
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
