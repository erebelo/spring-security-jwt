package com.rebelo.springsecurityjwt.service.impl;

import com.rebelo.springsecurityjwt.domain.entity.UserEntity;
import com.rebelo.springsecurityjwt.exception.DuplicationException;
import com.rebelo.springsecurityjwt.exception.NotFoundException;
import com.rebelo.springsecurityjwt.repository.UserRepository;
import com.rebelo.springsecurityjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return this.findByEmail(username);
        } catch (Exception e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }

    @Override
    public UserEntity findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("User not found: " + id));
    }

    @Override
    public UserEntity findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new NotFoundException("Person not found: " + email));
    }

    @Override
    public List<UserEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public UserEntity create(UserEntity user) {
        user.setId(null);
//        user.addRole(Role.USER);

        checkEmailDuplication(user);

        return repository.save(user);
    }

    @Override
    public UserEntity update(UserEntity user) {
        checkEmailDuplication(user);

        UserEntity u = this.findById(user.getId());
        u.setName(user.getName());
        u.setEmail(user.getEmail());
        u.setRoles(user.getRoles());

        return repository.save(u);
    }

    @Override
    public void delete(Long id) {
        final UserEntity u = this.findById(id);
        repository.delete(u);
    }

    private void checkEmailDuplication(UserEntity user) {
        final String email = user.getEmail();
        if (email != null && email.length() > 0) {
            final Long id = user.getId();
            final UserEntity u = repository.findByEmail(email).orElse(null);
            if (u != null && !Objects.equals(u.getId(), id)) {
                throw new DuplicationException("Email duplication: " + email);
            }
        }
    }
}
