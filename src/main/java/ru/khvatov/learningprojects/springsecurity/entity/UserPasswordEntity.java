package ru.khvatov.learningprojects.springsecurity.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_password")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPasswordEntity {
    @Id
    @Column(name = "user_id", nullable = false, unique = true)
    private Long id;
    @Column(name = "password", nullable = false)
    private String password;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    @JoinColumn(name = "user_id", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private UserEntity owner;
}
