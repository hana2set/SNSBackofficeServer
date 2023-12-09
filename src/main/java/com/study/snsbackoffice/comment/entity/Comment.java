package com.study.snsbackoffice.comment.entity;

import com.study.snsbackoffice.comment.dto.CommentRequestDto;
import com.study.snsbackoffice.common.entity.Timestamped;
import com.study.snsbackoffice.like.entity.CommentLikes;
import com.study.snsbackoffice.post.entity.Post;
import com.study.snsbackoffice.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "comment")
@NoArgsConstructor
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToMany(mappedBy = "comment")
    private List<CommentLikes> commentLikes = new ArrayList<>();

    public Comment(CommentRequestDto requestDto, User user, Post post) {
        this.text = requestDto.getText();
        this.post = post;
        this.user = user;
    }

    public void update(CommentRequestDto requestDto) {
        this.text = requestDto.getText();
    }
}