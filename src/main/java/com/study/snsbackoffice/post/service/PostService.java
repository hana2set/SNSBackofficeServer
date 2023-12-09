package com.study.snsbackoffice.post.service;

import com.study.snsbackoffice.common.constant.ExceptionType;
import com.study.snsbackoffice.common.exception.GlobalCustomException;
import com.study.snsbackoffice.post.dto.PostRequestDto;
import com.study.snsbackoffice.post.dto.PostResponseDto;
import com.study.snsbackoffice.post.entity.Post;
import com.study.snsbackoffice.post.repository.PostRepository;
import com.study.snsbackoffice.user.entity.User;
import com.study.snsbackoffice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public PostResponseDto createPost(User user, PostRequestDto postRequestDto) {
        Post post = postRepository.save(new Post(user, postRequestDto));
        return new PostResponseDto(post);
    }

    public PostResponseDto getPost(Long id) {
        return new PostResponseDto(findPost(id));
    }

    public List<PostResponseDto> getAllPosts() {
        return postRepository.findAllByOrderByNoticeDescCreatedAtDesc()
                .stream()
                .map(PostResponseDto::new)
                .toList();
    }

    public List<PostResponseDto> getUserPosts(User user) {
        List<Post> postList = postRepository.findAllByUserId(user.getId());
        return postList.stream().map(PostResponseDto :: new).toList();
    }

    @Transactional
    public PostResponseDto updatePost(User user, Long id, PostRequestDto postRequestDto) {
        Post post = findPost(id);
        if(!user.getId().equals(post.getUser().getId())){
            throw new GlobalCustomException(ExceptionType.ONLY_AUTHOR_ACCESS);
        }
        post.update(postRequestDto);
        return new PostResponseDto(post);
    }

    public ResponseEntity<String> deletePost(User user, Long postId) {
        Post post = findPost(postId);
        if(!user.getId().equals(post.getUser().getId())){
            throw new GlobalCustomException(ExceptionType.ONLY_AUTHOR_ACCESS);
        }
        postRepository.delete(post);
        return new ResponseEntity<>("게시글이 삭제되었습니다.", HttpStatus.OK);
    }

    private Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new GlobalCustomException(ExceptionType.NOT_EXIST_POST)
        );
    }
}
