package com.absentapp.project.domain.service.auth;

import com.absentapp.project.domain.core.service.BaseService;
import com.absentapp.project.domain.entity.User;
import com.absentapp.project.domain.repository.user.UserRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService extends BaseService<User> implements UserDetailsService {
    protected AuthService(final UserRepository repository) {
        super(repository);
    }

    @Override
    @Cacheable("loggedUser")
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return getRepository().findByEmail(email).orElseThrow(() -> new RuntimeException("Usuário não existente"));
    }

    @Override
    public UserRepository getRepository() {
        return (UserRepository) super.getRepository();
    }
}
