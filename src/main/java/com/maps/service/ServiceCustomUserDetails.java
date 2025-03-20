package com.maps.service;

import com.maps.persistence.model.Privilege;
import com.maps.persistence.model.Role;
import com.maps.persistence.model.User;
import com.maps.persistence.repository.RepositoryUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ServiceCustomUserDetails implements UserDetailsService {

    private final RepositoryUser repositoryUser;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = repositoryUser.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Resource not found"));
        return new org.springframework.security.core.userdetails.User(Objects.requireNonNull(user).getUsername(), user.getPassword(), getAuthorities(user.getRoles()));
    }
    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }
    private Set<GrantedAuthority> getGrantedAuthorities(Set<String> privileges) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
    private Set<String> getPrivileges(Collection<Role> roles) {
        Set<String> permissions = new HashSet<>();
        Set<Privilege> collection = new HashSet<>();
        for (Role role : roles) {
            permissions.add(role.getName());
            collection.addAll(role.getPrivileges());
        }
        for (Privilege item : collection) {
            permissions.add(item.getName());
        }
        return permissions;
    }
}