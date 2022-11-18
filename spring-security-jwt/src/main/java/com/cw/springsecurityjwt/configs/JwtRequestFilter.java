package com.cw.springsecurityjwt.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//intercept request once and examine header
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String token = jwtUtil.resolveToken(request);
    System.out.println(request+"hi");
    System.out.println(response+"hello");
    System.out.println(filterChain+"chain");
    System.out.println("request filter");
    System.out.println(token);
    System.out.println(SecurityContextHolder.getContext().getAuthentication());
    System.out.println(SecurityContextHolder.getContext().getAuthentication());
    if(token!=null&&jwtUtil.validateToken(token)&&SecurityContextHolder.getContext().getAuthentication()==null) {
        System.out.println("inside");
        Authentication auth = jwtUtil.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
        filterChain.doFilter(request,response);
    }
}
