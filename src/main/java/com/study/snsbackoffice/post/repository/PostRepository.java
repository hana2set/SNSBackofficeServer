package com.study.snsbackoffice.post.repository;

import com.study.snsbackoffice.post.entity.Post;
import com.study.snsbackoffice.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByNoticeDescCreatedAtDesc();

    List<Post> findAllByUserId(Long id);
}
