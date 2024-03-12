package ru.khvatov.learningprojects.springsecurity.security.converter;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.web.authentication.AuthenticationConverter;

import java.nio.charset.StandardCharsets;

public class HexAuthenticationConverter implements AuthenticationConverter {

    @Override
    public Authentication convert(HttpServletRequest request) {
        final var authenticationHeaderValue = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authenticationHeaderValue != null && authenticationHeaderValue.startsWith("Hex ")) {
            final var rawToken = authenticationHeaderValue.trim().replaceAll("^Hex ", "");
            final var token = new String(Hex.decode(rawToken), StandardCharsets.UTF_8);
            final var indexOfSeparator = token.indexOf(":");
            return UsernamePasswordAuthenticationToken.unauthenticated(
                    token.substring(0, indexOfSeparator),
                    token.substring(indexOfSeparator + 1)
            );
        } else
            return null;
    }
}
