package com.study.snsbackoffice.comment.controller;

import com.study.snsbackoffice.comment.dto.CommentRequestDto;
import com.study.snsbackoffice.comment.dto.CommentResponseDto;
import com.study.snsbackoffice.comment.service.CommentAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/comments/{id}")
@RequiredArgsConstructor
public class CommentAdminController {

    private final CommentAdminService commentAdminService;

    @PatchMapping
    public CommentResponseDto updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto) {
        return commentAdminService.updateComment(id, requestDto);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteComment(@PathVariable Long id) {
        return commentAdminService.deleteComment(id);
    }
}
