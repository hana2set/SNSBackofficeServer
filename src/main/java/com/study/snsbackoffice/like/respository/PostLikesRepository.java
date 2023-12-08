package com.study.snsbackoffice.like.respository;


import com.study.snsbackoffice.like.entity.PostLikes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikesRepository extends JpaRepository<PostLikes, Long> {
}
