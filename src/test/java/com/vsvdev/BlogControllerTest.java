package com.vsvdev;

import com.vsvdev.controller.BlogController;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("a")
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql","/articles-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-user-after.sql","/articles-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class BlogControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BlogController controller;


    @Test
    public void freeEnglishPageTest() throws Exception{
        this.mockMvc.perform( get("/freeEnglish") )
                .andDo( print() )
                .andExpect( authenticated() );

    }
    @Test
    public void articleListTest() throws Exception{
        this.mockMvc.perform( get("/freeEnglish") )
                .andDo( print() )
                .andExpect( authenticated() )
                .andExpect( xpath( "/html/body/div/div/div/div/div" ).nodeCount( 4 ) );

    }

    @Test
    public void addArticleToList() throws Exception{
        MockHttpServletRequestBuilder art=multipart( "/freeEnglish/add" )
                .param( "title","news1" )
                .param( "anons","some news" )
                .param( "text","bla bla" )
                .with( csrf() );
        this.mockMvc.perform(  art )
                .andExpect( authenticated() )
                .andExpect( status().is3xxRedirection() );
        this.mockMvc.perform(  get("/freeEnglish"))
             .andExpect( xpath( "/html/body/div/div/div/div/div" ).nodeCount( 5 ) )
               .andExpect( content().string(containsString("news1")) )
              .andExpect( content().string(containsString("some news")) );



    }
}
