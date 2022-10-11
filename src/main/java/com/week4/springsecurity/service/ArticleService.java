package com.week4.springsecurity.service;


import com.week4.springsecurity.entity.Article;
import com.week4.springsecurity.dto.ArticleForm;
import com.week4.springsecurity.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service // 서비스 선언! (서비스 객체를 스프링부트 선언)
@Slf4j
public class ArticleService {
    @Autowired // DI, 생성 객체를 가져와 선언
    private ArticleRepository articleRepository;

    public List<Article> index() {
        return articleRepository.findAll();
    }


    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();
        if(article.getId() != null){
            return null;
        }
        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm dto) {
        // 1. 수정용 엔티티 생성
        Article article = dto.toEntity();
        log.info("잘못된 요청! id: {}, article: {}", id, toString());

        // 2. 대상 엔티티 찾기
        Article target = articleRepository.findById(id).orElse(null);

        // 3. 잘못된 요청 처리(대상이 없거나, id가 다른경우)
        if(target == null & id != article.getId()) {
            log.info("잘못된 요청! id: {}, article: {}", id, article.toString());
            return null;
        }

        // 4. 업데이트 및 정상 응답(200)
        target.patch(article);
        Article updated = articleRepository.save(target);
        return updated;
    }

    public Article delete(Long id) {
        // 대상 엔티티 찾기
        Article target = articleRepository.findById(id).orElse(null);

        // 잘못된 요청 처리 (대상이 없는 경우)
        if(target == null){
            return null;
        }

        // 대상 삭제 후 응답 반환
        articleRepository.delete(target);
        return target;
    }

    @Transactional // 해당 메소드를 실행하다 실패하면 초기화한다
    public List<Article> createArticles(List<ArticleForm> dtos) {
        // dto 묶음을 Entitiy 묶음으로 변환
        List<Article> articleList = dtos.stream()
                .map( dto -> dto.toEntity())
                .collect(Collectors.toList());
//        List<Article> articleList = new ArrayList<>();
//        for(int i=0; i < dtos.size(); i++){
//            ArticleForm dto = dtos.get(i);
//            Article entity = dto.toEntity();
//            articleList.add(entity);
//        }

        // entity 묶음을 db로 저장
        articleList.stream()
                .forEach(article -> articleRepository.save(article));

        // 강제 예외 발생
        articleRepository.findById(-1L).orElseThrow(
                ()-> new IllegalArgumentException("결재 실패!")
        );

        //결과값 반환
        return articleList;
    }
}
