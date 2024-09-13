package com.absentapp.project.domain.repository.user;

import com.absentapp.project.domain.core.repository.BaseRepository;
import com.absentapp.project.domain.entity.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User> {

    @Cacheable("user")
    Optional<UserDetails> findByEmail(String email);
}
