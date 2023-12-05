package com.study.snsbackoffice.post.repository;

import com.study.snsbackoffice.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCreatedAtDesc();
    
    List<Post> findAllByUserId(Long id);
}
