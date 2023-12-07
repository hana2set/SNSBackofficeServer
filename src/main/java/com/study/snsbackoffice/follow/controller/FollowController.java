package com.study.snsbackoffice.follow.controller;

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
@RequestMapping("/api/users/{userId}")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/follow")
    public ResponseEntity<String> followUser(@PathVariable Long userId,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails){
        return followService.followUser(userId, userDetails.getUser());
    }

    @DeleteMapping("/follow")
    public ResponseEntity<String> unFollowUser(@PathVariable Long userId,
                                               @AuthenticationPrincipal UserDetailsImpl userDetails){
        return followService.unFollowUser(userId, userDetails.getUser());
    }

    @GetMapping("/following")
    public FollowingResponseDto getFollowings(@PathVariable Long userId){
        return followService.getFollowings(userId);
    }

    @GetMapping("/follower")
    public FollowerResponseDto getFollowers(@PathVariable Long userId){
        return followService.getFollowers(userId);
    }
}
