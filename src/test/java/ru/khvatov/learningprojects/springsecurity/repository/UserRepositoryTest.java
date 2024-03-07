package ru.khvatov.learningprojects.springsecurity.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.khvatov.learningprojects.springsecurity.entity.UserAuthorityEntity;
import ru.khvatov.learningprojects.springsecurity.entity.UserEntity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldRetrieveUserFromDatabase() {
        //given
        final String email = "maximkhvatovv@yandex.ru";

        //when
        final Optional<UserEntity> user = this.userRepository.findByEmail(email);

        //then
        assertThat(user).isPresent();
        final UserEntity userEntity = user.get();
        assertThat(userEntity.getEmail()).isEqualTo(email);
        assertThat(userEntity.getPasswordEntity().getPassword()).isEqualTo("{noop}qwerty2001");
        final List<String> authorities = userEntity.getAuthorities().stream()
                .map(UserAuthorityEntity::getName)
                .toList();
        assertThat(authorities).hasSize(2);
        assertThat(authorities).containsAll(List.of("ROLE_USER", "ROLE_DB_USER"));
    }
}