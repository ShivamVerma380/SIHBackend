package com.bootrestemailauth.userapi.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bootrestemailauth.userapi.helper.JwtUtil;
import com.bootrestemailauth.userapi.services.CustomUserDetailsService;

/*
JWT Authentication filter is basically the class which will check if the request sent by user has a valid JWT Token or not. If it has a 
valid JWT Token then only further part of the code will be executed else it will return from there only.
*/
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    @Autowired
    private CustomUserDetailsService customUserDetailService;

    @Autowired
    private JwtUtil jwtUtilHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        String requestTokenHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;
        if(requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer")){
            jwtToken=requestTokenHeader.substring(7);

            try {
                username = this.jwtUtilHelper.extractUsername(jwtToken);  //email id return
            } catch (Exception e) {
                //TODO: handle exception
                e.printStackTrace();
            }

            UserDetails userDetails=this.customUserDetailService.loadUserByUsername(username);

            if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken= new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }else{
                System.out.println("Token is not validated");
            }
        }

        filterChain.doFilter(request, response);

    }

}