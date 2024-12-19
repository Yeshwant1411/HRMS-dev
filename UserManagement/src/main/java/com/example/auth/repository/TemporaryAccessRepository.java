package com.example.auth.repository;

import com.example.auth.model.TemporaryAccess;
import com.example.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.Optional;

public interface TemporaryAccessRepository extends JpaRepository<TemporaryAccess, Long> {
    Optional<TemporaryAccess> findByUserAndActiveIsTrueAndEndTimeAfter(User user, LocalDateTime now);
}