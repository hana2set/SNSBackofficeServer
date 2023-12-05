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

    PostAdminService postAdminService;

    @PatchMapping("/{postId}")
    public PostResponseDto updatePost(@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postAdminService.updatePost(postId, postRequestDto, userDetails.getUser());
    }

    @DeleteMapping("/{postId}")
    public PostResponseDto deletePost(@PathVariable Long postId,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postAdminService.deletePost(postId, userDetails.getUser());
    }
}
