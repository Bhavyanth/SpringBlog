package com.phoenix.blog.DAO;

import com.phoenix.blog.Bean.User;
import com.phoenix.blog.Bean.Comment;
import com.phoenix.blog.Bean.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentDAO extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}
