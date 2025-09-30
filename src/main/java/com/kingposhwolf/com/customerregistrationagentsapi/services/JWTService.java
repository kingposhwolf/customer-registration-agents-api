package com.kingposhwolf.com.customerregistrationagentsapi.services;

import com.kingposhwolf.com.customerregistrationagentsapi.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
public class JWTService {
    @Value("${JWT_SECRETE}")
    private String jwtSecret;

    @Value("${JWT_EXPIRATION_TIME}")
    private long expirationTime;

    private final CaffeineCacheService cacheService;

    public JWTService(CaffeineCacheService cacheService) {
        this.cacheService = cacheService;
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTime * 1000))
                .signWith(getKey())
                .compact();
    }

    public String generateToken(UserDetails user){
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getAuthorities());
        return createToken(claims, user.getUsername());
    }

    public String getUsernameFromToken(String token){
        return getClaimFromToken(token, Claims::getSubject);
    }

    private Date getExpirationDate(String token){
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver){
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Key getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isTokenExpired(String token){
        Date expirationDate = getExpirationDate(token);
        return expirationDate.before(new Date());
    }

    public boolean validateToken(String token, User user){
        String username = getUsernameFromToken(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token) && !isTokenBlacklisted(token));
    }

    public void blacklistToken(String token) {
        try {
            cacheService.saveData( "blacklisted", token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isTokenBlacklisted(String token) {
        try {
            String cachedValue = cacheService.getDataByKey("blacklisted");
            return token.equals(cachedValue);
        } catch (Exception e) {
            return false;
        }
    }
}
