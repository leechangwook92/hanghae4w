package com.week4.springsecurity.controller.Article;


import com.week4.springsecurity.entity.Article;
import com.week4.springsecurity.dto.ArticleForm;
import com.week4.springsecurity.dto.CommentDto;
import com.week4.springsecurity.entity.Comment;
import com.week4.springsecurity.repository.ArticleRepository;
import com.week4.springsecurity.repository.CommentRepository;
import com.week4.springsecurity.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
public class ArticleController {

     // 스프링 부트가 미리 생성해놓은 객체를 가져다가 자동 연결해줌
    private final ArticleRepository articleRepository;

    private final CommentRepository commentRepository;

    private final CommentService commentService;

    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form, RedirectAttributes rttr){
        //System.out.println(form.toString());
        log.info(form.toString());

        //1.dto entity로 변환
        Article article = form.toEntity();
        //System.out.println(article.toString());
        log.info(article.toString());

        //2.Repository에게 Entity를 디비안에 저장
        Article saved = articleRepository.save(article);
        //System.out.println(saved.toString());
        log.info(saved.toString());

        rttr.addFlashAttribute("msg","등록 완료");

        return "redirect:/";
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model) {
        log.info("id =" + id);

        // 1. id로 데이터를 가져온다
        Article articleEntity = articleRepository.findById(id).orElse(null);
        List<CommentDto> commentDtos = commentService.comments(id);

        // 2. 가져온 데이터를 모델에 등록
        model.addAttribute("article", articleEntity);
        model.addAttribute("commentDtos",commentDtos);

        // 3. 보여줄 페이지 설정
        return "articles/show";
    }

   @GetMapping("/articles/edit/{id}")
   public String edit(@PathVariable Long id, Model model){
        //데이터 가져오기
       Article articleEntity = articleRepository.findById(id).orElse(null);

       //
       model.addAttribute("article", articleEntity);

        //뷰페이지 설정

        return "articles/edit";
   }

   @PostMapping("/articles/update")
    public String update(ArticleForm form, RedirectAttributes rttr){
        log.info(form.toString());

       Article articleEntity = form.toEntity();

       Article target = articleRepository.findById(articleEntity.getId()).orElse(null);

       if(target != null) {
           articleRepository.save(articleEntity);
       }
       rttr.addFlashAttribute("msg", "수정 완료");

        return "redirect:/articles/" +articleEntity.getId();
   }

   @Transactional
   @GetMapping("/articles/delete/{id}")
   public String delete(@PathVariable Long id, RedirectAttributes rttr){
        log.info("삭제 요청이 들어왔습니다.");

       Article target = articleRepository.findById(id).orElse(null);
       log.info(target.toString());

       List<Comment> findComment = commentRepository.findByArticleId(id);
       if(findComment.size() > 0) {
           for (Comment comment : findComment) {
               commentRepository.delete(comment);
           }
       }

       if(target != null){
           articleRepository.delete(target);
           rttr.addFlashAttribute("msg", "삭제 완료");
       }

       return "redirect:/";
   }
}
