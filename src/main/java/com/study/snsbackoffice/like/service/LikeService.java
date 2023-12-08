package com.study.snsbackoffice.like.service;


import com.study.snsbackoffice.common.constant.ExceptionType;
import com.study.snsbackoffice.common.exception.GlobalCustomException;
import com.study.snsbackoffice.like.entity.PostLikes;
import com.study.snsbackoffice.like.respository.PostLikesRepository;
import com.study.snsbackoffice.post.entity.Post;
import com.study.snsbackoffice.post.repository.PostRepository;
import com.study.snsbackoffice.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {


    private final PostLikesRepository postLikesRepository;
    private final PostRepository postRepository;

    public ResponseEntity<String> likePost(Long PostId, User user) {

        Post post = postRepository.findById(PostId).orElseThrow(
                () -> new GlobalCustomException(ExceptionType.NOT_EXIST_POST)
        );

        if(postLikesRepository.findByPostAndUser(post,user).isPresent()){
            throw new GlobalCustomException(ExceptionType.ALREADY_EXIST_LIKE);
        }

        PostLikes postLikes = new PostLikes();
        postLikes.setUser(user); // 외래 키(연관 관계) 설정
        postLikes.setPost(post); // 외래 키(연관 관계) 설정

        postLikesRepository.save(postLikes);

        return new ResponseEntity<>(post.getTitle() + " 좋아요.", HttpStatus.OK);
    }

}
