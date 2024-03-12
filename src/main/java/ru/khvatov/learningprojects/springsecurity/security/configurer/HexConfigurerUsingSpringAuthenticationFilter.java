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
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

public class HexConfigurerUsingSpringAuthenticationFilter extends AbstractHttpConfigurer<HexConfigurerUsingSpringAuthenticationFilter, HttpSecurity> {
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AuthenticationConverter authenticationConverter;

    public HexConfigurerUsingSpringAuthenticationFilter(final AuthenticationConverter authenticationConverter) {
        this(new AuthenticationEntryPoint() {
                 @Override
                 public void commence(
                         HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
                     response.setHeader(HttpHeaders.WWW_AUTHENTICATE, "Hex realm=\"My realm\"");
                     response.sendError(HttpStatus.FORBIDDEN.value());
                 }
             },
                authenticationConverter
        );
    }

    public HexConfigurerUsingSpringAuthenticationFilter(final AuthenticationEntryPoint authenticationEntryPoint,
                                                        final AuthenticationConverter authenticationConverter
    ) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.authenticationConverter = authenticationConverter;
    }

    @Override
    public void init(final HttpSecurity builder) throws Exception {
        builder.exceptionHandling(exceptionHandling ->
                exceptionHandling.authenticationEntryPoint(this.authenticationEntryPoint)
        );
    }

    @Override
    public void configure(final HttpSecurity builder) throws Exception {
        final var authenticationManager = builder.getSharedObject(AuthenticationManager.class);
        final var authenticationFilter = new AuthenticationFilter(authenticationManager, this.authenticationConverter);
        authenticationFilter.setSuccessHandler(
                (request, response, authentication) -> {}
        );
        authenticationFilter.setFailureHandler(
                new AuthenticationEntryPointFailureHandler(this.authenticationEntryPoint)
        );
        builder.addFilterBefore(
                authenticationFilter,
                BasicAuthenticationFilter.class);
    }
}
