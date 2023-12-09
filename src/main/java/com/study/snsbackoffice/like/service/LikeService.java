package com.study.snsbackoffice.like.service;


import com.study.snsbackoffice.comment.entity.Comment;
import com.study.snsbackoffice.comment.repository.CommentRepository;
import com.study.snsbackoffice.common.constant.ExceptionType;
import com.study.snsbackoffice.common.exception.GlobalCustomException;
import com.study.snsbackoffice.like.entity.CommentLikes;
import com.study.snsbackoffice.like.entity.PostLikes;
import com.study.snsbackoffice.like.respository.CommentLikesRepository;
import com.study.snsbackoffice.like.respository.PostLikesRepository;
import com.study.snsbackoffice.post.entity.Post;
import com.study.snsbackoffice.post.repository.PostRepository;
import com.study.snsbackoffice.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {


    private final PostLikesRepository postLikesRepository;
    private final PostRepository postRepository;
    private final CommentLikesRepository commentLikesRepository;
    private final CommentRepository commentRepository;

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

    public ResponseEntity<String> likeComment(Long commentId, User user) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new GlobalCustomException(ExceptionType.NOT_EXIST_COMMENT)
        );

        // 댓글 작성자와 같은 유저라면 좋아요 불가
        if(comment.getUser().getId().equals(user.getId())){
            throw new GlobalCustomException(ExceptionType.SAME_USER);
        }

        if(commentLikesRepository.findByCommentAndUser(comment,user).isPresent()){
            throw new GlobalCustomException(ExceptionType.ALREADY_EXIST_LIKE);
        }

        CommentLikes commentLikes = new CommentLikes();
        commentLikes.setUser(user); // 외래 키(연관 관계) 설정
        commentLikes.setComment(comment); // 외래 키(연관 관계) 설정

        commentLikesRepository.save(commentLikes);

        return new ResponseEntity<>(comment.getText() + " 좋아요.", HttpStatus.OK);
    }

    public ResponseEntity<String> deleteCommentLike(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new GlobalCustomException(ExceptionType.NOT_EXIST_COMMENT)
        );



        CommentLikes commentLikes = commentLikesRepository.findByCommentAndUser(comment,user).orElseThrow(
                () -> new GlobalCustomException(ExceptionType.NOT_EXIST_LIKE)
        );

        commentLikesRepository.delete(commentLikes);

        return new ResponseEntity<>("좋아요 삭제 완료", HttpStatus.OK);

    }
}
