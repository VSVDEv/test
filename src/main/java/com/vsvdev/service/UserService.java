package com.vsvdev.service;

import com.vsvdev.model.Role;
import com.vsvdev.model.User;
import com.vsvdev.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepo userRepository;

    public UserService(UserRepo userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername( username );
    }

    public boolean addUser(User user) {

        User userFromDbName=userRepository.findByUsername( user.getUsername() );

        if(userFromDbName!=null){
            return false;
        }
        user.setActive( true );
        user.getRoles().add( Role.USER );
        user.setPassword(  passwordEncoder.encode( user.getPassword() ) );
        userRepository.save( user );
        return true;
    }
}
