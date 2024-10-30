package com.alten.mx.scheduling.web.configuration;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import com.alten.mx.scheduling.persistance.dao.UsersCrudRepository;
import com.alten.mx.scheduling.persistance.entity.Users;
import lombok.AllArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private UsersCrudRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        List<Users> user = userRepository.findByEmail(usernameOrEmail);
        if (user.size() == 0)
            throw new UsernameNotFoundException("User not exists by Username or Email");

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("USER"));

        return new org.springframework.security.core.userdetails.User(
                usernameOrEmail,
                user.get(0).getPassword(), authorities);
    }
}
