package com.adplatform.qubby.repository;

import com.adplatform.qubby.model.Discussion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DiscussionRepository extends JpaRepository<Discussion, Long> {
    List<Discussion> findAllByOrderByCreateTimeDesc();
}