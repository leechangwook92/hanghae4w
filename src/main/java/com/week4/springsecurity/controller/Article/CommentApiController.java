package com.week4.springsecurity.controller.Article;


import com.week4.springsecurity.dto.CommentDto;
import com.week4.springsecurity.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

    private final CommentService commentService;

    // 댓글 목록 조회
    @GetMapping("/api/articles/comments/{articleId}")
    public ResponseEntity<List<CommentDto>> comments(@PathVariable Long articleId, Model model){
        // 서비스에게 위임
        List<CommentDto> dtos = commentService.comments(articleId);

        //결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    // 댓글 생성
    @PostMapping("/api/articles/comments/{articleId}")
    public ResponseEntity<CommentDto> create(@PathVariable String articleId,
                                             @RequestBody CommentDto dto){

        Long i = Long.parseLong(articleId);
        System.out.println("dto.getBody() = " + dto.getBody());
        System.out.println("dto.getBody() = " + dto.getNickname());
        // 서비스 위임
        CommentDto createdDto=commentService.create(i,dto);
        //결과응답

        return ResponseEntity.status(HttpStatus.OK).body(createdDto);
    }

    // 댓글 수정
    @PatchMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> update(@PathVariable Long id,
                                             @RequestBody CommentDto dto){
        // 서비스 위임hhqh
        CommentDto updatedDto=commentService.update(id,dto);
        //결과응답

        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    }
    // 댓글 삭제
    @DeleteMapping("api/comments/{id}")
    public ResponseEntity<CommentDto> delete(@PathVariable Long id){
        // 서비스 위임
        CommentDto deletedDto=commentService.delete(id);
        //결과응답

        return ResponseEntity.status(HttpStatus.OK).body(deletedDto);
    }

}
