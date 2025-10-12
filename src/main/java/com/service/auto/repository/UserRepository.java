package com.service.auto.repository;

import com.service.auto.entity.User;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository extends BaseRepository<User> implements InitializingBean {

    @Override
    public void afterPropertiesSet() {
        this.setClazz(User.class);
    }

    public Optional<User> findByEmail(String email) {
        if (StringUtils.isBlank(email)) {
            return Optional.empty();
        }
        var query = entityManager.createQuery(
                        "SELECT u FROM User u WHERE u.deleted = false AND u.email = :email",
                        User.class
                )
                .setParameter("email", email)
                .setMaxResults(1);

        List<User> results = query.getResultList();
        return results.isEmpty()
                ? Optional.empty()
                : Optional.of(results.get(0));
    }


   public Optional<User> findByResetToken(String token){

       if (StringUtils.isBlank(token)) {
           return Optional.empty();
       }
       var query =entityManager.createQuery(" select u from User as u WHERE u.deleted = false and u.resetToken = :token",
               User.class)
               .setParameter("token", token)
               .setMaxResults(1);

       List<User> results = query.getResultList();
       return results.isEmpty()
               ? Optional.empty()
               : Optional.of(results.get(0));
    }


}
