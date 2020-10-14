package com.phoenix.blog.DAO;

import com.phoenix.blog.Bean.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenDAO extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);
}
