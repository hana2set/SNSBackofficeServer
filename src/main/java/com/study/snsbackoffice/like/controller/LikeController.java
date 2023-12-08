package com.study.snsbackoffice.like.controller;

import com.study.snsbackoffice.common.filter.UserDetailsImpl;
import com.study.snsbackoffice.follow.dto.FollowerResponseDto;
import com.study.snsbackoffice.follow.dto.FollowingResponseDto;
import com.study.snsbackoffice.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikeController {

    private final FollowService followService;

    @PostMapping("/posts/{postId}/like")
    public ResponseEntity<String> likePost(@PathVariable Long postId,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.likePost(postId);
    }

//    @DeleteMapping("/posts/{postId}/like")
//    public ResponseEntity<String> unFollowUser(@PathVariable Long userId,
//                                               @AuthenticationPrincipal UserDetailsImpl userDetails){
//        return followService.unFollowUser(userId, userDetails.getUser());
//    }
//
//    @GetMapping("/posts/{postId}/like")
//    public FollowingResponseDto getFollowings(@PathVariable Long userId){
//        return followService.getFollowings(userId);
//    }
//
//    @GetMapping("/follower")
//    public FollowerResponseDto getFollowers(@PathVariable Long userId){
//        return followService.getFollowers(userId);
//    }
}
