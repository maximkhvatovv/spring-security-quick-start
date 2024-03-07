package ru.khvatov.learningprojects.springsecurity.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @OneToOne(mappedBy = "owner", optional = false, fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @PrimaryKeyJoinColumn
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private UserPasswordEntity passwordEntity;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private List<UserAuthorityEntity> authorities = new ArrayList<>();
}
