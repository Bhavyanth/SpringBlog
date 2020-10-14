package com.phoenix.blog.service;

import com.phoenix.blog.mapper.PostMapper;
import com.phoenix.blog.Bean.Subpost;
import com.phoenix.blog.Bean.User;
import com.phoenix.blog.DTO.PostRequest;
import com.phoenix.blog.DTO.PostResponse;
import com.phoenix.blog.exceptions.PostNotFoundException;
import com.phoenix.blog.exceptions.SubpostNotFoundException;
import com.phoenix.blog.Bean.Post;
import com.phoenix.blog.DAO.PostDAO;
import com.phoenix.blog.DAO.SubpostDAO;
import com.phoenix.blog.DAO.UserDAO;
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

    private PostDAO postDAO;
    private SubpostDAO subpostDAO;
    private UserDAO userDAO;
    private AuthService authService;
    private PostMapper postMapper;

    public void save(PostRequest postRequest) {
        Subpost subpost = subpostDAO.findByName(postRequest.getSubpostName())
                .orElseThrow(() -> new SubpostNotFoundException(postRequest.getSubpostName()));
        postDAO.save(postMapper.map(postRequest, subpost, authService.getCurrentUser()));
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postDAO.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postDAO.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubpost(Long subpostId) {
        Subpost subpost = subpostDAO.findById(subpostId)
                .orElseThrow(() -> new SubpostNotFoundException(subpostId.toString()));
        List<Post> posts = postDAO.findAllBySubpost(subpost);
        return posts.stream().map(postMapper::mapToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userDAO.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return postDAO.findByUser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }
}
