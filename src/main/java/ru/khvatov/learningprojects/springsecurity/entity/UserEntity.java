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

    @OneToOne(mappedBy = "owner", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @PrimaryKeyJoinColumn
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private UserPasswordEntity passwordEntity;

    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private List<AuthorityEntity> authorities = new ArrayList<>();

    public void addAuthority(final AuthorityEntity authorityEntity) {
        authorityEntity.getUsers().add(this);
        this.getAuthorities().add(authorityEntity);
    }

    public void setPasswordEntity(final UserPasswordEntity passwordEntity) {
        this.passwordEntity = passwordEntity;
        passwordEntity.setOwner(this);
    }
}
