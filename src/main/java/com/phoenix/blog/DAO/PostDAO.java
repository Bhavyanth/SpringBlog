package com.phoenix.blog.DAO;

import com.phoenix.blog.Bean.Subpost;
import com.phoenix.blog.Bean.User;
import com.phoenix.blog.Bean.Post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostDAO extends JpaRepository<Post, Long> {
    List<Post> findAllBySubpost(Subpost subpost);

    List<Post> findByUser(User user);
}
