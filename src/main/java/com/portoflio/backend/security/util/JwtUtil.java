package com.portoflio.backend.security.util;

import com.portoflio.backend.model.Role;
import com.portoflio.backend.model.UserPortfolio;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;


@Component
public class JwtUtil {

    @Value("${jwt.secret.key}")
    private String JWT_SECRET_KEY;
    @Value("${jwt.time.expiration}")
    private String JWT_TIME_EXPIRATION; // 1 dia

    // Generar token de acceso
    public String generateAccessToken(UserPortfolio userPortfolio){
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("id", userPortfolio.getId());
        claims.put("roles", userPortfolio.getRoles().stream().map(Role::getName).toList());

        return Jwts
                .builder()
                .setSubject(userPortfolio.getEmail())
                .addClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(JWT_TIME_EXPIRATION)))
                .signWith(getSignatureKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Validar el token de acceso
    public boolean isTokenValid(String token){
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSignatureKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (Exception e){
            return false;
        }
    }

    // Validad caducidad del token
    public Boolean isTokenExpired(String token){
        return getExpirationDate(token).before(new Date());
    }

    // Obtener firma del token tipo Key
    public Key getSignatureKey(){
        byte[] keyByte = Decoders.BASE64.decode(JWT_SECRET_KEY);
        return Keys.hmacShaKeyFor(keyByte);
    }

    // Métodos para extraer un claim a partir de los métodos de la clase Claims
    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver){
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Métodos para extraer todos los claims, por si queremos extraer el tiempo de duración, etc.
    public Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignatureKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Obtener username del token
    public String getUsername(String token){
        return extractClaims(token, Claims::getSubject);
    }

    // Obtener fecha de expiración
    public Date getExpirationDate(String token){
        return extractClaims(token, Claims::getExpiration);
    }

}
