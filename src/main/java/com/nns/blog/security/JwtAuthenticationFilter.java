package com.nns.blog.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            // 1. Get the token from the header
            String jwt = parseJwt(request);

            // 2. Safely check if a token exists and no authentication is set yet
            if (jwt != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // 3. Extract username first (Throws exception automatically if signature/expiration fails)
                String username = jwtUtil.extractUsername(jwt);

                if (username != null) {
                    // 4. Load the user details from database/service
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    // 5. Validate the token against the user details using modern 0.13.0 helper
                    if (jwtUtil.isTokenValid(jwt, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());

                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        // 6. Set context
                        SecurityContextHolder.getContext().setAuthentication(authentication);

                        // Debug logs
                        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                        logger.info("Auth: " + auth.getName() + " | Authorities: " + auth.getAuthorities());
                    }
                }
            }
        } catch (Exception e) {
            // JJWT 0.13.0 exceptions (ExpiredJwtException, MalformedJwtException) will catch here cleanly
            logger.error("Something went wrong with JWT validation: {}", e);
        }
        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) throws Exception {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
}
