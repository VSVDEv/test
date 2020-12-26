package com.vsvdev.service;

import com.vsvdev.model.Role;
import com.vsvdev.model.User;
import com.vsvdev.repo.UserRepo;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @MockBean
    private UserRepo userRepo;
    @MockBean
    private PasswordEncoder encoder ;
    @Autowired
    private UserService userService;
    @Test
    public void addUser() {
        User user =new User();
        boolean isUserCreated=userService.addUser( user );
        Assert.assertTrue(isUserCreated);
        Assert.assertTrue( CoreMatchers.is( user.getRoles() ).matches( Collections.singleton( Role.USER ) ) );
        Assert.assertTrue( user.isActive() );
        Mockito.verify( encoder,Mockito.times( 1 ) ).encode( user.getPassword() );
        Mockito.verify( userRepo,Mockito.times( 1 ) ).save( user );
    }

    @Test
    public void addUserFailTest(){
        User user = new User();
        user.setUsername( "Joe" );
        Mockito.doReturn( user)
                .when( userRepo )
                .findByUsername( "Joe" );
        boolean isUserCreated=userService.addUser( user );
        Assert.assertFalse( isUserCreated );
        Mockito.verify( encoder,Mockito.times( 0 ) ).encode( user.getPassword() );
        Mockito.verify( userRepo,Mockito.times( 0 ) ).save( ArgumentMatchers.any(User.class) );
    }
}