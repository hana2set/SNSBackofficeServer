package com.study.snsbackoffice.like.service;


import com.study.snsbackoffice.common.constant.ExceptionType;
import com.study.snsbackoffice.common.exception.GlobalCustomException;
import com.study.snsbackoffice.follow.entity.Follow;
import com.study.snsbackoffice.follow.respository.FollowRepository;
import com.study.snsbackoffice.like.respository.LikeRepository;
import com.study.snsbackoffice.post.repository.PostRepository;
import com.study.snsbackoffice.user.entity.User;
import com.study.snsbackoffice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class likeService {


    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    public ResponseEntity<String> LikePost(Long PostId) {

    }


}
