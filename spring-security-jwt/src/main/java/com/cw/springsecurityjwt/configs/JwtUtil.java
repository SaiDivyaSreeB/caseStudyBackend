package com.cw.springsecurityjwt.configs;

import com.cw.springsecurityjwt.models.Roles;
import com.cw.springsecurityjwt.models.Users;
import com.cw.springsecurityjwt.services.CustomUserDetailsService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.Set;

@Component
public class JwtUtil {
    //@Value("${security.jwt.token.secret-key:secret}")
    private String secretKey = "secret";
   // @Value("${security.jwt.token.expire-length:3600000}")
    private long validityInMilliseconds = 3600000; //1 hr
    @Autowired
    private CustomUserDetailsService userDetailsService;
    //to create the token
    public String createToken(String username, Set<Roles> set){
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles",set);
        Date now = new Date();
        Date validity = new Date(now.getTime()+validityInMilliseconds);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
    //returns authentication token
    public Authentication getAuthentication(String token){
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails,"", userDetails.getAuthorities());

    }
    //to extract username from token
    private String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
    //to resolve the token without 'Bearer'
    public String resolveToken(HttpServletRequest req){
        String bearerToken = req.getHeader("Authorization");
      System.out.println("hello");
       System.out.println(bearerToken);
        if(bearerToken != null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
    //validating token by extracting expiry date
    public boolean validateToken(String token){
        try{
            System.out.println("validate token");
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            if(claims.getBody().getExpiration().before(new Date())){
                return false;
            }
            return true;
        }
        catch(JwtException | IllegalArgumentException e){
            throw new JwtException("Expired or invalid JWT token");
        }
    }
    //executes after DI
  @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }
}
