package com.phoenix.blog.service;

import com.phoenix.blog.DTO.VoteDto;
import com.phoenix.blog.exceptions.PostNotFoundException;
import com.phoenix.blog.exceptions.SpringPostException;
import com.phoenix.blog.Bean.Post;
import com.phoenix.blog.Bean.Vote;
import com.phoenix.blog.DAO.PostDAO;
import com.phoenix.blog.DAO.VoteDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.phoenix.blog.Bean.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {

    private VoteDAO voteDAO;
    private PostDAO postDAO;
    private AuthService authService;

    @Transactional
    public void vote(VoteDto voteDto) {
        Post post = postDAO.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post not found with ID - " + voteDto.getPostId()));
        Optional<Vote> voteByPostAndUser = voteDAO.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
        if (voteByPostAndUser.isPresent() &&
                voteByPostAndUser.get().getVoteType()
                        .equals(voteDto.getVoteType())) {
            throw new SpringPostException("You have already "
                    + voteDto.getVoteType() + "'d for this post");
        }
        if (UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteDAO.save(mapToVote(voteDto, post));
        postDAO.save(post);
    }

    private Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }
}
