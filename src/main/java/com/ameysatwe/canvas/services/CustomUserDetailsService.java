package com.ameysatwe.canvas.services;

import com.ameysatwe.canvas.DAO.UserDao;
import com.ameysatwe.canvas.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));

        if (!user.isApproved()) {
            System.out.println("not approved");
            throw new UsernameNotFoundException("User not approved by admin");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_"+ user.getRole())));

    }
}
