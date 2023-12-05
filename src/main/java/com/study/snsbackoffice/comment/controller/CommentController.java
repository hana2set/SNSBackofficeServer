package com.study.snsbackoffice.comment.controller;

import com.study.snsbackoffice.comment.dto.CommentRequestDto;
import com.study.snsbackoffice.comment.dto.CommentResponseDto;
import com.study.snsbackoffice.comment.service.CommentService;
import com.study.snsbackoffice.common.filter.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comments")
    public CommentResponseDto createComment(@RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(requestDto, userDetails.getUser());
    }

    @PatchMapping("/comments/{id}")
    public CommentResponseDto updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.updateComment(id, requestDto, userDetails.getUser());
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteComment(id, userDetails.getUser());
    }

    @GetMapping("/posts/{postId}/comments")
    public Page<CommentResponseDto> getCommentsInPost(
            @PathVariable Long postId,
            @RequestParam(value = "page", defaultValue = "1") int page) {
        return commentService.getCommentsInPost(postId, page-1);
    }
}
