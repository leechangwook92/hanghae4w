package com.week4.springsecurity.dto;


import com.week4.springsecurity.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ArticleForm {

    private Long id; //id 추가

    private String title;
    private String content;
//    private String number;

    public Article toEntity() {
        return new Article(id,title,content);
    }

}
