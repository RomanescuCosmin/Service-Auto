package com.service.auto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.service.auto.entity.Role;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
