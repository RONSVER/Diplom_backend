package com.superstore.security;

import com.superstore.dto.UserDTO;
import com.superstore.security.model.SecurityUser;
import com.superstore.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDTO userEntity = userService.findByEmail(email);
        return new SecurityUser(userEntity.email(),
                userEntity.passwordHash(),
                List.of(new SimpleGrantedAuthority(userEntity.role().toString())));
    }
}
