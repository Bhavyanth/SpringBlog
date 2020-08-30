package com.blog.MyBlog.repository;

import com.blog.MyBlog.model.Subpost;
import com.blog.MyBlog.model.User;
import com.blog.MyBlog.model.Post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubpost(Subpost subpost);

    List<Post> findByUser(User user);
}
