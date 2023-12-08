package com.study.snsbackoffice.follow.service;

import com.study.snsbackoffice.common.constant.ExceptionType;
import com.study.snsbackoffice.common.exception.GlobalCustomException;
import com.study.snsbackoffice.follow.dto.FollowerResponseDto;
import com.study.snsbackoffice.follow.dto.FollowingResponseDto;
import com.study.snsbackoffice.follow.entity.Follow;
import com.study.snsbackoffice.follow.respository.FollowRepository;
import com.study.snsbackoffice.user.entity.User;
import com.study.snsbackoffice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

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

    @Transactional
    public ResponseEntity<String> unFollowUser(Long followingId, User follower) {
        Follow follow = followRepository.findByFollowingIdAndFollowerId(followingId, follower.getId())
                .orElseThrow(() -> new GlobalCustomException(ExceptionType.NOT_EXIST_FOLLOW));

        User following = findUser(followingId);
        followRepository.delete(follow);
        return new ResponseEntity<>(following.getNickname() + "님을 팔로우 취소하였습니다.", HttpStatus.OK);
    }

    public FollowingResponseDto getFollowings(Long userId) {
        User user = findUser(userId);
        List<String> followingUserList = new ArrayList<>();

        for(Follow follow : user.getFollowingList()){
           followingUserList.add(follow.getFollowing().getNickname());
        }

        return new FollowingResponseDto(followingUserList);
    }

    public FollowerResponseDto getFollowers(Long userId) {
        User user = findUser(userId);
        List<String> followerUserList = new ArrayList<>();

        for(Follow follow : user.getFollowerList()){
            followerUserList.add(follow.getFollower().getNickname());
        }

        return new FollowerResponseDto(followerUserList);
    }

    private User findUser(Long userId){
        return userRepository.findById(userId).orElseThrow(() ->
                new GlobalCustomException(ExceptionType.NOT_EXIST_USER)
        );
    }
}


