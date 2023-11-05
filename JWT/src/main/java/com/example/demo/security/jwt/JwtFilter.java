package com.example.demo.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;
import com.example.demo.exceptions.LoginException;
import com.example.demo.entities.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtProperties jwtProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = getJwtToken(request);

        Jws<Claims> jwsClaims = validateTokenAndParse(token);

        String username = getUsername(jwsClaims);

        authenticate(username);

        filterChain.doFilter(request, response);

    }

    private void authenticate(String username) {

        var user = (UserEntity) userDetailsService.loadUserByUsername(username);

        Authentication authentication = new PreAuthenticatedAuthenticationToken(user, null, user.getAuthorities());
        var context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);

    }

    private String getUsername(Jws<Claims> jwsClaims) {
        var body = jwsClaims.getBody();

        return body.getSubject();
    }

    private Jws<Claims> validateTokenAndParse(String token) {

        String key = jwtProperties.getKey();

        Jws<Claims> jwsClaims;

        try {

            jwsClaims = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(key.getBytes())).build()
                    .parseClaimsJws(token);
        } catch (JwtException exception) {

            throw new LoginException("Token not valid");
        }

        return jwsClaims;
    }

    private String getJwtToken(HttpServletRequest request) {

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || authorizationHeader.isBlank()
                || !authorizationHeader.startsWith(jwtProperties.getPrefix())) {

            throw new LoginException("Token is missing");
        }

        return authorizationHeader.replace(jwtProperties.getPrefix(), "");

    }

}
