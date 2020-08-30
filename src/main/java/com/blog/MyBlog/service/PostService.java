package com.blog.MyBlog.service;

import com.blog.MyBlog.mapper.PostMapper;
import com.blog.MyBlog.model.Subpost;
import com.blog.MyBlog.model.User;
import com.blog.MyBlog.dto.PostRequest;
import com.blog.MyBlog.dto.PostResponse;
import com.blog.MyBlog.exceptions.PostNotFoundException;
import com.blog.MyBlog.exceptions.SubpostNotFoundException;
import com.blog.MyBlog.model.Post;
import com.blog.MyBlog.repository.PostRepository;
import com.blog.MyBlog.repository.SubpostRepository;
import com.blog.MyBlog.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Transactional
public class PostService {

    private PostRepository postRepository;
    private SubpostRepository subpostRepository;
    private UserRepository userRepository;
    private AuthService authService;
    private PostMapper postMapper;

    public void save(PostRequest postRequest) {
        Subpost subpost = subpostRepository.findByName(postRequest.getSubpostName())
                .orElseThrow(() -> new SubpostNotFoundException(postRequest.getSubpostName()));
        postRepository.save(postMapper.map(postRequest, subpost, authService.getCurrentUser()));
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubpost(Long subpostId) {
        Subpost subpost = subpostRepository.findById(subpostId)
                .orElseThrow(() -> new SubpostNotFoundException(subpostId.toString()));
        List<Post> posts = postRepository.findAllBySubpost(subpost);
        return posts.stream().map(postMapper::mapToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }
}
