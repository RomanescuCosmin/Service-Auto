package com.service.auto.dto;


import com.service.auto.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String nume;
    private String email;
    private String password;
    private Boolean enabled = true;
    private Boolean deleted = false;
    private Integer version;
    private Set<Role> roles;
    private String resetToken;
    private LocalDateTime tokenExpiry;
}
