    package com.nurd.your.places.securities;


    import com.nurd.your.places.models.User;
    import io.jsonwebtoken.Claims;
    import io.jsonwebtoken.Jwts;
    import io.jsonwebtoken.SignatureAlgorithm;
    import io.jsonwebtoken.io.Decoders;
    import io.jsonwebtoken.security.Keys;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.stereotype.Component;

    import java.security.Key;
    import java.util.Date;
    import java.util.HashMap;
    import java.util.Map;
    import java.util.function.Function;


    @Component
    public class JwtService {

        @Value("${jwt.secret.key}")
        private String jwtSecretKey;

        @Value("${jwt.expiration.time}")
        private long jwtExpirationTime;

        private Key jwtKey() {
            byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
            return Keys.hmacShaKeyFor(keyBytes);
        }

        public String extractId(String token) {
            return extractClaim(token, Claims::getId);
        }

        public String extractUsername(String token) {
            return extractClaim(token, Claims::getSubject);
        }

        public String extractEmail(String token) {
            return extractClaim(token, (claims) -> claims.get("email", String.class));
        }

        public Date extractExpiration(String token) {
            return extractClaim(token, Claims::getExpiration);
        }

        public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
            final Claims claims = extractAllClaims(token);
            return claimsResolver.apply(claims);
        }

        public Claims extractAllClaims(String token) {
            return Jwts.parserBuilder().setSigningKey(jwtKey()).build().parseClaimsJws(token).getBody();
        }

        private Boolean isTokenExpired(String token) {
            return extractExpiration(token).before(new Date());
        }

        public String generateToken(User user) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", user.getId());
            claims.put("email", user.getEmail());
            claims.put("role", user.getAuthorities());
            return createToken(claims, user.getUsername(), jwtExpirationTime);
        }

        private String createToken(Map<String, Object> claims, String subject, long jwtExpiration) {
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(subject)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() +  jwtExpiration))
                    .signWith(jwtKey(), SignatureAlgorithm.HS256)
                    .compact();
        }

        public Boolean validateToken(String token, UserDetails user) {
            final String username = extractUsername(token);
            return (username.equals(user.getUsername()) && !isTokenExpired(token));
        }
    }
