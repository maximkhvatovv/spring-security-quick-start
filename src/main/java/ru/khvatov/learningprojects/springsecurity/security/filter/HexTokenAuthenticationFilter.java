package ru.khvatov.learningprojects.springsecurity.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class HexTokenAuthenticationFilter extends OncePerRequestFilter {
    private final SecurityContextHolderStrategy securityContextHolderStrategy =
            SecurityContextHolder.getContextHolderStrategy();
    private final SecurityContextRepository securityContextRepository =
            new RequestAttributeSecurityContextRepository();

    private final AuthenticationManager authenticationManager;

    private final AuthenticationEntryPoint authenticationEntryPoint;

    public HexTokenAuthenticationFilter(final AuthenticationManager authenticationManager,
                                        final AuthenticationEntryPoint authenticationEntryPoint
    ) {
        this.authenticationManager = authenticationManager;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    protected void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain
    ) throws ServletException, IOException {

        final var authenticationHeaderValue = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authenticationHeaderValue != null && authenticationHeaderValue.startsWith("Hex ")) {
            final var rawToken = authenticationHeaderValue.trim().replaceAll("^Hex ", "");
            final var token = new String(Hex.decode(rawToken), StandardCharsets.UTF_8);
            final var indexOfSeparator = token.indexOf(":");
            final var authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(
                    token.substring(0, indexOfSeparator),
                    token.substring(indexOfSeparator + 1)
            );
            try {
                final var authenticationResult = authenticationManager.authenticate(authenticationRequest);
                final var securityContext = securityContextHolderStrategy.createEmptyContext();
                securityContext.setAuthentication(authenticationResult);
                securityContextHolderStrategy.setContext(securityContext);
                securityContextRepository.saveContext(securityContext, request, response);

            } catch (final AuthenticationException authenticationException) {
                securityContextHolderStrategy.clearContext();
                authenticationEntryPoint.commence(request, response, authenticationException);
                return;
            }
        }
        filterChain.doFilter(request, response);


    }
}
