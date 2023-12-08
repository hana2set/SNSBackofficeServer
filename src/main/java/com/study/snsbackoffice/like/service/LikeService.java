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

    public ResponseEntity<String> likePost(Long postId, User user) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new GlobalCustomException(ExceptionType.NOT_EXIST_POST)
        );

        // 글 작성자와 같은 유저라면 좋아요 불가
        if(post.getUser().getId().equals(user.getId())){
            throw new GlobalCustomException(ExceptionType.SAME_USER);
        }

        if(postLikesRepository.findByPostAndUser(post,user).isPresent()){
            throw new GlobalCustomException(ExceptionType.ALREADY_EXIST_LIKE);
        }

        PostLikes postLikes = new PostLikes();
        postLikes.setUser(user); // 외래 키(연관 관계) 설정
        postLikes.setPost(post); // 외래 키(연관 관계) 설정

        postLikesRepository.save(postLikes);

        return new ResponseEntity<>(post.getTitle() + " 좋아요.", HttpStatus.OK);
    }

    public ResponseEntity<String> deletePostLike(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new GlobalCustomException(ExceptionType.NOT_EXIST_POST)
        );



        PostLikes postLikes = postLikesRepository.findByPostAndUser(post,user).orElseThrow(
                () -> new GlobalCustomException(ExceptionType.NOT_EXIST_LIKE)
        );

        postLikesRepository.delete(postLikes);

        return new ResponseEntity<>("좋아요 삭제 완료", HttpStatus.OK);

    }
}
