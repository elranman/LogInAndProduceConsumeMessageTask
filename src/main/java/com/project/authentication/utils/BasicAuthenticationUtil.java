package com.project.authentication.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.codec.digest.DigestUtils;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

public class BasicAuthenticationUtil {

    public static String encryptToMD5(String str) {
        return DigestUtils.md5Hex(str);
    }

    public static String jwtCreation(String subject, Instant time, Map<String, Object> claims , byte[] secret) {
        return Jwts.builder()
                .setSubject(subject)
                .addClaims(claims)
                .setIssuedAt(Date.from(time))
                .setExpiration(Date.from(time.plus(2, ChronoUnit.MINUTES)))
                .signWith(Keys.hmacShaKeyFor(secret))
                .compact();
    }

    public static Jws<Claims> jwtVerification(String jwt,byte[] secret) {
        return Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(secret))
                .parseClaimsJws(jwt);
    }
}
