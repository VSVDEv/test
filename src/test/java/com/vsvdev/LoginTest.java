package com.vsvdev;

import com.vsvdev.controller.MainController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql","/articles-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-user-after.sql","/articles-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class LoginTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MainController controller;

    @Test
    public void testContextLoads() throws Exception{
       //assertThat(controller).isNotNull();
        this.mockMvc.perform( get("/"))
                .andDo(print() )
                .andExpect( status().isOk() )
                .andExpect( content().string(containsString("Hello, guest")) )
                .andExpect( content().string(containsString("Hello, main page!")) )
                .andExpect( content().string(containsString("Album example")) );
    }

    @Test
    public void loginTest() throws Exception{
        this.mockMvc.perform( get( "/bookPrinter") )
                .andDo( print() )
                .andExpect( status().is3xxRedirection() )
                .andExpect( redirectedUrl("http://localhost/login") );
    }

    @Test
    @Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void correctLoginTest() throws Exception{
        this.mockMvc.perform( formLogin().user( "a" ).password( "a" ) )
        .andDo( print() )
        .andExpect( status().is3xxRedirection() )
        .andExpect( redirectedUrl( "/" ) );
    }

    @Test
    public void badCridentials()throws Exception{
        this.mockMvc.perform( post( "/login" ).param( "user","Ali" ) )
                .andDo( print() )
                .andExpect( status().isForbidden() );
    }
}
