package com.guptaji.springboot_learning.service.impl;

import com.guptaji.springboot_learning.entity.User;
import com.guptaji.springboot_learning.model.UserLoginDto;
import com.guptaji.springboot_learning.service.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    private static Key generateKey(String secretKey) {
        byte[] decodedByteKey = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(decodedByteKey);
    }
}
