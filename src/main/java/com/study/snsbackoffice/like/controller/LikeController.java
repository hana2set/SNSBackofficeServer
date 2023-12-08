package com.study.snsbackoffice.like.controller;

import com.study.snsbackoffice.common.filter.UserDetailsImpl;
import com.study.snsbackoffice.like.respository.PostLikesRepository;
import com.study.snsbackoffice.like.service.LikeService;
import com.study.snsbackoffice.post.entity.Post;
import com.study.snsbackoffice.post.repository.PostRepository;
import com.study.snsbackoffice.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/posts/{postId}/likes")
    public ResponseEntity<String> likePost(@PathVariable Long postId,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {

        User user = userDetails.getUser();
        return likeService.likePost(postId, user);
    }
}
