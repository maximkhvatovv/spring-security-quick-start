package ru.khvatov.learningprojects.springsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.khvatov.learningprojects.springsecurity.entity.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {


    @Query("""
            select user
            from UserEntity user
            inner join fetch user.passwordEntity user_pass
            left join fetch user.authorities user_authority
            where user.email = :email
            """)
    Optional<UserEntity> findByEmail(String email);
}
