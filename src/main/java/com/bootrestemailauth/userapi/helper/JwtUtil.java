package com.bootrestemailauth.userapi.helper;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

//method for generating token . e.g. validate, isExp  
//util class for jwt

@Component
public class JwtUtil {

    private String SECRET_KEY;

    private int jwtExpirationinMs;

    private int refreshExpirationinMs;

    @Value("${jwt.secret}")
	public void setSecret(String secret) {
		this.SECRET_KEY = secret;
	}
	
	@Value("${jwt.expirationInMs}")
	public void setJwtExpirationInMs(int jwtExpirationInMs) {
		this.jwtExpirationinMs = jwtExpirationInMs;
	}

	@Value("${jwt.refreshexpirationInMs}")
	public void setRefreshExpirationDateInMs(int refreshExpirationInMs){
		this.refreshExpirationinMs = refreshExpirationInMs;
	}

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationinMs))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token) {
        // try {
        //     final String username = extractUsername(token);
        //     return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        // } catch (ExpiredJwtException expiredJwtException) {
        //     //TODO: handle exception
        //     //throw new Exception(header,claims,"Token has expired",expiredJwtException);
        // }
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
			return true;
            
        } catch (ExpiredJwtException e) {
            throw e;
        }
        // final String username = extractUsername(token);
        // return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String doGenerateRefreshToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + refreshExpirationinMs))
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();

	}
}