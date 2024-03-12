package ru.khvatov.learningprojects.springsecurity.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.DisableEncodeUrlFilter;
import ru.khvatov.learningprojects.springsecurity.security.configurer.HexConfigurer;
import ru.khvatov.learningprojects.springsecurity.security.filter.DenyAccessViaCurlClientFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .addFilterBefore(new DenyAccessViaCurlClientFilter(), DisableEncodeUrlFilter.class)
                .with(new HexConfigurer(), withDefaults())
                .httpBasic(withDefaults())
                .build();
    }
}
