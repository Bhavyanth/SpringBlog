package com.phoenix.blog.DAO;

import com.phoenix.blog.Bean.Subpost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubpostDAO extends JpaRepository<Subpost, Long> {

    Optional<Subpost> findByName(String subpostName);
}
