package hospital.medflow.config.security;

import hospital.medflow.service.UserService;
import hospital.medflow.utils.SecurityUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserService service;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!isPublic(request.getServletPath())) {
            try {
                String authorization = request.getHeader(SecurityUtils.AUTHORIZATION);
                Claims claims = jwtUtils.validateAccessToken(authorization);
                UserDetails userDetails = service.findByUsername(claims.getSubject());
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean isPublic(String url) {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        return Arrays.stream(SecurityUtils.WHITE_LIST)
                .anyMatch(pattern -> pathMatcher.match(pattern, url));
    }

}
