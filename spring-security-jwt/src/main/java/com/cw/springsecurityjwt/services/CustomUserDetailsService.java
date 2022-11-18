package com.cw.springsecurityjwt.services;

import com.cw.springsecurityjwt.models.Roles;
import com.cw.springsecurityjwt.models.Users;
import com.cw.springsecurityjwt.repositories.RoleRepository;
import com.cw.springsecurityjwt.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;
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
        System.out.println(ExistingUser);
        return userRepository.save(ExistingUser);
    }
    public Users saveUser(Users user){
        //Bcrypt is a one way strong Hashing function
        //calling a bean in configuration file(WebSecurityConfig)
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        System.out.println(user.getRoles());
        /*List<Roles> admin = user.getRoles().stream().filter(x -> x.getRole().contains("ADMIN")).collect(Collectors.toList());
        List<Roles> washer = user.getRoles().stream().filter(x -> x.getRole().contains("WASHER")).collect(Collectors.toList());
        if(washer.size()==1)
        {
            Roles userRole = roleRepository.findByRole("WASHER");
            user.setRoles((new HashSet<>(Arrays.asList(userRole))));
        }
        else if (admin.size()==1) {
            Roles userRole = roleRepository.findByRole("ADMIN");
            user.setRoles((new HashSet<>(Arrays.asList(userRole))));
        }
        else {
            Roles userRole = roleRepository.findByRole("USER");
            user.setRoles((new HashSet<>(Arrays.asList(userRole))));
        }*/
        //logging the saved user in console
        Roles userRole = roleRepository.findByRole("ADMIN");
        user.setRoles((new HashSet<>(Arrays.asList(userRole))));
        System.out.println(user);
        return userRepository.save(user);
    }
//   @PostConstruct
//    public void init(){
//        Users admin = new Users();
//        admin.setEmail("suhan@gmail.com");
//        admin.setPassword(bCryptPasswordEncoder.encode("suhan1234"));
//        admin.setEnabled(true);
//        admin.setToken("");
//        admin.setFullname("suhan1");
//        Roles userRole = roleRepository.findByRole("ADMIN");
//        admin.setRoles((new HashSet<>(Arrays.asList(userRole))));
//        userRepository.save(admin);
//    }
    @Override
   // public Users loadUserByUsername(String email) throws UsernameNotFoundException {
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(email);
        if(user!=null){
            System.out.println("user"+user);
            List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
            return builderUserForAuthentication(user, authorities);
        }
        else{
            throw new UsernameNotFoundException("username not found in database");
        }

    }
    public List<GrantedAuthority> getUserAuthority(Set<Roles> userRoles){
        Set<GrantedAuthority> roles = new HashSet<>();
        System.out.println(userRoles);
        userRoles.forEach((role)->{
            roles.add(new SimpleGrantedAuthority(role.getRole()));
        });
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }
    public UserDetails builderUserForAuthentication(Users user, List<GrantedAuthority> authorities){
        System.out.println(authorities);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

}
