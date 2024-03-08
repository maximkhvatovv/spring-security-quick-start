package ru.khvatov.learningprojects.springsecurity.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.khvatov.learningprojects.springsecurity.entity.AuthorityEntity;
import ru.khvatov.learningprojects.springsecurity.entity.UserEntity;
import ru.khvatov.learningprojects.springsecurity.entity.UserPasswordEntity;

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
                .map(AuthorityEntity::getName)
                .toList();
        assertThat(authorities).hasSize(2);
        assertThat(authorities).containsAll(List.of("ROLE_USER", "ROLE_DB_USER"));
    }

    @Test
    void shouldSaveUserToDatabase() {
        //given
        final String userEmail = "newuser@mail.ru";
        final UserEntity userEntity = UserEntity.builder()
                .email(userEmail)
                .build();
        final String userPassword = "{noop}strong-pass";
        final UserPasswordEntity userPasswordEntity = UserPasswordEntity.builder()
                .password(userPassword)
                .build();
        userEntity.setPasswordEntity(userPasswordEntity);

        //when
        final UserEntity saved = userRepository.save(userEntity);

        //then
        assertThat(saved).isNotNull();
        assertThat(saved.getEmail()).isEqualTo(userEmail);
        assertThat(saved.getPasswordEntity().getPassword()).isEqualTo(userPassword);
    }
}