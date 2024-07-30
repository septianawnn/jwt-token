package com.my.project.service;

import com.my.project.dto.UserDto;
import com.my.project.mapper.UserMapper;
import com.my.project.model.User;
import com.my.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author pi
 */
@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository repository;
    @Autowired
    UserMapper mapper;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                AuthorityUtils.createAuthorityList("ROLE_USER"));
    }

    public UserDto create(UserDto dto){
        User user = mapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        repository.save(user);
        return mapper.toDto(user);
    }

}
