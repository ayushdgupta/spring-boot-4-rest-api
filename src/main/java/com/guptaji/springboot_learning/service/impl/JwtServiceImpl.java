package com.guptaji.springboot_learning.service.impl;

import com.guptaji.springboot_learning.model.UserLoginDto;
import com.guptaji.springboot_learning.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.guptaji.springboot_learning.constant.Constants.*;

@Component
public class JwtServiceImpl implements JwtService {

    private static final Logger LOG = LoggerFactory.getLogger(JwtServiceImpl.class);

    private static String secretKey = "";

    static {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(KEY_GEN_ALGO);
            SecretKey genSecretKey = keyGen.generateKey();
            secretKey = Base64.getEncoder().encodeToString(genSecretKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String generateToken(UserLoginDto user) {

        LOG.info("Generating token");
        Map<String, String> claims = new HashMap<>();
        claims.put(ROLE, user.getUserType());

        return Jwts.builder()
                .claims(claims)
                .subject(user.getUserName())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 30))
                .signWith(generateKey(secretKey))
                .compact();
    }

    @Override
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private static SecretKey generateKey(String secretKey) {
        byte[] decodedByteKey = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(decodedByteKey);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(generateKey(secretKey))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenExpired(String token) {
        return extractExpirationTime(token).before(new Date());
    }

    public Date extractExpirationTime(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
