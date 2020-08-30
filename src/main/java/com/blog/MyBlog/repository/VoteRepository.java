package com.blog.MyBlog.repository;

import com.blog.MyBlog.model.User;
import com.blog.MyBlog.model.Post;
import com.blog.MyBlog.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
