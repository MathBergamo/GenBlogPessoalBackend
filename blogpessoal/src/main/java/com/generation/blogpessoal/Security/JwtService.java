package com.generation.blogpessoal.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {
    public static final String SECRET = "5gqbrAeJLykNC+XfdCOvxkVW/VOawo5EmWZqmfaeCcVcTgtJHN57pgxwseTZzgSw/Ssvof2QH4tGXcB7STGaG//koMuifAKQzuZN15zghYMEzGXYEvj3R1W6Yk2QpEbgHRcWWZFL4dlj9Pd3K2JDRgNB4cmGrix79S1KlsV7H8aQsxrAUQw/ued8oouWaDBJaNmK/XbC+6mlOfCOVSNnwbJo2AQqcUO+zzQ0SwSr5aGl+nB8gfxxTbiNsYCHaNUYYZ+97OaSO2A8/0rF/XA0OG6h0AF4/v3PdjxORNO4HGKKoggzaTn+hbB1Ch1tL5/42AcSkgmSvoHd1ir/FS5QwAWrH2RBYDqI1TUSl9KMp/c=";

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey()).build()
                .parseClaimsJws(token).getBody();
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver) {
        final  Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractUsername(String token) {
        return extractClaim(token,Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String email = extractUsername(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private String createToken(Map<String, Object> claims, String email) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    public String generateToken(String email){
        Map<String,Object> claims = new HashMap<>();
        return createToken(claims, email);
    }
}
