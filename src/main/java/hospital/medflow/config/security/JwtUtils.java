package hospital.medflow.config.security;

import hospital.medflow.config.YmlData;
import hospital.medflow.dto.login.TokenDto;
import hospital.medflow.model.User;
import hospital.medflow.utils.ErrorConstants;
import hospital.medflow.utils.SecurityUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final YmlData ymlData;

    public TokenDto generateAccessToken(User user, Map<String, Object> claims) {
        Date expiry = new Date(System.currentTimeMillis() + ymlData.getAccessToken());
        claims.put(SecurityUtils.TYPE, SecurityUtils.ACCESS_TOKEN);
        String token = buildToken(user.getUsername(), claims, expiry);
        return TokenDto.builder().
                token(token).
                expiry(expiry).
                build();
    }

    public TokenDto generateRefreshToken(User user) {
        Date expiry = new Date(System.currentTimeMillis() + ymlData.getRefreshToken());
        String token = buildToken(user.getUsername(), Map.of(), expiry);
        return TokenDto.builder().
                token(token).
                expiry(expiry).
                build();
    }

    public Claims validateAccessToken(String token) {
        if (token == null || !token.startsWith(SecurityUtils.BEARER)) {
            throw new BadCredentialsException(ErrorConstants.INVALID_TOKEN);
        }

        Claims claims = exractClaims(token.replace(SecurityUtils.BEARER, ""));

        if (!SecurityUtils.ACCESS_TOKEN.equals(claims.get(SecurityUtils.TYPE, String.class))) {
            throw new BadCredentialsException(ErrorConstants.INVALID_TOKEN);
        }
        return claims;
    }

    public Claims exractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(ymlData.getJwtSecretKey().getBytes());
    }

    private String buildToken(String username, Map<String, Object> claims, Date expiry) {
        return Jwts.builder()
                .signWith(getSecretKey())
                .claims(claims)
                .subject(username)
                .issuedAt(new Date())
                .expiration(expiry)
                .compact();
    }
}
