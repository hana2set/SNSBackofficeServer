package com.study.snsbackoffice.like.entity;

import com.study.snsbackoffice.post.entity.Post;
import com.study.snsbackoffice.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "post_likes")
@NoArgsConstructor
public class post_likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToMany
    @JoinColumn(name = "following_id")
    private User following;

    public Follow(User follower, User following){
        this.follower = follower;
        this.following = following;
    }
}