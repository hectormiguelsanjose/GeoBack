package com.biotatf.geoserver.geoback.security.util;

import com.biotatf.geoserver.geoback.security.transfer.JwtUserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

/**
 * convenience class to generate a token for testing your requests.
 * Make sure the used secret here matches the on in your application.yml
 *
 * @author pascal alma
 */
public class JwtTokenGenerator {


    @Value("${jwt.secret}")
    private String _secret;

    /**
     * Generates a JWT token containing username as subject, and userId and role as additional claims. These properties are taken from the specified
     * User object. Tokens validity is infinite.
     *
     * @param u the user for which the token will be generated
     * @return the JWT token
     */
    public static String generateToken(JwtUserDto u, String secret) {
        Claims claims = Jwts.claims().setSubject(u.getUsername());
        claims.put("userId", u.getId() + "");
        claims.put("role", u.getRole());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        JwtUserDto user = new JwtUserDto();
        user.setId(123L);
        user.setUsername("Hector");
        user.setRole("ADMIN");

        System.out.println("**************************************\n\n" + generateToken(user, "Mi_password_de_jwt") + "\n\n**************************************");
    }
}
