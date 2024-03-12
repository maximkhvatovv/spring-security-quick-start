package ru.khvatov.learningprojects.springsecurity.security.configurer;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import ru.khvatov.learningprojects.springsecurity.security.filter.HexTokenAuthenticationFilter;

import java.io.IOException;

public class HexConfigurer extends AbstractHttpConfigurer<HexConfigurer, HttpSecurity> {
    private final AuthenticationEntryPoint authenticationEntryPoint;

    public HexConfigurer() {
        this(new AuthenticationEntryPoint() {
                 @Override
                 public void commence(
                         HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
                     response.setHeader(HttpHeaders.WWW_AUTHENTICATE, "Hex realm=\"My realm\"");
                     response.sendError(HttpStatus.FORBIDDEN.value());
                 }
             }
        );
    }

    public HexConfigurer(AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    public void init(HttpSecurity builder) throws Exception {
        builder.exceptionHandling(exceptionHandling ->
                exceptionHandling.authenticationEntryPoint(this.authenticationEntryPoint)
        );
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        final var authenticationManager = builder.getSharedObject(AuthenticationManager.class);
        builder.addFilterBefore(
                new HexTokenAuthenticationFilter(authenticationManager, this.authenticationEntryPoint),
                BasicAuthenticationFilter.class);
    }
}
