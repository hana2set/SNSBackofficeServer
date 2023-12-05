package com.study.snsbackoffice.post.service;

import com.study.snsbackoffice.post.dto.PostRequestDto;
import com.study.snsbackoffice.post.dto.PostResponseDto;
import com.study.snsbackoffice.post.entity.Post;
import com.study.snsbackoffice.post.repository.PostRepository;
import com.study.snsbackoffice.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    public PostResponseDto createPost(User user, PostRequestDto postRequestDto) {
        Post post = postRepository.save(new Post(user, postRequestDto));
        return new PostResponseDto(post);
    }

    public PostResponseDto getPost(Long id) {
        return new PostResponseDto(findPost(id));
    }

    public Map<String, List<PostResponseDto>> getPosts() {
        Map<String, List<PostResponseDto>> userPostMap = new HashMap<>();
        List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();
        postList.forEach(post -> {
            if(userPostMap.containsKey(post.getUser().getUsername())){
                userPostMap.get(post.getUser().getUsername()).add(new PostResponseDto(post));
            }
            else{
                userPostMap.put(post.getUser().getUsername(), new ArrayList<>(List.of(new PostResponseDto(post))));
            }
        });
        return userPostMap;
    }

    public List<PostResponseDto> getUserPosts(User user) {
        List<Post> postList = postRepository.findAllByUserId(user.getId());
        return postList.stream().map(PostResponseDto :: new).toList();
    }

    @Transactional
    public PostResponseDto updatePost(User user, Long id, PostRequestDto postRequestDto) {
        Post post = findPost(id);
        if(!user.getId().equals(post.getUser().getId())){
            throw new IllegalArgumentException("일치하지 않는 유저입니다.");
        }
        post.update(postRequestDto);
        return new PostResponseDto(post);
    }

    public PostResponseDto deletePost(User user, Long postId) {
        Post post = findPost(postId);
        if(!user.getId().equals(post.getUser().getId())){
            throw new IllegalArgumentException("일치하지 않는 유저입니다.");
        }
        postRepository.delete(post);
        return new PostResponseDto(post);
    }

    private Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new NullPointerException("포스트가 존재하지 않습니다.")
        );
    }
}
