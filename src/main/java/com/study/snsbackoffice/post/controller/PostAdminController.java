package com.study.snsbackoffice.post.controller;

import com.study.snsbackoffice.common.filter.UserDetailsImpl;
import com.study.snsbackoffice.post.dto.PostRequestDto;
import com.study.snsbackoffice.post.dto.PostResponseDto;
import com.study.snsbackoffice.post.service.PostAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/posts")
@RequiredArgsConstructor
public class PostAdminController {

    private final PostAdminService postAdminService;

    @PostMapping
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postAdminService.createPost(postRequestDto, userDetails.getUser());
    }

    @PatchMapping("/{postId}")
    public PostResponseDto updatePost(@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto) {
        return postAdminService.updatePost(postId, postRequestDto);
    }

    @DeleteMapping("/{postId}")
    public PostResponseDto deletePost(@PathVariable Long postId){
        return postAdminService.deletePost(postId);
    }
}
