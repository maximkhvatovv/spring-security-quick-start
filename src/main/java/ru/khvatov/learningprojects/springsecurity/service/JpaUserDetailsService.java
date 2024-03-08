package ru.khvatov.learningprojects.springsecurity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.khvatov.learningprojects.springsecurity.entity.AuthorityEntity;
import ru.khvatov.learningprojects.springsecurity.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JpaUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(userEntity -> User.builder()
                        .username(userEntity.getEmail())
                        .password(userEntity.getPasswordEntity().getPassword())
                        .authorities(
                                userEntity.getAuthorities().stream()
                                        .map(AuthorityEntity::getName)
                                        .toArray(String[]::new)
                        )
                        .build()
                )
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User with email %s is not found".formatted(username)
                ));
    }
}
