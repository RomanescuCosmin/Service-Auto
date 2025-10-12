package com.service.auto.service;


import com.service.auto.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class UserService extends BaseService {


    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void createResetTokenForUser(User user, String token) {
        user.setResetToken(token);
        user.setTokenExpiry(LocalDateTime.now().plusHours(1));
        userRepository.merge(user);
    }

    public Optional<User> validatePasswordResetToken(String token) {
        return userRepository.findByResetToken(token)
                .filter(u -> u.getTokenExpiry() != null && u.getTokenExpiry().isAfter(LocalDateTime.now()));
    }

    public void updatePassword(User user, String newPassword) {
        user.setPassword(newPassword); // trebuie criptată!
        user.setResetToken(null);
        user.setTokenExpiry(null);
        userRepository.merge(user);
    }

    public User save(User user) {
        return userRepository.merge(user);
    }
}
