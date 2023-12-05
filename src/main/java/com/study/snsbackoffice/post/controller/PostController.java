package com.study.snsbackoffice.post.controller;

import com.study.snsbackoffice.common.filter.UserDetailsImpl;
import com.study.snsbackoffice.post.dto.PostRequestDto;
import com.study.snsbackoffice.post.dto.PostResponseDto;
import com.study.snsbackoffice.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final PostService postService;
    @PostMapping("/post")
    public PostResponseDto createPost(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                      @RequestBody PostRequestDto postRequestDto)
    {
        return postService.createPost(userDetails.getUser(), postRequestDto);
    }

    @GetMapping("/post/{postId}")
    public PostResponseDto getPost(@PathVariable Long postId)
    {
        return postService.getPost(postId);
    }

    @GetMapping("/posts")
    public Map<String, List<PostResponseDto>> getPosts(){
        return postService.getPosts();
    }

    @GetMapping("/userposts")
    public List<PostResponseDto> getUserPosts(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.getUserPosts(userDetails.getUser());
    }

    @PatchMapping("/post/{postId}")
    public PostResponseDto updatePost(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                      @PathVariable Long postId, @RequestBody PostRequestDto postRequestDto) {
        return postService.updatePost(userDetails.getUser(), postId, postRequestDto);
    }
    @DeleteMapping("/post/{postId}")
    public PostResponseDto deletePost(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                      @PathVariable Long postId){
        return postService.deletePost(userDetails.getUser(), postId);
    }

}