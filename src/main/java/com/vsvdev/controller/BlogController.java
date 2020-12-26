package com.vsvdev.controller;

import com.vsvdev.model.Article;
import com.vsvdev.model.User;
import com.vsvdev.repo.ArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class BlogController {
    private final ArticleRepo articleRepository;
@Autowired
    public BlogController(ArticleRepo articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("/freeEnglish")
    public String freeEnglishMain( Model model) {
        Iterable<Article> articles= articleRepository.findAll();
        model.addAttribute("articles", articles);
        return "english";
    }

    @GetMapping("/freeEnglish/add")
    public String freeEnglishAdd( Model model) {
        model.addAttribute( "article",new Article(  ) );
        return "englishAdd";
    }

    @PostMapping("/freeEnglish/add")
    public String postAdd(@AuthenticationPrincipal User user, @RequestParam String title, @RequestParam String anons, @RequestParam String text, String image, Model model) {
        Article article=new Article(title,anons,text, image );
        articleRepository.save( article );
        return "redirect:/freeEnglish";
    }

    @GetMapping("/freeEnglish/{id}")
    public String freeEnglishDetails(@PathVariable(value = "id") long id, Model model) {
        if(!articleRepository.existsById( id )){
            return "redirect:/freeEnglish";
        }
        Optional<Article> article= articleRepository.findById( id );
        List<Article> res=new ArrayList<>(  );
        article.ifPresent( res::add );
        model.addAttribute( "article",res );
        return "englishDetails";
    }

    @GetMapping("/freeEnglish/edit/{id}")
    public String freeEnglishEdit(@PathVariable(value = "id") long id, Model model) {
        if(!articleRepository.existsById( id )){
            return "redirect:/freeEnglish";
        }
        Article article= articleRepository.findById( id ).orElseThrow();
        model.addAttribute( "article",article );

        return "englishEdit";
    }


    @PostMapping("/freeEnglish/edit/{id}")
    public String postEdit(@PathVariable Long id, Article article) {
        articleRepository.save( article );
        return "redirect:/freeEnglish";
    }


    @PostMapping("/freeEnglish/{id}/remove")
    public String postRemove(@PathVariable(value = "id") long id) {
        Article article=articleRepository.findById( id ).orElseThrow();
        articleRepository.delete( article );
        return "redirect:/freeEnglish";
    }

}
