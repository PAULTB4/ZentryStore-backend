package com.zentry.zentrystore.domain.user.repository;

import com.zentry.zentrystore.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Búsquedas básicas
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByVerificationToken(String token);

    Optional<User> findByPasswordResetToken(String token);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    // Búsquedas con condiciones
    List<User> findByActiveTrue();

    List<User> findByActiveFalse();

    List<User> findByEmailVerifiedTrue();

    List<User> findByEmailVerifiedFalse();

    // Búsquedas por fecha
    List<User> findByCreatedAtAfter(LocalDateTime date);

    List<User> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<User> findByLastLoginAtAfter(LocalDateTime date);

    // Búsquedas complejas con Query
    @Query("SELECT u FROM User u WHERE u.active = true AND u.emailVerified = true")
    List<User> findActiveAndVerifiedUsers();

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :roleName")
    List<User> findUsersByRoleName(@Param("roleName") String roleName);

    @Query("SELECT u FROM User u WHERE u.passwordResetToken = :token AND u.passwordResetExpiresAt > :now")
    Optional<User> findByValidPasswordResetToken(@Param("token") String token, @Param("now") LocalDateTime now);

    @Query("SELECT COUNT(u) FROM User u WHERE u.createdAt >= :startDate")
    Long countUsersCreatedSince(@Param("startDate") LocalDateTime startDate);

    @Query("SELECT u FROM User u WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<User> searchUsers(@Param("search") String search);

    // Validaciones de tokens
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u " +
            "WHERE u.verificationToken = :token")
    boolean existsByVerificationToken(@Param("token") String token);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u " +
            "WHERE u.passwordResetToken = :token AND u.passwordResetExpiresAt > :now")
    boolean isPasswordResetTokenValid(@Param("token") String token, @Param("now") LocalDateTime now);
}