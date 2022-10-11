package com.week4.springsecurity.controller;

import com.week4.springsecurity.entity.Article;
import com.week4.springsecurity.repository.ArticleRepository;
import com.week4.springsecurity.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired // 스프링 부트가 미리 생성해놓은 객체를 가져다가 자동 연결해줌
    private ArticleRepository articleRepository;

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<Article> articleEntityList= articleRepository.findAll();

        // 2. 가져온 Article 묶음을 뷰로 전달
        model.addAttribute("articleList",articleEntityList);

        model.addAttribute("username", userDetails.getUser().getUsername());

        return "index";
    }
}