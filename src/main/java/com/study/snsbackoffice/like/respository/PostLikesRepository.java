package com.study.snsbackoffice.like.respository;


import com.study.snsbackoffice.like.entity.PostLikes;
import com.study.snsbackoffice.post.entity.Post;
import com.study.snsbackoffice.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikesRepository extends JpaRepository<PostLikes, Long> {


    Optional<PostLikes> findByPostAndUser(Post post, User user);
}
