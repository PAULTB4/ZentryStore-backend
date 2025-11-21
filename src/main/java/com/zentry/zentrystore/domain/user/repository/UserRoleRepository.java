package com.zentry.zentrystore.domain.user.repository;

import com.zentry.zentrystore.domain.user.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    // Búsqueda por nombre
    Optional<UserRole> findByName(String name);

    boolean existsByName(String name);

    // Búsquedas de roles específicos
    @Query("SELECT r FROM UserRole r WHERE r.name = 'ROLE_ADMIN'")
    Optional<UserRole> findAdminRole();

    @Query("SELECT r FROM UserRole r WHERE r.name = 'ROLE_USER'")
    Optional<UserRole> findUserRole();

    @Query("SELECT r FROM UserRole r WHERE r.name = 'ROLE_MODERATOR'")
    Optional<UserRole> findModeratorRole();

    // Búsqueda por nombres múltiples
    Set<UserRole> findByNameIn(Set<String> names);

    // Contar usuarios por rol
    @Query("SELECT COUNT(u) FROM User u JOIN u.roles r WHERE r.id = :roleId")
    Long countUsersByRoleId(@Param("roleId") Long roleId);

    @Query("SELECT COUNT(u) FROM User u JOIN u.roles r WHERE r.name = :roleName")
    Long countUsersByRoleName(@Param("roleName") String roleName);
}