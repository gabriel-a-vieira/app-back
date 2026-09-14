package com.softix.app_back.config;

import lombok.RequiredArgsConstructor;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenConfig tokenConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorizedHeader = request.getHeader("Authorization");

        if (StringUtils.isNotBlank(authorizedHeader) && authorizedHeader.startsWith("Bearer ")) {

            String token = authorizedHeader.substring("Bearer ".length());
            Optional<JWTUserData> user = tokenConfig.validateToken(token);

            if (user.isPresent()) {

                JWTUserData userData = user.get();
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userData, null, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }

            filterChain.doFilter(request, response);

        } else {
            filterChain.doFilter(request, response);
        }

    }

}
