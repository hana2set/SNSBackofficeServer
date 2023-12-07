package com.study.snsbackoffice.follow.controller;

import com.study.snsbackoffice.common.filter.UserDetailsImpl;
import com.study.snsbackoffice.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/{userId}/follow")
public class FollowController {

    private final FollowService followService;

    @PostMapping
    public ResponseEntity<String> followUser(@PathVariable Long userId,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails){
        return followService.followUser(userId, userDetails.getUser());
    }
}
