package com.study.snsbackoffice.like.service;


import com.study.snsbackoffice.common.constant.ExceptionType;
import com.study.snsbackoffice.common.exception.GlobalCustomException;
import com.study.snsbackoffice.follow.entity.Follow;
import com.study.snsbackoffice.follow.respository.FollowRepository;
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

    public ResponseEntity<String> followUser(Long followingId, User follower) {
        if(followingId.equals(follower.getId())){
            throw new GlobalCustomException(ExceptionType.SELF_FOLLOW);
        }

        Optional<Follow> checkFollow = followRepository.findByFollowingIdAndFollowerId(followingId, follower.getId());
        if(checkFollow.isPresent()){
            throw new GlobalCustomException(ExceptionType.ALREADY_FOLLOW);
        }

        User following = findUser(followingId);
        Follow follow = new Follow(follower, following);
        followRepository.save(follow);

        return new ResponseEntity<>(following.getNickname() + "님을 팔로우하였습니다.", HttpStatus.OK);
    }


}
