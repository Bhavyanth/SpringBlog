package com.phoenix.blog.DAO;

import com.phoenix.blog.Bean.User;
import com.phoenix.blog.Bean.Post;
import com.phoenix.blog.Bean.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteDAO extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
