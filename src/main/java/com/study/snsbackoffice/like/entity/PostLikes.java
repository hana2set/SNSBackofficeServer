package com.study.snsbackoffice.like.entity;

import com.study.snsbackoffice.post.entity.Post;
import com.study.snsbackoffice.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "post_likes")
public class PostLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public PostLikes(Post post, User user) {
        this.post = post;
        this.user = user;
    }


}