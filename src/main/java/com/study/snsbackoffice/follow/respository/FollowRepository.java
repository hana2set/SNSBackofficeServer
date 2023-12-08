package com.study.snsbackoffice.follow.respository;

import com.study.snsbackoffice.follow.entity.Follow;
import com.study.snsbackoffice.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    Optional<Follow> findByFollowingIdAndFollowerId(Long followingId, Long id);
}
