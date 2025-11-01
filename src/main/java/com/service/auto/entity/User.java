package com.service.auto.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@SequenceGenerator(name = "users_seq", sequenceName = "users_seq", allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor
public class User implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
    @SequenceGenerator(name = "users_seq", sequenceName = "USERS_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "nume")
    private String nume;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "is_enabled")
    private Boolean enabled = true;

    @Column(name = "is_deleted")
    private Boolean deleted = false;

    @Version
    private Integer version;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @Column(name = "reset_token", length = 100)
    private String resetToken;

    @Column(name = "token_expiry")
    private LocalDateTime tokenExpiry;
}
