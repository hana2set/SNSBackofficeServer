package com.study.snsbackoffice.like.respository;

import com.study.snsbackoffice.comment.entity.Comment;
import com.study.snsbackoffice.like.entity.CommentLikes;
import com.study.snsbackoffice.post.entity.Post;
import com.study.snsbackoffice.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikesRepository extends JpaRepository<CommentLikes, Long> {
    Optional<CommentLikes> findByCommentAndUser(Comment comment, User user);
}
