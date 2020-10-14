package com.phoenix.blog.service;

import com.phoenix.blog.mapper.CommentMapper;
import com.phoenix.blog.Bean.User;
import com.phoenix.blog.DTO.CommentsDto;
import com.phoenix.blog.exceptions.PostNotFoundException;
import com.phoenix.blog.Bean.Comment;
import com.phoenix.blog.Bean.NotificationEmail;
import com.phoenix.blog.Bean.Post;
import com.phoenix.blog.DAO.CommentDAO;
import com.phoenix.blog.DAO.PostDAO;
import com.phoenix.blog.DAO.UserDAO;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class CommentService {
    private static final String POST_URL = "";
    private PostDAO postDAO;
    private UserDAO userDAO;
    private AuthService authService;
    private CommentMapper commentMapper;
    private CommentDAO commentDAO;
    private MailContentBuilder mailContentBuilder;
    private MailService mailService;

    public void save(CommentsDto commentsDto) {
        Post post = postDAO.findById(commentsDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));
        Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
        commentDAO.save(comment);

        String message = mailContentBuilder.build(post.getUser().getUsername() + " posted a comment on your post." + POST_URL);
        sendCommentNotification(message, post.getUser());
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
    }

    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        Post post = postDAO.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentDAO.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto).collect(toList());
    }

    public List<CommentsDto> getAllCommentsForUser(String userName) {
        User user = userDAO.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));
        return commentDAO.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(toList());
    }
}
