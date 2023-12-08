package com.study.snsbackoffice.post.service;

import com.study.snsbackoffice.common.constant.ExceptionType;
import com.study.snsbackoffice.common.exception.GlobalCustomException;
import com.study.snsbackoffice.post.dto.PostRequestDto;
import com.study.snsbackoffice.post.dto.PostResponseDto;
import com.study.snsbackoffice.post.entity.Post;
import com.study.snsbackoffice.post.repository.PostRepository;
import com.study.snsbackoffice.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostAdminService {

    private final PostRepository postRepository;

    @Transactional
    public PostResponseDto createPost(PostRequestDto requestDto, User user) {
        Post post = postRepository.save(new Post(user, requestDto));
        post.noticePost();
        return new PostResponseDto(post);
    }


    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto requestDto) {
        Post post = findPost(id);
        post.update(requestDto);
        return new PostResponseDto(post);
    }

    @Transactional
    public ResponseEntity<String> deletePost(Long id) {
        Post post = findPost(id);
        postRepository.delete(post);
        return new ResponseEntity<>("게시글이 삭제되었습니다.", HttpStatus.OK);
    }

    private Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new GlobalCustomException(ExceptionType.NOT_EXIST_POST)
        );
    }
}
