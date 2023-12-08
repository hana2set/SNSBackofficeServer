package com.study.snsbackoffice.comment.service;

import com.study.snsbackoffice.comment.dto.CommentRequestDto;
import com.study.snsbackoffice.comment.dto.CommentResponseDto;
import com.study.snsbackoffice.comment.entity.Comment;
import com.study.snsbackoffice.comment.repository.CommentRepository;
import com.study.snsbackoffice.common.constant.ExceptionType;
import com.study.snsbackoffice.common.exception.GlobalCustomException;
import com.study.snsbackoffice.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentAdminService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentResponseDto updateComment(Long id, CommentRequestDto requestDto) {
        Comment comment = findComment(id);
        comment.update(requestDto);
        return new CommentResponseDto(comment);
    }

    @Transactional
    public ResponseEntity<String> deleteComment(Long id){
        Comment comment = findComment(id);
        commentRepository.delete(comment);
        return new ResponseEntity<>("댓글이 삭제되었습니다.", HttpStatus.OK);
    }

    private Comment findComment(Long id) {
        return commentRepository.findById(id).orElseThrow(() ->
                new GlobalCustomException(ExceptionType.NOT_EXIST_COMMENT)
        );
    }
}
