package com.study.snsbackoffice.comment.entity;

import com.study.snsbackoffice.comment.dto.CommentRequestDto;
import com.study.snsbackoffice.common.entity.Timestamped;
import com.study.snsbackoffice.post.entity.Post;
import com.study.snsbackoffice.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @JoinColumn(name = "parent_id")
    private Comment parent;      // 상위 댓글

    @Column
    private Long topParentId;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public Comment(CommentRequestDto requestDto, User user, Post post) {
        this.text = requestDto.getText();
        this.post = post;
        this.user = user;
    }

    public void addParent(Comment parentComment) {
        this.parent = parentComment;
        if (this.topParentId == this.id) { //부모 댓글이 있으면 최상단 부모 id 저장
            if (parentComment.getParent() != null) {
                this.topParentId = parentComment.getParent().getTopParentId();
            } else {
                this.topParentId = parentComment.getId();
            }
        }
    }

    public void update(CommentRequestDto requestDto) {
        this.text = requestDto.getText();
    }
}