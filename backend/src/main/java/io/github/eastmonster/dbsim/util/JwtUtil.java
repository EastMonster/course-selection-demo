package io.github.eastmonster.dbsim.util;

import io.jsonwebtoken.*;

import java.util.Date;

public class JwtUtil {
    private final static long EXPIRATION_TIME = 3600;

    private final static String SECRET_KEY = "dbsimdbsimdbsimdbsimdbsimdbsimdb";

    public static String generateToken(Integer id, Boolean isStudent) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 1000 * EXPIRATION_TIME);
        return Jwts.builder()
                .setHeaderParam("type", "JWT")
                .setSubject(id.toString()) // FIXME 学生手动输入链接仍然可以访问教师页面，可能这里得改？
                .claim("id", id)
                .claim("isStudent", isStudent)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public static Claims getClaimsByToken(String token) {
        if (token == null) return null;

        JwtParser parser = Jwts.parser();
        try {
            Jws<Claims> claimsJws = parser
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);
            return claimsJws.getBody();
        } catch (Exception e) {
            return null;
        }
    }
}
