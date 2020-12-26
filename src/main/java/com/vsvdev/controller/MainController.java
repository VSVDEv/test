package com.vsvdev.controller;

import com.vsvdev.model.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String home( Model model) {
        model.addAttribute("title", "main page");
        return "home";
    }

    @GetMapping("/about")
    public String about( Model model) {
        model.addAttribute("title", "about page");
        return "about";
    }
    @GetMapping("/bookPrinter")
    public String book(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("title", "BookPrinter page");
        model.addAttribute( "user",user.getUsername() );
        return "bookPrinter";
    }
}
