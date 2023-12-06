package com.study.snsbackoffice.comment.dto;

import com.study.snsbackoffice.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentResponseDto {
    private Long id;
    private String username;
    private String text;
    private LocalDateTime createdAt;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.username = comment.getUser().getUsername();
        this.text = comment.getText();
        this.createdAt = comment.getCreatedAt();
    }
}