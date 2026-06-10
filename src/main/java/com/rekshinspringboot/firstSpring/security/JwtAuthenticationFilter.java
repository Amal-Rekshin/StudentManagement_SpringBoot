package com.rekshinspringboot.firstSpring.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.rekshinspringboot.firstSpring.service.CustomPersonDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final CustomPersonDetailsService customPersonDetailsService;

	public JwtAuthenticationFilter(JwtUtil jwtUtil, CustomPersonDetailsService customPersonDetailsService) {
		super();
		this.jwtUtil = jwtUtil;
		this.customPersonDetailsService = customPersonDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException {
	
	    String token = request.getHeader("Authorization");
	    System.out.println("Token received: " + token);
	
	    if (token != null && token.startsWith("Bearer ")) {
	
	        token = token.substring(7);
	        try {
	            String username = jwtUtil.extractUsername(token);
	            System.out.println("Extracted username: " + username);
	
	            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	
	                UserDetails userDetails = customPersonDetailsService.loadUserByUsername(username);
	                System.out.println("User details loaded: " + userDetails.getUsername());
	
	                if (jwtUtil.isValidToken(token)) {
	                    System.out.println("Token is valid, setting authentication");
	                    UsernamePasswordAuthenticationToken authToken =
	                            new UsernamePasswordAuthenticationToken(
	                                    userDetails,
	                                    null,
	                                    userDetails.getAuthorities()
	                            );
	
	                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                    SecurityContextHolder.getContext().setAuthentication(authToken);
	                } else {
	                    System.out.println("Token is invalid");
	                }
	            }
	        } catch (Exception e) {
	            System.out.println("Exception in JWT processing: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }
	    filterChain.doFilter(request, response);
	}
}
