package com.nns.blog.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration-ms}")
    private long jwtExpiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    /*
    Key Changes for 0.13.0:Signing:
    Jwts.SIG.HS256 replaces SignatureAlgorithm.HS256.
    Parsing: .verifyWith(key) and .parseSignedClaims(token) are the new standards.
    Claims: .getPayload() replaces .getBody().
    */
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey(), Jwts.SIG.HS256) // 0.13.0 signature
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey()) // 0.13.0 parser
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }
}
/*
    !!Why this is safer in JJWT 0.13.0:!!
        In the older versions of JJWT, parsing structural anomalies often resulted
        in fragmented null values. In 0.13.0, calling parseSignedClaims() inside
        your helper's extractUsername() function will immediately throw an explicit
        ExpiredJwtException or MalformedJwtException if the token is tampered with
        or expired. Your try-catch block catches this immediately, refusing to
        process invalid tokens safely before hitting database layers.


    !!Setting up a clean SecurityFilterChain along with global token exception handling
    requires a specific architecture!!
    Because your JwtAuthenticationFilter executes before Spring's standard DispatcherServlet,
    standard @RestControllerAdvice classes will not catch exceptions like ExpiredJwtException
    or MalformedJwtException thrown inside your filter.

        The industry standard to cleanly solve this relies on two primary layers: a custom
    AuthenticationEntryPoint to process unauthorized exceptions, and a modern Lambda-based
    SecurityFilterChain configuration using the latest Spring Security updates.

    !!Step 1: Handle Expired/Invalid Tokens (JwtAuthenticationEntryPoint)!!
    This component acts as your global exception catcher specifically for the Security
    Filter Chain. If a token is expired, missing, or altered, this class sends a uniform,
    structured JSON response directly back to the client.
        Create a JwtAuthenticationEntryPoint class implementing AuthenticationEntryPoint to
    catch security exceptions. The commence method should set the response status to
    401 Unauthorized, define APPLICATION_JSON_VALUE, and write a structured JSON error
    body (e.g., status, error message, path) using an ObjectMapper.

    !!Step 2: Inject Exception Logging inside your Filter!!
    Within your JwtAuthenticationFilter, catch specific io.jsonwebtoken exceptions
    (e.g., ExpiredJwtException, MalformedJwtException) and use request.setAttribute
    to pass a custom error message to the JwtAuthenticationEntryPoint for handling.

    !!Step 3: Configure the SecurityFilterChain!!
    Define a SecurityFilterChain bean to configure security rules:
    Disable CSRF: AbstractHttpConfigurer::disable for stateless APIs.
    Permit/Authenticate: Configure endpoints using authorizeHttpRequests, typically
        allowing authentication endpoints while restricting others.
    Stateless Session: Use SessionCreationPolicy.STATELESS.
    Exception Handling: Link the JwtAuthenticationEntryPoint using .exceptionHandling.
    Filter Order: Use .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
*/

