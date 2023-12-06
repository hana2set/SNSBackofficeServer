package com.study.snsbackoffice.comment.service;

import com.study.snsbackoffice.comment.dto.CommentRequestDto;
import com.study.snsbackoffice.comment.dto.CommentResponseDto;
import com.study.snsbackoffice.comment.entity.Comment;
import com.study.snsbackoffice.comment.repository.CommentRepository;
import com.study.snsbackoffice.post.entity.Post;
import com.study.snsbackoffice.post.repository.PostRepository;
import com.study.snsbackoffice.user.entity.User;
import com.study.snsbackoffice.user.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public CommentResponseDto createComment(CommentRequestDto requestDto, User user) {
        if (requestDto.getPostId() == null) {
            throw new IllegalIdentifierException("게시글를 선택해주세요.");
        }

        Post post = postRepository.findById(requestDto.getPostId()).orElseThrow(() -> new IllegalIdentifierException("선택한 게시글은 존재하지 않습니다."));
        Comment saveComment = new Comment(requestDto, user, post);

        commentRepository.save(saveComment);

        return new CommentResponseDto(saveComment);
    }

    @Transactional
    public CommentResponseDto updateComment(Long id, CommentRequestDto requestDto, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("선택한 댓글이 존재하지 않습니다."));
        checkUser(user, comment);
        comment.update(requestDto);
        return new CommentResponseDto(comment);
    }

    public ResponseEntity<String> deleteComment(Long id, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("선택한 댓글이 존재하지 않습니다."));
        checkUser(user, comment);
        commentRepository.delete(comment);
        return new ResponseEntity<>("댓글이 삭제되었습니다.", HttpStatus.OK);
    }

    public Page<CommentResponseDto> getCommentsInPost(Long postId, int page) {
        int size = 30;
        Sort sort = Sort.by("topParentId", "id");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Comment> productList = commentRepository.findAllByPostId(postId, pageable);

        Page<CommentResponseDto> responseDtoList = productList.map(CommentResponseDto::new);

        return responseDtoList;
    }

    private void checkUser(User user, Comment comment) {
        if(!user.getId().equals(comment.getUser().getId())){
            throw new IllegalArgumentException("작성자만 삭제/수정 할 수 있습니다.");
        }
    }
}