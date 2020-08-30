package com.blog.MyBlog.service;

import com.blog.MyBlog.mapper.CommentMapper;
import com.blog.MyBlog.model.User;
import com.blog.MyBlog.dto.CommentsDto;
import com.blog.MyBlog.exceptions.PostNotFoundException;
import com.blog.MyBlog.model.Comment;
import com.blog.MyBlog.model.NotificationEmail;
import com.blog.MyBlog.model.Post;
import com.blog.MyBlog.repository.CommentRepository;
import com.blog.MyBlog.repository.PostRepository;
import com.blog.MyBlog.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class CommentService {
    private static final String POST_URL = "";
    private PostRepository postRepository;
    private UserRepository userRepository;
    private AuthService authService;
    private CommentMapper commentMapper;
    private CommentRepository commentRepository;
    private MailContentBuilder mailContentBuilder;
    private MailService mailService;

    public void save(CommentsDto commentsDto) {
        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));
        Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
        commentRepository.save(comment);

        String message = mailContentBuilder.build(post.getUser().getUsername() + " posted a comment on your post." + POST_URL);
        sendCommentNotification(message, post.getUser());
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
    }

    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto).collect(toList());
    }

    public List<CommentsDto> getAllCommentsForUser(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(toList());
    }
}
