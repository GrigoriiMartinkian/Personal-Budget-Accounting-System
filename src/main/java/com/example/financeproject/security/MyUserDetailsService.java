package com.example.financeproject.security;

import com.example.financeproject.models.User;
import com.example.financeproject.repositories.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user1=userRepository.findByUsername(username);
        if(user1==null){
            throw new UsernameNotFoundException("User not found"+username);
        }
        return new UserPrincipal(user1);
    }
}
