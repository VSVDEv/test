package com.vsvdev.controller;

import com.vsvdev.model.User;
import com.vsvdev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    private final UserService userService;
    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/registration")
    public String registration(Model model){
        model.addAttribute( "user", new User() );
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser( User user, Model model) throws Exception {

        if (!userService.addUser( user )){
            model.addAttribute( "messageExist", "user exist" );
            return "registration";
        }
        return "redirect:/login";
    }

}
